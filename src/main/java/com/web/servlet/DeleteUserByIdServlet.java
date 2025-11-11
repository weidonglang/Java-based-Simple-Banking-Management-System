package com.web.servlet;

import com.web.service.UserService;
import com.web.service.UserServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/deleteUserById")
public class DeleteUserByIdServlet extends HttpServlet {
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idStr = req.getParameter("id");
        System.out.println("[DEL] hit /deleteUserById id=" + idStr);

        String msg;
        boolean ok = false;
        try {
            int id = Integer.parseInt(idStr);
            // ✅ 改为手动级联删除
            ok = userService.deleteUserDeep(id);
            msg = ok ? "删除成功" : "未删除任何记录（可能ID不存在或已回滚）";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "删除异常";
        }

        System.out.println("[DEL] result ok=" + ok + ", msg=" + msg);
        String encoded = URLEncoder.encode(msg, StandardCharsets.UTF_8.name());
        resp.sendRedirect(req.getContextPath() + "/showUsers?msg=" + encoded);
    }
}
