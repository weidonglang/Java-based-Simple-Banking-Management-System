package com.tianshi.web.servlet;

import com.tianshi.web.service.AccountService;
import com.tianshi.web.service.AccountServiceImpl;
import com.tianshi.web.service.TransactionResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // 验证登录
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 1. 获取并校验表单参数
        String type = request.getParameter("type");
        String amountStr = request.getParameter("amount");
        double amount;

        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                // 如果校验失败，设置错误信息并转发回原页面
                request.setAttribute("errorMessage", "操作失败：金额必须为正数。");
                request.getRequestDispatcher("transaction.jsp").forward(request, response);
                return;
            }
        } catch (NumberFormatException e) {
            request.setAttribute("errorMessage", "操作失败：金额格式不正确。");
            request.getRequestDispatcher("transaction.jsp").forward(request, response);
            return;
        }

        // 2. 调用 Service 层执行包含事务的业务
        TransactionResult result = accountService.performTransaction(userId, amount, type);

        // 3. 根据 Service 返回的结果，准备要在结果页面上显示的信息
        String messageTitle;
        String messageBody;

        if (result.isSuccess()) {
            messageTitle = "操作成功！";
            // 使用 String.format 格式化金额，保留两位小数
            messageBody = result.getMessage() + " 您当前的余额为: ¥ " + String.format("%.2f", result.getNewBalance());
        } else {
            messageTitle = "操作失败";
            messageBody = result.getMessage();
        }

        // 4. 将结果信息存入 request，并转发到统一的结果展示页面
        request.setAttribute("messageTitle", messageTitle);
        request.setAttribute("messageBody", messageBody);
        request.getRequestDispatcher("/accountResult.jsp").forward(request, response);
    }
}