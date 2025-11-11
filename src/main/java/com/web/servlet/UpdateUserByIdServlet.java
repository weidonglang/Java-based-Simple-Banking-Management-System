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

        // 可选：同步令牌防 CSRF（若你在登录后写入了 sessionScope.csrfToken）
        String formToken = request.getParameter("csrfToken");
        String sessionToken = (String) request.getSession().getAttribute("csrfToken");
        if (sessionToken != null && (formToken == null || !sessionToken.equals(formToken))) {
            response.sendRedirect(request.getContextPath() + "/showUsers?msg=CSRF校验失败");
            return;
        }

        String idStr = request.getParameter("id");
        String username = request.getParameter("username");
        if (idStr == null || username == null || username.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/showUsers?msg=参数不完整");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            User u = new User();
            u.setId(id);
            u.setUsername(username.trim());

            boolean ok = userService.update(u);
            if (ok) {
                // PRG：避免刷新重复提交
                response.sendRedirect(request.getContextPath() + "/showUsers?msg=修改成功");
            } else {
                response.sendRedirect(request.getContextPath() + "/showUsers?msg=修改失败");
            }
        } catch (Exception e) {
            response.sendRedirect(request.getContextPath() + "/showUsers?msg=修改异常");
        }
    }
}
