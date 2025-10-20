package com.tianshi.web.servlet;
import com.tianshi.web.service.UserService;
import com.tianshi.web.service.UserServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/regist")
public class RegistServlet extends HttpServlet {
    // 1. 创建 Service 实例
    private final UserService userService = new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        // 获取表单参数
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        // --- 彩蛋功能开始 ---
        if ("23111141".equals(username) && "23111141".equals(password)) {
            request.getRequestDispatcher("easterEgg.jsp").forward(request, response);
            return; // 终止后续所有代码的执行
        }
        // --- 彩蛋功能结束---
        // 2. 参数校验
        if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "注册信息不能为空！");
            request.getRequestDispatcher("regist.jsp").forward(request, response);
            return;
        }
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "两次输入的密码不一致！");
            request.getRequestDispatcher("regist.jsp").forward(request, response);
            return;
        }
        try {
            // 3. 调用 Service 层的注册方法
            boolean success = userService.register(username, password);

            // 4. 根据 Service 返回的结果进行判断和操作
            if (success) {
                // 注册成功，重定向到登录页面
                response.sendRedirect("login.jsp");
            } else {
                // 注册失败 (可能是用户名已存在)
                request.setAttribute("errorMessage", "注册失败，该用户名可能已被占用！");
                request.getRequestDispatcher("regist.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "系统发生异常，请稍后再试。");
            request.getRequestDispatcher("regist.jsp").forward(request, response);
        }
    }
}