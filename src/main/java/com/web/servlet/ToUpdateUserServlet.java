package com.web.servlet;

import com.web.entity.User;
import com.web.service.UserService;
import com.web.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/toUpdateUser")
public class ToUpdateUserServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idStr = request.getParameter("id");
        try {
            int id = Integer.parseInt(idStr);
            User user = userService.getById(id);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/updateUser.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("msg", "参数无效或用户不存在");
            request.getRequestDispatcher("/showUsers").forward(request, response);
        }
    }
}

