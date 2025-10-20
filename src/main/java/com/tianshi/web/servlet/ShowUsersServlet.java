package com.tianshi.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/showUsers")
public class  ShowUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/bank?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
            String dbUser = "root";
            String dbPassword = "123123";

            conn = DriverManager.getConnection(url, dbUser, dbPassword);

            // (1) 编写JDBC代码实现查询所有用户信息
            String sql = "SELECT id, username FROM user"; //
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            // (3) 将用户信息显示到浏览器网页中
            out.println("<html><head><title>所有用户信息</title>");
            out.println("<style> table { border-collapse: collapse; width: 50%; margin: 20px; } th, td { border: 1px solid #ddd; padding: 8px; text-align: left; } th { background-color: #f2f2f2; } </style>");
            out.println("</head><body>");
            out.println("<h2>系统内所有用户信息</h2>");
            out.println("<table>");
            out.println("<tr><th>用户ID</th><th>用户名</th></tr>");

            // (2) 遍历结果集，获取用户信息
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("username") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<a href='index.jsp'>返回首页</a>");
            out.println("</body></html>");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("数据库查询失败: " + e.getMessage());
        } finally {
            // 释放资源
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}