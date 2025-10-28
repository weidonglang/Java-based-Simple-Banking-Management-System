package com.web.servlet;

import com.web.entity.Account;
import com.web.service.AccountService;
import com.web.service.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/showAccount")
public class ShowAccountServlet extends HttpServlet {

    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // 验证用户是否登录
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 1. 调用 Service 层获取账户信息
            Account account = accountService.getAccountByUserId(userId);

            // 2. 将账户信息（可能为 null）存入 request 作用域
            request.setAttribute("account", account);

            // 3. 请求转发到 JSP 页面进行展示
            request.getRequestDispatcher("/showAccount.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // 转发到一个统一的错误页面
            request.setAttribute("errorMessage", "查询账户信息时发生系统错误。");
            request.getRequestDispatcher("/error.jsp").forward(request, response); // 假设您有一个 error.jsp 页面
        }
    }
}