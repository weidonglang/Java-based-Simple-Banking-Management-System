package com.web.servlet;

import com.web.service.UserService;
import com.web.service.UserServiceImpl;
import com.web.util.CsrfUtil;

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
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "Use POST for deleteUserById");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        if (!CsrfUtil.isValid(req)) {
            redirectWithMsg(req, resp, "CSRF validation failed");
            return;
        }

        String idStr = req.getParameter("id");
        System.out.println("[DEL] hit /deleteUserById id=" + idStr);
        String msg;
        boolean ok = false;
        try {
            int id = Integer.parseInt(idStr);
            ok = userService.deleteUserDeep(id);
            msg = ok ? "Delete succeeded" : "No user was deleted";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "Delete failed";
        }
        System.out.println("[DEL] result ok=" + ok + ", msg=" + msg);
        redirectWithMsg(req, resp, msg);
    }

    private void redirectWithMsg(HttpServletRequest req, HttpServletResponse resp, String msg) throws IOException {
        String encoded = URLEncoder.encode(msg, StandardCharsets.UTF_8.name());
        resp.sendRedirect(req.getContextPath() + "/showUsers?msg=" + encoded);
    }
}
