package com.web.filter;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 仅允许管理员访问 /showUsers 及相关管理接口
 */
@WebFilter(urlPatterns = {"/showUsers", "/deleteUserById", "/toUpdateUser", "/updateUserById"})
public class AdminAuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { /* no-op */ }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request  = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        // 1) 登录校验
        Integer userId = (session == null) ? null : (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2) 管理员校验
        Boolean adminFlag = (session == null) ? null : (Boolean) session.getAttribute("isAdmin");
        boolean isAdmin = adminFlag != null && adminFlag;
        if (!isAdmin) {
            request.setAttribute("msg", "权限不足：只有管理员可以访问该页面。");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() { /* no-op */ }
}
