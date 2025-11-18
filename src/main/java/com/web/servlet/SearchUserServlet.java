package com.web.servlet;

import com.web.entity.User;
import com.web.service.UserService;
import com.web.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 基于 Druid 连接池的“按用户名模糊搜索用户信息” Servlet
 */
@WebServlet("/searchUser")
public class SearchUserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String keyword = req.getParameter("username");
        List<User> users;

        if (keyword == null || keyword.trim().isEmpty()) {
            // 没有关键词就查询全部
            users = userService.findAllUsers();
        } else {
            users = userService.findByUsernameLike(keyword.trim());
        }

        // 把数据放入 request 作用域
        req.setAttribute("users", users);
        req.setAttribute("searchKeyword", keyword);

        // 仍然复用 showUsers.jsp 进行展示
        req.getRequestDispatcher("/showUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doGet(req, resp);
    }
}
