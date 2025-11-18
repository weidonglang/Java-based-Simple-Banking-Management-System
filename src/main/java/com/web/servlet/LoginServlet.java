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
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserService userService = new UserServiceImpl();
        User user = userService.login(username, password);

        if (user == null) {
            request.setAttribute("msg", "用户名或密码错误！");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }

        // 登录成功
        HttpSession session = request.getSession(true);
        session.setAttribute("loginUser", user);
        session.setAttribute("userId", user.getId());
        session.setAttribute("username", user.getUsername());

        // [ADMIN-PATCH] 方案A：临时管理员规则——用户名等于 admin 即视为管理员
        boolean isAdmin = "admin".equalsIgnoreCase(user.getUsername());
        session.setAttribute("isAdmin", isAdmin);

        // 回首页
        response.sendRedirect(request.getContextPath() + "/index.jsp");
    }

    /**
     * 基于 Druid 连接池的“按用户名模糊搜索用户信息” Servlet
     */

    
}
