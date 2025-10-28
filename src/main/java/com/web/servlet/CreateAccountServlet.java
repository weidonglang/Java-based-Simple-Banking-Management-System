package com.web.servlet;

import com.web.service.AccountService;
import com.web.service.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/createAccount")
public class CreateAccountServlet extends HttpServlet {

    private final AccountService accountService = new AccountServiceImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        // 1. 验证用户是否登录
        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // 为了显示结果，我们创建一个新的JSP页面
        String forwardPage = "/accountResult.jsp";
        String messageTitle;
        String messageBody;

        try {
            // 2. 调用 Service 层处理业务逻辑
            int resultCode = accountService.createAccount(userId);

            // 3. 根据 Service 返回的结果码设置不同的提示信息
            if (resultCode == 1) {
                messageTitle = "恭喜您！";
                messageBody = "银行账户开通成功，初始余额为 0.0 元。";
            } else if (resultCode == -1) {
                messageTitle = "操作提醒";
                messageBody = "您已经拥有一个银行账户，无需重复开通。";
            } else {
                messageTitle = "系统错误";
                messageBody = "账户开通失败，请联系管理员。";
            }
        } catch (Exception e) {
            e.printStackTrace();
            messageTitle = "系统异常";
            messageBody = "处理您的请求时发生错误，请稍后再试。";
        }

        // 4. 将提示信息存入 request，并转发到结果页面
        request.setAttribute("messageTitle", messageTitle);
        request.setAttribute("messageBody", messageBody);
        request.getRequestDispatcher(forwardPage).forward(request, response);
    }
}