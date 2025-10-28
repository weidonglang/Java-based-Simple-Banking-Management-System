package com.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

/**
 * ShowUsersServlet（JSP 渲染版 + 强化调试输出）
 * - 查询所有用户：SELECT id, username FROM user
 * - 将结果放入 request.setAttribute("users", List<Map<String,Object>>)
 * - 转发到 /showUsers.jsp 来渲染表格
 *
 * 调试要点：
 * 1) 在响应头里加入 X-Handler: 当前处理该请求的 Servlet 类名
 * 2) 在控制台（Tomcat 控制台/IDE Run 窗口）打印详细的步骤与数据
 *
 * 重要：
 * 1) 项目中只能有一个 /showUsers 的映射（@WebServlet 或 web.xml），避免命中旧类
 * 2) Servlet 内不再使用 out.println(...) 拼 HTML，防止 forward 失效
 */
@WebServlet("/showUsers") // 如使用 web.xml 做唯一映射，请去掉本注解
public class ShowUsersServlet extends HttpServlet {

    // ======== 按你原来的数据库配置 ========
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String JDBC_URL =
            "jdbc:mysql://localhost:3306/bank?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "123123";

    // ======== 小工具：时间戳 + 统一调试输出 ========
    private static String now() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new java.util.Date());
    }
    private static void debug(String tag, String msg) {
        System.out.println("[" + now() + "][ShowUsersServlet][" + tag + "] " + msg);
    }
    private static void debugf(String tag, String fmt, Object... args) {
        debug(tag, String.format(Locale.ROOT, fmt, args));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        long t0 = System.currentTimeMillis();
        // 探针：让你在浏览器的 Network 响应头中能看到哪个类处理了请求
        response.addHeader("X-Handler", getClass().getName());

        // 基本请求信息（方便确认是否命中新部署/新映射）
        String uri = request.getRequestURI();
        String ctx = request.getContextPath();
        HttpSession session = request.getSession(false);
        String sessionId = (session == null) ? "NO-SESSION" : session.getId();
        debugf("REQ", "ctx=%s, uri=%s, session=%s, remote=%s",
                ctx, uri, sessionId, request.getRemoteAddr());

        // ======== DB 查询 ========
        List<Map<String, Object>> users = new ArrayList<>();
        String sql = "SELECT id, username FROM user";

        try {
            // 1) 加载驱动
            Class.forName(JDBC_DRIVER);
            debug("DB", "JDBC Driver loaded: " + JDBC_DRIVER);

            // 2) 建立连接并执行查询（使用 try-with-resources 自动释放）
            try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                debugf("DB", "Connected. url=%s, user=%s", JDBC_URL, JDBC_USER);
                debug("SQL", sql);

                // 3) 遍历结果集 -> 填充列表
                while (rs.next()) {
                    Map<String, Object> row = new HashMap<>();
                    int id = rs.getInt("id");
                    String username = rs.getString("username");
                    row.put("id", id);
                    row.put("username", username);
                    users.add(row);

                    // 每行数据都打印，便于核对是否是“最新”
                    debugf("ROW", "id=%d, username=%s", id, username);
                }
                debugf("COUNT", "users.size=%d", users.size());
            }

            // 4) 放入 request，并转发到 JSP
            request.setAttribute("users", users);
            debug("FWD", "forward to /showUsers.jsp");
            request.getRequestDispatcher("/showUsers.jsp").forward(request, response);

        } catch (ClassNotFoundException e) {
            debug("ERROR", "JDBC Driver not found: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("msg", "数据库驱动未找到：" + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (SQLException e) {
            debug("ERROR", "SQL exception: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("msg", "数据库查询失败：" + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } catch (Exception e) {
            debug("ERROR", "Unexpected: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("msg", "系统异常：" + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);

        } finally {
            long t1 = System.currentTimeMillis();
            debugf("TIME", "done in %d ms", (t1 - t0));
        }
    }

    // 如需支持 POST，同步到 GET 即可
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
