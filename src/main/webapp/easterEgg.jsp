<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>神秘彩蛋 - 银行系统</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=ZCOOL+KuaiLe&display=swap');
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    body {
      font-family: 'ZCOOL KuaiLe', cursive, Arial, sans-serif;
      background: linear-gradient(135deg, #FFD700 0%, #FFA500 100%);
      color: #333;
      display: flex;
      justify-content: center;
      align-items: center;
      min-height: 100vh;
      padding: 20px;
      text-align: center;
    }

    .container {
      background-color: rgba(255, 255, 255, 0.9);
      padding: 50px 70px;
      border-radius: 20px;
      box-shadow: 0 15px 30px rgba(0, 0, 0, 0.15);
      max-width: 600px;
      width: 100%;
      border: 3px solid #FFD700;
      position: relative;
      overflow: hidden;
    }

    .container::before {
      content: "";
      position: absolute;
      top: -10px;
      left: -10px;
      right: -10px;
      bottom: -10px;
      background: linear-gradient(45deg, #FFD700, #FFA500, #FFD700, #FFA500);
      z-index: -1;
      filter: blur(20px);
      opacity: 0.7;
    }

    h1 {
      font-size: 3.5em;
      text-shadow: 2px 2px 4px rgba(255, 215, 0, 0.5);
      color: #B8860B;
      margin-bottom: 20px;
      position: relative;
      display: inline-block;
    }

    h1::after {
      content: "";
      position: absolute;
      bottom: -5px;
      left: 10%;
      width: 80%;
      height: 3px;
      background: linear-gradient(to right, transparent, #FFD700, transparent);
    }

    p {
      font-size: 1.8em;
      margin-bottom: 30px;
      color: #555;
      line-height: 1.5;
    }

    .egg-icon {
      font-size: 4em;
      margin: 20px 0;
      color: #FFD700;
      text-shadow: 0 0 10px rgba(255, 215, 0, 0.5);
      animation: bounce 2s infinite;
    }

    @keyframes bounce {
      0%, 100% { transform: translateY(0); }
      50% { transform: translateY(-10px); }
    }

    a {
      display: inline-block;
      margin-top: 20px;
      padding: 12px 30px;
      background: linear-gradient(to bottom, #FFD700, #FFA500);
      color: #333;
      text-decoration: none;
      border-radius: 8px;
      transition: all 0.3s ease;
      font-size: 1.2em;
      border: 2px solid #FFA500;
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
      font-weight: bold;
    }

    a:hover {
      transform: translateY(-3px);
      box-shadow: 0 6px 12px rgba(0, 0, 0, 0.25);
      background: linear-gradient(to bottom, #FFA500, #FF8C00);
    }

    .copyright {
      margin-top: 40px;
      padding-top: 20px;
      border-top: 1px dashed #FFD700;
      font-size: 1em;
      color: #666;
    }

    .bank-logo {
      position: absolute;
      top: 20px;
      right: 20px;
      font-size: 1.5em;
      color: #B8860B;
      font-weight: bold;
    }

    .gold-coin {
      position: absolute;
      width: 40px;
      height: 40px;
      background: radial-gradient(circle at 30% 30%, #FFD700, #B8860B);
      border-radius: 50%;
      box-shadow: 0 0 10px #FFD700;
      animation: float 5s infinite ease-in-out;
    }

    .coin1 {
      top: 10%;
      left: 10%;
      animation-delay: 0s;
    }

    .coin2 {
      top: 20%;
      right: 15%;
      animation-delay: 1s;
    }

    .coin3 {
      bottom: 15%;
      left: 15%;
      animation-delay: 2s;
    }

    @keyframes float {
      0%, 100% { transform: translateY(0) rotate(0deg); }
      50% { transform: translateY(-15px) rotate(180deg); }
    }

    @media (max-width: 768px) {
      .container {
        padding: 30px 20px;
      }

      h1 {
        font-size: 2.5em;
      }

      p {
        font-size: 1.4em;
      }
    }
  </style>
</head>
<body>
<div class="gold-coin coin1"></div>
<div class="gold-coin coin2"></div>
<div class="gold-coin coin3"></div>

<div class="container">
  <div class="bank-logo">银行系统</div>

  <div class="egg-icon">🥚</div>

  <h1>恭喜您！</h1>
  <p>您发现了银行系统的隐藏彩蛋！</p>
  <p>感谢您使用我们的银行服务</p>

  <a href="login.jsp">返回登录页面</a>

  <div class="copyright">
    版权所有 © 计科6班 魏语石 23111141
  </div>
</div>
</body>
</html>