package com.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false); // 获取现有session，如果不存在则不创建

        if (session != null && session.getAttribute("username") != null) {
           // 销毁session对象 [cite: 50]
            session.invalidate();
            // 响应”用户已退出登录！”到浏览器网页 [cite: 50]
            response.getWriter().println("<h2>用户已退出登录！</h2><a href='login.jsp'>返回登录页面</a>");
        } else {
            // 响应”用户未登录！”到浏览器网页 [cite: 49]
            response.getWriter().println("<h2>用户未登录！</h2><a href='login.jsp'>去登录</a>");
        }
    }
}