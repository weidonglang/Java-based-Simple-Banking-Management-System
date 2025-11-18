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

/**
 * 分页查询用户列表的 Servlet
 */
@WebServlet("/showUsersByPage")
public class ShowUsersByPageServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        int currentPage = 1; // 默认第一页
        String cpStr = req.getParameter("currentPage");
        if (cpStr != null && cpStr.trim().length() > 0) {
            try {
                currentPage = Integer.parseInt(cpStr.trim());
            } catch (NumberFormatException ignored) {}
        }

        Page<User> page = userService.findUsersByPage(currentPage, PAGE_SIZE);
        req.setAttribute("page", page);

        String msg = req.getParameter("msg");
        if (msg != null && !msg.trim().isEmpty()) {
            req.setAttribute("msg", msg.trim());
        }

        req.getRequestDispatcher("/showUsers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
