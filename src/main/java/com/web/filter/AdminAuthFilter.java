package com.web.filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
/**
 * 仅允许管理员访问 /showUsers
 * 方案：管理员判定来自 session.isAdmin（在 LoginServlet 中基于用户名=admin设置）
 */
@WebFilter(urlPatterns = {"/showUsers"}) // 如需再限制其它接口，直接加到数组里
public class AdminAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // no-op
    }
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        // 1) 登录校验
        Integer userId = (session == null) ? null : (Integer) session.getAttribute("userId");
        if (userId == null) {
            // 未登录 -> 登录页
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        // 2) 管理员校验（LoginServlet 已按用户名=admin 写入 isAdmin）
        boolean isAdmin = session.getAttribute("isAdmin") instanceof Boolean
                && (Boolean) session.getAttribute("isAdmin");

        if (!isAdmin) {
            // 非管理员 -> 回首页并提示
            request.setAttribute("msg", "权限不足：只有管理员可以访问“查询所有用户”。");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }
        // 通过
        chain.doFilter(req, resp);
    }
    @Override
    public void destroy() {
        // no-op
    }
}
