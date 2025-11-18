package com.web.servlet;

import com.web.entity.Page;
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
 * 基于 Druid 的“按用户名模糊搜索用户信息” Servlet
 */
@WebServlet("/searchUser")
public class SearchUserServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String keyword = req.getParameter("username");
        List<User> users;

        if (keyword == null || keyword.trim().isEmpty()) {
            // 没填关键字：退回到全部（第一页）
            Page<User> page = userService.findUsersByPage(1, 10);
            req.setAttribute("page", page);
        } else {
            keyword = keyword.trim();
            users = userService.findByUsernameLike(keyword);

            // 搜索结果也封装成 Page，方便 JSP 统一使用 page.data
            Page<User> page = new Page<>();
            page.setTotal(users.size());
            page.setSize(users.size() == 0 ? 1 : users.size());
            page.setCurrentPage(1);
            page.setTotalPage(1);
            page.setData(users);

            req.setAttribute("page", page);
            req.setAttribute("searchKeyword", keyword);
        }

        req.getRequestDispatcher("/showUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
