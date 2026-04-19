package com.web.servlet;

import com.web.entity.User;
import com.web.service.UserService;
import com.web.service.UserServiceImpl;
import com.web.util.CsrfUtil;
import com.web.util.PasswordUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/updateUserById")
public class UpdateUserByIdServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        if (!CsrfUtil.isValid(request)) {
            response.sendRedirect(request.getContextPath() + "/showUsers?msg=CSRF+validation+failed");
            return;
        }

        String idStr = request.getParameter("id");
        String username = request.getParameter("username");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (idStr == null || username == null || username.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/showUsers?msg=Missing+required+parameters");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            User dbUser = userService.getById(id);
            if (dbUser == null) {
                response.sendRedirect(request.getContextPath() + "/showUsers?msg=User+not+found");
                return;
            }

            User toUpdate = new User();
            toUpdate.setId(id);
            toUpdate.setUsername(username.trim());

            boolean wantChangePwd = newPassword != null && !newPassword.trim().isEmpty();
            if (wantChangePwd) {
                if (confirmPassword == null || !newPassword.equals(confirmPassword)) {
                    forwardError(request, response, dbUser, "New passwords do not match");
                    return;
                }
                if (!PasswordUtil.verify(currentPassword, dbUser.getPassword())) {
                    forwardError(request, response, dbUser, "Current password is incorrect");
                    return;
                }
                toUpdate.setPassword(newPassword);
            }

            boolean ok = userService.update(toUpdate);
            response.sendRedirect(request.getContextPath()
                    + (ok ? "/showUsers?msg=Update+succeeded" : "/showUsers?msg=Update+failed"));
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/showUsers?msg=Update+failed");
        }
    }

    private void forwardError(HttpServletRequest request, HttpServletResponse response, User user, String error)
            throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("user", user);
        CsrfUtil.getOrCreateToken(request);
        request.getRequestDispatcher("/updateUser.jsp").forward(request, response);
    }
}
