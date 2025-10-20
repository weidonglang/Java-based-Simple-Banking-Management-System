package com.tianshi.web.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 仅允许管理员访问“查询所有用户”等受限接口。
 * 判定规则（尽量贴合你现有代码与会话约定）：
 *  1) 必须已登录：session 中存在 "loginUser" 或 "username"
 *  2) 管理员判定：优先看 session 中的 Boolean "isAdmin"；否则 username 等于 "admin"
 */
@WebFilter(
        filterName = "AdminAuthFilter",
        urlPatterns = {"/showUsers", "/admin/*"}
)
public class AdminAuthFilter implements Filter {

    // 显式无参构造，避免 Tomcat 实例化时报 InstantiationException
    public AdminAuthFilter() { }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 可选：启动日志
        filterConfig.getServletContext().log("[AdminAuthFilter] 初始化完成");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        if (!(req instanceof HttpServletRequest) || !(resp instanceof HttpServletResponse)) {
            chain.doFilter(req, resp);
            return;
        }

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        HttpSession session = request.getSession(false);

        boolean loggedIn = false;
        String username = null;
        boolean isAdmin = false;

        if (session != null) {
            Object loginUser = session.getAttribute("loginUser");
            Object unameObj  = session.getAttribute("username");

            loggedIn = (loginUser != null) || (unameObj != null);
            if (unameObj instanceof String) {
                username = (String) unameObj;
            }

            Object flag = session.getAttribute("isAdmin");
            if (flag instanceof Boolean) {
                isAdmin = (Boolean) flag;
            }
            // 兜底：用户名为 admin 视为管理员
            if (!isAdmin && username != null && "admin".equalsIgnoreCase(username)) {
                isAdmin = true;
            }
        }

        if (!loggedIn) {
            request.setAttribute("msg", "请先登录后再访问该功能。");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        if (!isAdmin) {
            request.setAttribute("msg", "权限不足：只有管理员可以访问“查询所有用户”。");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        // 校验通过
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        // 可选：销毁日志
    }
}
