package com.web.servlet;

import com.web.entity.User;
import com.web.service.UserService;
import com.web.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/updateUserById")
public class UpdateUserByIdServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        // 可选：CSRF 简单校验
        String formToken = request.getParameter("csrfToken");
        String sessionToken = (String) request.getSession().getAttribute("csrfToken");
        if (sessionToken != null && (formToken == null || !sessionToken.equals(formToken))) {
            response.sendRedirect(request.getContextPath() + "/showUsers?msg=CSRF校验失败");
            return;
        }

        String idStr = request.getParameter("id");
        String username = request.getParameter("username");
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (idStr == null || username == null || username.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/showUsers?msg=参数不完整");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            // 取库里当前用户（为了校验旧密码 & 回显）
            User dbUser = userService.getById(id);
            if (dbUser == null) {
                response.sendRedirect(request.getContextPath() + "/showUsers?msg=用户不存在");
                return;
            }

            // 封装要更新的数据
            User toUpdate = new User();
            toUpdate.setId(id);
            toUpdate.setUsername(username.trim());

            boolean wantChangePwd = newPassword != null && !newPassword.trim().isEmpty();

            if (wantChangePwd) {
                // 1) 两次一致
                if (confirmPassword == null || !newPassword.equals(confirmPassword)) {
                    request.setAttribute("error", "两次输入的新密码不一致");
                    request.setAttribute("user", dbUser);
                    request.getRequestDispatcher("/Lab3/updateUser.jsp").forward(request, response);
                    return;
                }
                // 2) 校验旧密码
                if (currentPassword == null || !currentPassword.equals(dbUser.getPassword())) {
                    request.setAttribute("error", "当前密码不正确");
                    request.setAttribute("user", dbUser);
                    request.getRequestDispatcher("/Lab3/updateUser.jsp").forward(request, response);
                    return;
                }
                // 3) 设置新密码
                toUpdate.setPassword(newPassword);
            }

            boolean ok = userService.update(toUpdate);
            if (ok) {
                response.sendRedirect(request.getContextPath() + "/showUsers?msg=修改成功");
            } else {
                response.sendRedirect(request.getContextPath() + "/showUsers?msg=修改失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/showUsers?msg=修改异常");
        }
    }
}
