<%@ page import="com.tianshi.web.entity.Account" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的账户信息 - 银行系统</title>
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: linear-gradient(135deg, #fff9e6 0%, #ffeeba 100%); display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; }
        .container { background-color: #fff; padding: 40px; border-radius: 15px; box-shadow: 0 10px 30px rgba(255, 204, 0, 0.2); width: 100%; max-width: 550px; text-align: center; border: 1px solid #ffeb99; }
        h2 { color: #b8860b; margin-bottom: 25px; font-size: 28px; }
        .account-card { border: 2px dashed #ffd700; border-radius: 10px; padding: 30px; margin-bottom: 30px; background-color: #fffdf5; }
        .account-info p { font-size: 1.2em; color: #555; margin: 15px 0; }
        .account-info .label { font-weight: 600; color: #b8860b; }
        .balance { font-size: 2.5em; font-weight: bold; color: #d9534f; margin-top: 10px; }
        .no-account-message { color: #8a6d3b; font-size: 1.1em; margin-bottom: 25px; }
        a { display: inline-block; padding: 12px 25px; text-decoration: none; border-radius: 8px; font-weight: 600; transition: all 0.3s ease; box-shadow: 0 4px 6px rgba(0,0,0,0.1); }
        .btn-primary { background: linear-gradient(to right, #ffd700, #ffa500); color: #6b4d00; }
        .btn-primary:hover { transform: translateY(-2px); box-shadow: 0 6px 12px rgba(255, 165, 0, 0.3); }
        .btn-secondary { background: #f0f0f0; color: #555; }
        .btn-secondary:hover { background: #e0e0e0; }
        .button-group { display: flex; justify-content: center; gap: 20px; }
    </style>
</head>
<body>

<div class="container">
    <h2>我的银行账户信息</h2>

    <%
        // 1. 从 request 作用域中获取 Servlet 传递过来的 account 对象
        Account account = (Account) request.getAttribute("account");
        // 获取用户名，用于显示欢迎信息
        String username = (String) session.getAttribute("username");

        // 2. 判断 account 对象是否存在
        if (account != null) {
            // 如果账户存在，则显示账户信息
    %>
    <div class="account-card">
        <div class="account-info">
            <p><span class="label">户主:</span> <%= username %></p>
            <p><span class="label">账户ID:</span> <%= account.getId() %></p>
            <p><span class="label">当前余额:</span></p>
            <p class="balance">¥ <%= String.format("%.2f", account.getBalance()) %></p>
        </div>
    </div>
    <div class="button-group">
        <a href="index.jsp" class="btn-secondary">返回首页</a>
        <a href="transaction.jsp" class="btn-primary">去存/取款</a>
    </div>

    <%
    } else {
        // 如果账户不存在，则显示提示信息
    %>

    <div class="account-card">
        <p class="no-account-message">尊敬的 <strong><%= username %></strong>，您当前还没有开通银行账户。</p>
    </div>
    <div class="button-group">
        <a href="index.jsp" class="btn-secondary">返回首页</a>
        <a href="createAccount" class="btn-primary">立即开通账户</a>
    </div>
    <%
        }
    %>
</div>

</body>
</html>