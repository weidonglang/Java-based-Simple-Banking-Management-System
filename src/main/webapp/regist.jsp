<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>用户注册 - 银行系统</title>
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
      position: relative;
    }

    body::before {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-image: url('data:image/svg+xml;utf8,<svg xmlns="http://www.w3.org/2000/svg" width="100" height="100" viewBox="0 0 100 100"><rect fill="none" width="100" height="100"/><path fill="%23ffd700" opacity="0.1" d="M20,20 L80,20 L80,80 L20,80 Z M30,30 L70,30 L70,70 L30,70 Z M40,40 L60,40 L60,60 L40,60 Z"/></svg>');
      background-size: 200px;
      opacity: 0.3;
      z-index: -1;
    }

    .container {
      background-color: #fff;
      padding: 40px;
      border-radius: 20px;
      box-shadow: 0 20px 40px rgba(255, 204, 0, 0.25);
      width: 100%;
      max-width: 480px;
      text-align: center;
      border: 1px solid #ffeb99;
      position: relative;
      overflow: hidden;
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }

    .container:hover {
      transform: translateY(-5px);
      box-shadow: 0 25px 50px rgba(255, 204, 0, 0.3);
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

    .container::after {
      content: "";
      position: absolute;
      bottom: 0;
      right: 0;
      width: 100px;
      height: 100px;
      background: radial-gradient(circle, rgba(255,215,0,0.1) 0%, rgba(255,215,0,0) 70%);
      z-index: 0;
    }

    .logo {
      margin-bottom: 25px;
      position: relative;
      z-index: 1;
    }

    .logo-icon {
      font-size: 52px;
      color: #ffd700;
      margin-bottom: 10px;
      filter: drop-shadow(0 2px 4px rgba(0,0,0,0.1));
    }

    h2 {
      color: #b8860b;
      margin-bottom: 10px;
      font-size: 28px;
      font-weight: 600;
      text-shadow: 1px 1px 2px rgba(0,0,0,0.1);
    }

    .subtitle {
      color: #8a6d3b;
      margin-bottom: 30px;
      font-size: 14px;
    }

    .form-group {
      margin-bottom: 22px;
      text-align: left;
      position: relative;
      z-index: 1;
    }

    label {
      display: block;
      margin-bottom: 8px;
      color: #b8860b;
      font-weight: 600;
      font-size: 14px;
      display: flex;
      align-items: center;
    }

    .label-icon {
      margin-right: 8px;
      font-size: 16px;
    }

    .input-container {
      position: relative;
    }

    input[type="text"], input[type="password"] {
      width: 100%;
      padding: 15px 15px 15px 45px;
      border: 1px solid #ffd700;
      border-radius: 10px;
      font-size: 16px;
      transition: all 0.3s ease;
      background-color: #fffdf5;
      box-shadow: inset 0 2px 4px rgba(0,0,0,0.05);
    }

    input[type="text"]:focus, input[type="password"]:focus {
      outline: none;
      border-color: #ffa500;
      box-shadow: 0 0 0 3px rgba(255, 165, 0, 0.2), inset 0 2px 4px rgba(0,0,0,0.05);
      transform: translateY(-2px);
    }

    .input-icon {
      position: absolute;
      left: 15px;
      top: 50%;
      transform: translateY(-50%);
      color: #ffa500;
      font-size: 18px;
    }

    .password-requirements {
      margin-top: 8px;
      font-size: 12px;
      color: #8a6d3b;
      padding-left: 20px;
    }

    input[type="submit"] {
      width: 100%;
      padding: 16px;
      background: linear-gradient(to right, #ffd700, #ffa500);
      color: #6b4d00;
      border: none;
      border-radius: 10px;
      cursor: pointer;
      font-size: 16px;
      font-weight: 600;
      transition: all 0.3s ease;
      margin-top: 15px;
      box-shadow: 0 6px 12px rgba(255, 165, 0, 0.2);
      position: relative;
      overflow: hidden;
      z-index: 1;
    }

    input[type="submit"]::before {
      content: "";
      position: absolute;
      top: 0;
      left: -100%;
      width: 100%;
      height: 100%;
      background: linear-gradient(90deg, transparent, rgba(255,255,255,0.4), transparent);
      transition: left 0.5s;
      z-index: -1;
    }

    input[type="submit"]:hover {
      background: linear-gradient(to right, #ffa500, #ff8c00);
      transform: translateY(-3px);
      box-shadow: 0 10px 20px rgba(255, 165, 0, 0.3);
    }

    input[type="submit"]:hover::before {
      left: 100%;
    }

    .login-link {
      text-align: center;
      margin-top: 30px;
      padding-top: 20px;
      border-top: 1px dashed #ffd700;
      position: relative;
      z-index: 1;
    }

    .login-link p {
      color: #666;
      margin-bottom: 15px;
    }

    .login-link a {
      display: inline-block;
      padding: 12px 24px;
      background: linear-gradient(to right, #ffed4e, #ffdb4d);
      color: #6b4d00;
      text-decoration: none;
      border-radius: 10px;
      font-weight: 600;
      transition: all 0.3s ease;
      box-shadow: 0 4px 8px rgba(255, 219, 77, 0.2);
      position: relative;
      overflow: hidden;
    }

    .login-link a::before {
      content: "";
      position: absolute;
      top: 0;
      left: -100%;
      width: 100%;
      height: 100%;
      background: linear-gradient(90deg, transparent, rgba(255,255,255,0.4), transparent);
      transition: left 0.5s;
    }

    .login-link a:hover {
      background: linear-gradient(to right, #ffdb4d, #ffc800);
      transform: translateY(-2px);
      box-shadow: 0 8px 16px rgba(255, 219, 77, 0.3);
    }

    .login-link a:hover::before {
      left: 100%;
    }

    .copyright {
      margin-top: 30px;
      padding-top: 15px;
      border-top: 1px dashed #ffd700;
      color: #b8860b;
      font-size: 14px;
      position: relative;
      z-index: 1;
    }

    @media (max-width: 480px) {
      .container {
        padding: 30px 20px;
      }

      h2 {
        font-size: 24px;
      }

      input[type="text"], input[type="password"] {
        padding: 12px 12px 12px 40px;
      }
    }

    /* 添加一些微妙的动画效果 */
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(20px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .container {
      animation: fadeIn 0.5s ease-out;
    }
  </style>
</head>
<body>

<div class="container">
  <div class="logo">
    <div class="logo-icon">🏦</div>
    <h2>银行系统用户注册</h2>
    <p class="subtitle">创建您的银行账户，享受安全便捷的金融服务</p>
  </div>

  <%
    // 从 request 中获取 Servlet 传递过来的错误信息
    String errorMessage = (String) request.getAttribute("errorMessage");
    // 如果错误信息不为空，就在页面上显示它
    if (errorMessage != null) {
  %>
  <div style="color: #D8000C; background-color: #FFD2D2; padding: 10px; border-radius: 5px; margin-bottom: 20px; font-weight: bold;">
    <%= errorMessage %>
  </div>
  <%
    }
  %>
  <form action="regist" method="post">
    <div class="form-group">
      <label for="username">
        <span class="label-icon">👤</span>用户名:
      </label>
      <div class="input-container">
        <span class="input-icon">👤</span>
        <input type="text" id="username" name="username" required placeholder="请输入用户名（4-16位字母或数字）">
      </div>
    </div>

    <div class="form-group">
      <label for="password">
        <span class="label-icon">🔒</span>密码:
      </label>
      <div class="input-container">
        <span class="input-icon">🔒</span>
        <input type="password" id="password" name="password" required placeholder="请输入密码（至少8位，包含字母和数字）">
      </div>
      <div class="password-requirements">密码需包含字母和数字，长度至少8位</div>
    </div>

    <div class="form-group">
      <label for="confirmPassword">
        <span class="label-icon">✅</span>确认密码:
      </label>
      <div class="input-container">
        <span class="input-icon">✅</span>
        <input type="password" id="confirmPassword" name="confirmPassword" required placeholder="请再次输入密码">
      </div>
    </div>

    <div class="form-group">
      <input type="submit" value="注册账户">
    </div>
  </form>

  <div class="login-link">
    <p>已有银行账户?</p>
    <a href="login.jsp">立即登录</a>
  </div>

  <div class="copyright">
    版权所有 © 计科6班 魏语石 23111141
  </div>
</div>

</body>
</html>