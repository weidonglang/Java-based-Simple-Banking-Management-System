package com.tianshi.web.servlet;

import com.tianshi.web.entity.User;
import com.tianshi.web.service.UserService;
import com.tianshi.web.service.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // 1) 读取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // --- 控制台：登录尝试 ---
        logConsole("LOGIN TRY",
                "Username=" + safe(username),
                "ClientIP=" + getClientIp(request),
                "User-Agent=" + safe(request.getHeader("User-Agent")));

        // 2) 业务校验
        UserService userService = new UserServiceImpl();
        User user = userService.login(username, password);

        // 3) 分支处理
        if (user != null) {
            // 登录成功：写入 session
            HttpSession session = request.getSession();
            session.setAttribute("username", user.getUsername());
            session.setAttribute("userId", user.getId());
            // 如需在其他页面取整对象，也可顺手塞一份（不影响原逻辑）
            session.setAttribute("loginUser", user);

            // --- 控制台：登录成功 ---
            logConsole("LOGIN SUCCESS",
                    "SessionId=" + session.getId(),
                    "Username=" + safe(user.getUsername()),
                    "UserId=" + user.getId(),
                    "ClientIP=" + getClientIp(request),
                    "User-Agent=" + safe(request.getHeader("User-Agent")));

            // 4) 重定向到首页
            response.sendRedirect("index.jsp");
        } else {
            // --- 控制台：登录失败 ---
            logConsole("LOGIN FAILED",
                    "Username=" + safe(username),
                    "ClientIP=" + getClientIp(request));

            // 设置错误信息并转发回登录页
            request.setAttribute("errorMessage", "用户名或密码错误！");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 一般 GET 展示登录页，也可改为 doPost
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    /* ==================== 工具方法 ==================== */

    private static void logConsole(String title, String... kvs) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println("[" + now + "] " + title);
        for (String s : kvs) {
            System.out.println("  " + s);
        }
        System.out.println("-----------------------------------------------------");
    }

    private static String getClientIp(HttpServletRequest request) {
        String ip = headerFirstIp(request.getHeader("X-Forwarded-For"));
        if (notEmpty(ip)) return ip;
        ip = request.getHeader("X-Real-IP");
        if (notEmpty(ip)) return ip.trim();
        return request.getRemoteAddr();
    }

    private static String headerFirstIp(String headerVal) {
        if (headerVal == null || headerVal.isEmpty() || "unknown".equalsIgnoreCase(headerVal)) return null;
        int idx = headerVal.indexOf(',');
        return (idx != -1) ? headerVal.substring(0, idx).trim() : headerVal.trim();
    }

    private static boolean notEmpty(String s) { return s != null && !s.isEmpty(); }
    private static String safe(String s) { return s == null ? "(null)" : s; }
}
