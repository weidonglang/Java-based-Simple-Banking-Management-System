package com.web.servlet;

import com.web.entity.User;
import com.web.service.UserService;
import com.web.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登录 Servlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        // 直接转发到登录页面
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userService.login(username, password);
        if (user == null) {
            // 登录失败
            request.setAttribute("msg", "用户名或密码错误！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // 登录成功，写入 session
        HttpSession session = request.getSession(true);
        session.setAttribute("loginUser", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());

        // 简单判断管理员：用户名为 admin 的视为管理员
        boolean isAdmin = "admin".equalsIgnoreCase(user.getUsername());
        session.setAttribute("isAdmin", isAdmin);

        // 跳转到首页
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }
}
