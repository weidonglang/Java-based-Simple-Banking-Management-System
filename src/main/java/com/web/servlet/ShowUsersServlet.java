package com.web.servlet;

import com.web.entity.Page;
import com.web.entity.User;
import com.web.service.UserService;
import com.web.service.UserServiceImpl;
import com.web.util.CsrfUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 展示用户列表（默认第 1 页）
 * 方便从 /showUsers 入口访问。
 */
@WebServlet("/showUsers")
public class ShowUsersServlet extends HttpServlet {

    private final UserService userService = new UserServiceImpl();
    private static final int PAGE_SIZE = 10;

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse resp) throws ServletException, IOException {

        Page<User> page = userService.findUsersByPage(1, PAGE_SIZE);
        req.setAttribute("page", page);
        CsrfUtil.getOrCreateToken(req);

        // 如果有 msg 之类的提示，可以从参数带过来
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
