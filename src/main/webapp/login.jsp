<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width,initial-scale=1,viewport-fit=cover" />
  <title>用户登录 - 银行系统</title>
  <style>
    /* ========== 主题变量（不改变功能，仅优化样式） ========== */
    :root{
      --bg-grad-start:#fff9e6;
      --bg-grad-end:#ffeeba;
      --gold-50:#fffdf5;
      --gold-100:#fffbde;
      --gold-200:#ffef99;
      --gold-300:#ffdf66;
      --gold-400:#ffd700;
      --gold-500:#ffa500;
      --gold-600:#ff8c00;
      --text-strong:#6b4d00;
      --text-gold:#b8860b;
      --text-muted:#666;
      --ring: 0 0 0 3px rgba(255,165,0,.22);
      --shadow-1: 0 10px 25px rgba(255,165,0,.18);
      --shadow-2: 0 15px 35px rgba(255,165,0,.22);
      --shadow-3: 0 6px 12px rgba(255,165,0,.28);
      --radius: 16px;
      --dur-fast:.18s;
      --dur:.3s;
      --dur-slow: 5s;
    }

    /* ========== 全局基础 ========== */
    *{ margin:0; padding:0; box-sizing:border-box; }
    html,body{ height:100%; }
    body{
      font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", "PingFang SC","Microsoft YaHei", Tahoma, Geneva, Verdana, sans-serif;
      background: linear-gradient(135deg,var(--bg-grad-start) 0%, var(--bg-grad-end) 100%);
      min-height: 100vh;
      display:flex; justify-content:center; align-items:center;
      padding:24px;
      position:relative;
      overflow-x:hidden;
    }

    /* 背景装饰气泡（纯装饰，不影响逻辑） */
    body::before, body::after{
      content:""; position:absolute; inset:auto;
      width:420px; height:420px; border-radius:50%;
      filter: blur(60px);
      opacity:.35; z-index:0; pointer-events:none;
      animation: float var(--dur-slow) ease-in-out infinite alternate;
    }
    body::before{
      left:-120px; top:-100px;
      background: radial-gradient(65% 65% at 50% 50%, #fff0b3 0%, #ffd861 60%, transparent 100%);
    }
    body::after{
      right:-140px; bottom:-120px;
      background: radial-gradient(60% 60% at 50% 50%, #ffe8a3 0%, #ffc04b 60%, transparent 100%);
      animation-delay: .8s;
    }

    @keyframes float{
      from{ transform: translateY(0) translateX(0) scale(1); }
      to  { transform: translateY(12px) translateX(8px) scale(1.03); }
    }

    /* ========== 容器卡片 ========== */
    .container{
      position:relative;
      z-index:1;
      width:100%; max-width:480px;
      background:#fff;
      border:1px solid #ffeb99;
      border-radius: var(--radius);
      padding:42px 40px 32px;
      box-shadow: var(--shadow-2);
      text-align:center;
      overflow:hidden;
      backdrop-filter: saturate(120%);
    }

    /* 顶部动效色带 */
    .container::before{
      content:""; position:absolute; left:0; top:0; width:100%; height:6px;
      background: linear-gradient(90deg, var(--gold-400), var(--gold-500), var(--gold-400));
      background-size: 200% 100%;
      animation: shimmer 3.6s linear infinite;
    }
    @keyframes shimmer{
      0%{ background-position: 0% 50%; }
      100%{ background-position: 200% 50%; }
    }

    .logo{ margin-bottom: 18px; }
    .logo-icon{ font-size:56px; color: var(--gold-400); line-height:1; margin-bottom: 8px; }

    h2{
      color: var(--text-gold);
      font-size:28px; font-weight:700;
      text-shadow: 0 1px 0 rgba(0,0,0,.06);
      letter-spacing:.4px;
      margin-bottom: 24px;
    }

    /* ========== 表单 ========== */
    form{ margin-top:6px; }

    .form-group{
      margin-bottom: 18px; text-align:left;
      /* 聚焦时让整组元素有呼吸感 */
      transition: transform var(--dur-fast) ease, filter var(--dur-fast) ease;
    }
    .form-group:focus-within{
      transform: translateY(-1px);
      filter: saturate(110%);
    }

    label{
      display:block; margin-bottom:8px;
      color: var(--text-gold); font-weight: 700; font-size: 14px;
      letter-spacing:.3px;
      transition: color var(--dur) ease, transform var(--dur) ease;
    }
    .form-group:focus-within label{
      color: var(--gold-600);
      transform: translateX(2px);
    }

    .input-container{ position:relative; }

    .input-icon{
      position:absolute; left:14px; top:50%;
      transform: translateY(-50%);
      font-size:18px; color: var(--gold-500);
      transition: transform var(--dur) ease, color var(--dur) ease, opacity var(--dur) ease;
      opacity:.9;
      pointer-events:none;
    }

    input[type="text"], input[type="password"]{
      width:100%;
      padding: 14px 14px 14px 44px;
      border: 1px solid var(--gold-400);
      border-radius: 10px;
      font-size:16px; line-height:1.2;
      background: var(--gold-50);
      color:#2a1f00;
      transition: border-color var(--dur) ease, box-shadow var(--dur) ease, background-color var(--dur) ease, transform var(--dur-fast) ease;
      caret-color: var(--gold-600);
    }

    input[type="text"]::placeholder,
    input[type="password"]::placeholder{
      color:#a58b3a; opacity:.7;
    }

    /* 聚焦态 */
    input[type="text"]:focus, input[type="password"]:focus{
      outline:none;
      border-color: var(--gold-500);
      box-shadow: var(--ring);
      background:#fffef8;
      transform: translateZ(0); /* 抗锯齿 */
    }
    .input-container:focus-within .input-icon{
      transform: translateY(-50%) scale(1.06);
      color: var(--gold-600);
      opacity:1;
    }

    /* 非法态提示（不改变校验逻辑，仅样式） */
    input:required:invalid{
      border-color:#ffc14d;
    }

    /* 适配自动填充 */
    input:-webkit-autofill{
      transition: background-color 5000s ease-in-out 0s;
      -webkit-text-fill-color:#2a1f00;
    }

    /* 提交按钮 */
    input[type="submit"]{
      width:100%;
      padding: 14px 16px;
      border:none; border-radius: 10px;
      background: linear-gradient(90deg, var(--gold-400), var(--gold-500));
      color: var(--text-strong);
      font-size:16px; font-weight:800; letter-spacing:.4px;
      cursor:pointer; margin-top: 10px;
      box-shadow: 0 8px 18px rgba(255,165,0,.22);
      transition: transform var(--dur) ease, box-shadow var(--dur) ease, filter var(--dur) ease, background-position var(--dur) ease;
      background-size: 200% 100%;
      background-position: 0% 50%;
    }
    input[type="submit"]:hover{
      transform: translateY(-2px);
      box-shadow: var(--shadow-3);
      background-position: 100% 50%;
      filter: saturate(110%);
    }
    input[type="submit"]:active{
      transform: translateY(0);
      box-shadow: 0 4px 10px rgba(255,165,0,.22);
    }
    input[type="submit"]:focus-visible{
      outline: none;
      box-shadow: var(--ring), 0 8px 18px rgba(255,165,0,.22);
    }

    /* ========== 底部区域 ========== */
    .register-link{
      text-align:center;
      margin-top: 26px;
      padding-top: 18px;
      border-top: 1px dashed #ffd700;
    }
    .register-link p{
      color: var(--text-muted); margin-bottom: 12px;
    }
    .register-link a{
      display:inline-block;
      padding:10px 18px;
      background: linear-gradient(90deg, #ffed4e, #ffdb4d);
      color: var(--text-strong);
      text-decoration:none;
      border-radius:10px;
      font-weight:800;
      letter-spacing:.3px;
      transition: transform var(--dur) ease, box-shadow var(--dur) ease, background-position var(--dur) ease;
      box-shadow: 0 6px 14px rgba(255,219,77,.22);
      background-size: 160% 100%;
      background-position: 0% 50%;
    }
    .register-link a:hover{
      transform: translateY(-2px);
      box-shadow: 0 10px 18px rgba(255,219,77,.28);
      background-position: 100% 50%;
    }
    .register-link a:active{ transform: translateY(0); }

    .copyright{
      margin-top: 24px; padding-top: 14px;
      border-top: 1px dashed #ffd700;
      color: var(--text-gold); font-size: 13px;
      letter-spacing:.2px;
      opacity:.9;
    }

    /* ========== 响应式优化 ========== */
    @media (max-width: 480px){
      .container{ padding: 30px 20px 24px; }
      h2{ font-size: 24px; margin-bottom: 18px; }
      input[type="text"], input[type="password"]{ padding: 12px 12px 12px 42px; }
      .logo-icon{ font-size: 48px; }
    }

    /* ========== 深色模式适配（不改变功能） ========== */
    @media (prefers-color-scheme: dark){
      body{ background: linear-gradient(135deg,#2a2107 0%, #3a2b0a 100%); }
      .container{
        background: #1d1606; border-color: #5a450f; box-shadow: 0 10px 28px rgba(0,0,0,.35);
      }
      h2, .copyright{ color:#ffde7a; text-shadow:none; }
      label{ color:#ffde7a; }
      .register-link p{ color:#bca76a; }
      input[type="text"], input[type="password"]{
        background: #231a07; color:#ffefbf; border-color:#d19a00;
      }
      .input-icon{ color:#ffc14d; opacity:.95; }
      input[type="submit"]{ color:#3a2b00; }
    }

    /* ========== 降低动效以适配系统设置 ========== */
    @media (prefers-reduced-motion: reduce){
      *{ animation: none !important; transition: none !important; }
    }
  </style>
</head>
<body>

<div class="container">
  <div class="logo" aria-hidden="true">
    <div class="logo-icon">🏦</div>
    <h2>银行系统登录</h2>
  </div>

  <!-- 保持原有逻辑、字段名与提交地址不变 -->
  <form action="login" method="post" autocomplete="on">
    <div class="form-group">
      <label for="username">用户名:</label>
      <div class="input-container">
        <span class="input-icon" aria-hidden="true">👤</span>
        <input type="text" id="username" name="username" required placeholder="请输入您的用户名" autocomplete="username" />
      </div>
    </div>

    <div class="form-group">
      <label for="password">密码:</label>
      <div class="input-container">
        <span class="input-icon" aria-hidden="true">🔒</span>
        <input type="password" id="password" name="password" required placeholder="请输入您的密码" autocomplete="current-password" />
      </div>
    </div>

    <div class="form-group">
      <input type="submit" value="登录" />
    </div>
  </form>

  <div class="register-link">
    <p>还没有账户?</p>
    <a href="regist.jsp" title="前往注册页面">立即注册新账户</a>
  </div>

  <div class="copyright">
    版权所有 © 计科6班 魏语石 23111141
  </div>
</div>

</body>
</html>
