<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>银行系统首页</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
      background: linear-gradient(135deg, #fff9e6 0%, #ffeeba 100%);
      min-height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      padding: 20px;
    }

    .container {
      background-color: #fff;
      padding: 40px;
      border-radius: 15px;
      box-shadow: 0 10px 30px rgba(255, 204, 0, 0.2);
      width: 100%;
      max-width: 600px;
      text-align: center;
      border: 1px solid #ffeb99;
      position: relative;
      overflow: hidden;
    }

    .container::before {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 5px;
      background: linear-gradient(90deg, #ffd700, #ffa500, #ffd700);
    }

    h2 {
      color: #b8860b;
      margin-bottom: 20px;
      font-size: 28px;
      font-weight: 600;
      text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
    }

    .welcome-text {
      color: #666;
      margin-bottom: 30px;
      font-size: 16px;
      line-height: 1.5;
    }

    .button-group {
      display: flex;
      flex-direction: column;
      gap: 15px;
      margin-bottom: 30px;
    }

    a {
      display: block;
      padding: 15px 20px;
      text-decoration: none;
      border-radius: 8px;
      font-weight: 600;
      font-size: 16px;
      transition: all 0.3s ease;
      position: relative;
      overflow: hidden;
      box-shadow: 0 4px 6px rgba(0,0,0,0.1);
    }

    a::after {
      content: "";
      position: absolute;
      top: 0;
      left: -100%;
      width: 100%;
      height: 100%;
      background: linear-gradient(90deg, transparent, rgba(255,255,255,0.4), transparent);
      transition: left 0.5s;
    }

    a:hover::after {
      left: 100%;
    }

    a:hover {
      transform: translateY(-2px);
    }

    .create-account {
      background: linear-gradient(to right, #ffd700, #ffa500);
      color: #6b4d00;
      border: 1px solid #ffc107;
    }
    .create-account:hover {
      box-shadow: 0 6px 12px rgba(255, 165, 0, 0.3);
    }

    .account-info {
      background: linear-gradient(to right, #ffed4e, #ffdb4d);
      color: #6b4d00;
      border: 1px solid #ffd700;
    }
    .account-info:hover {
      box-shadow: 0 6px 12px rgba(255, 219, 77, 0.3);
    }

    .transaction {
      background: linear-gradient(to right, #ffc107, #ff9800);
      color: #6b4d00;
      border: 1px solid #ffa000;
    }
    .transaction:hover {
      box-shadow: 0 6px 12px rgba(255, 152, 0, 0.3);
    }

    /* --- 新增的样式 --- */
    .show-users {
      background: linear-gradient(to right, #d3d3d3, #a9a9a9);
      color: #333;
      border: 1px solid #b0b0b0;
    }
    .show-users:hover {
      box-shadow: 0 6px 12px rgba(169, 169, 169, 0.3);
    }
    /* --- 新增样式结束 --- */

    .logout {
      background: linear-gradient(to right, #ff6b00, #ff4500);
      color: white;
      border: 1px solid #ff4500;
    }
    .logout:hover {
      box-shadow: 0 6px 12px rgba(255, 69, 0, 0.3);
    }

    .copyright {
      margin-top: 30px;
      padding-top: 15px;
      border-top: 1px dashed #ffd700;
      color: #b8860b;
      font-size: 14px;
    }

    @media (max-width: 480px) {
      .container {
        padding: 25px 20px;
      }
      h2 {
        font-size: 24px;
      }
      a {
        padding: 12px 15px;
        font-size: 15px;
      }
    }
  </style>
</head>
<body>

<div class="container">
  <%
    String username = (String) session.getAttribute("username");
    if (username == null) {
      response.sendRedirect("login.jsp");
      return;
    }
  %>

  <h2>欢迎您, <%= username %>!</h2>
  <p class="welcome-text">请选择您要进行的操作，我们将为您提供安全、便捷的银行服务</p>

  <div class="button-group">
    <a href="createAccount" class="create-account">开通银行账户</a>
    <a href="showAccount" class="account-info">查询我的账户信息</a>
    <a href="transaction.jsp" class="transaction">办理存款/取款</a>
    <a href="showUsers" class="show-users">查询所有用户</a>
    <a href="logout" class="logout">退出登录</a>
  </div>

  <div class="copyright">
    版权所有 © 计科6班 魏语石 23111141
  </div>
</div>

</body>
</html>