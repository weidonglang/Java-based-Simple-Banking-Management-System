<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="description" content="项目管理系统首页">
  <title>项目管理系统</title>
  <style>
    /* ========== CSS 变量定义 ========== */
    :root {
      /* 基础色彩 */
      --color-primary: #3b82f6;
      --color-primary-hover: #2563eb;
      --color-primary-active: #1d4ed8;
      --color-success: #10b981;
      --color-danger: #ef4444;
      --color-warning: #f59e0b;

      /* 中性色 */
      --color-bg: #f8fafc;
      --color-panel: #ffffff;
      --color-text: #1e293b;
      --color-text-muted: #64748b;
      --color-border: #e2e8f0;
      --color-border-hover: #cbd5e1;
      --color-border-active: #94a3b8;

      /* 按钮色彩 */
      --color-btn-bg: #f1f5f9;
      --color-btn-bg-hover: #e2e8f0;
      --color-btn-bg-active: #cbd5e1;
      --color-btn-text: #334155;

      /* 阴影与透明度 */
      --shadow-sm: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
      --shadow-md: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
      --backdrop-blur: 6px;

      /* 边框圆角 */
      --radius-sm: 0.25rem;
      --radius-md: 0.375rem;
      --radius-lg: 0.5rem;
      --radius-full: 9999px;
    }

    /* ========== 基础样式 ========== */
    * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
    }

    html {
      height: 100%;
      scroll-behavior: smooth;
    }

    body {
      min-height: 100%;
      font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue",
      "PingFang SC", "Microsoft YaHei", "Noto Sans", sans-serif;
      color: var(--color-text);
      background: linear-gradient(135deg, var(--color-bg) 0%, #f1f5f9 100%);
      line-height: 1.5;
      -webkit-font-smoothing: antialiased;
      -moz-osx-font-smoothing: grayscale;
    }

    /* ========== 布局容器 ========== */
    .container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 2rem 1.5rem;
    }

    /* ========== 排版样式 ========== */
    h1 {
      font-size: 1.75rem;
      font-weight: 700;
      line-height: 2rem;
      margin-bottom: 0.75rem;
      color: var(--color-text);
      display: flex;
      align-items: center;
      gap: 0.75rem;
    }

    .subtitle {
      font-size: 0.875rem;
      color: var(--color-text-muted);
      margin-bottom: 1.5rem;
    }

    /* ========== 面板卡片 ========== */
    .panel {
      background: var(--color-panel);
      border: 1px solid var(--color-border);
      border-radius: var(--radius-lg);
      padding: 1.75rem;
      box-shadow: var(--shadow-sm);
      transition: box-shadow 0.2s ease, transform 0.2s ease;
    }

    .panel:hover {
      box-shadow: var(--shadow-md);
      transform: translateY(-2px);
    }

    /* ========== 按钮样式 ========== */
    .btn {
      position: relative;
      display: inline-flex;
      align-items: center;
      justify-content: center;
      padding: 0.5rem 1rem;
      border: 1px solid var(--color-border);
      border-radius: var(--radius-md);
      background-color: var(--color-btn-bg);
      color: var(--color-btn-text);
      font-size: 0.875rem;
      font-weight: 500;
      line-height: 1.5;
      cursor: pointer;
      transition: all 0.2s ease;
      user-select: none;
      min-width: 8rem;
      height: 2.5rem;
      text-decoration: none;
      overflow: hidden;
    }

    .btn:hover {
      background-color: var(--color-btn-bg-hover);
      border-color: var(--color-border-hover);
      transform: translateY(-1px);
    }

    .btn:active {
      background-color: var(--color-btn-bg-active);
      border-color: var(--color-border-active);
      transform: translateY(0);
    }

    .btn:focus {
      outline: none;
      box-shadow: 0 0 0 3px rgba(59, 130, 246, 0.15);
    }

    .btn:disabled {
      opacity: 0.6;
      cursor: not-allowed;
      transform: none;
    }

    .btn-primary {
      background-color: var(--color-primary);
      border-color: var(--color-primary);
      color: white;
    }

    .btn-primary:hover {
      background-color: var(--color-primary-hover);
      border-color: var(--color-primary-hover);
    }

    .btn-primary:active {
      background-color: var(--color-primary-active);
      border-color: var(--color-primary-active);
    }

    /* ========== 按钮组 ========== */
    .btn-group {
      display: flex;
      flex-wrap: wrap;
      gap: 0.75rem;
      margin-bottom: 1.5rem;
    }

    .btn-wrap {
      display: inline-flex;
    }

    .btn-wrap a {
      text-decoration: none;
      width: 100%;
    }

    /* ========== 用户信息栏 ========== */
    .user-info {
      display: flex;
      flex-wrap: wrap;
      align-items: center;
      justify-content: space-between;
      gap: 1rem;
      padding-top: 1rem;
      border-top: 1px solid var(--color-border);
    }

    .user-details {
      display: flex;
      align-items: center;
      gap: 0.75rem;
      font-size: 0.875rem;
    }

    .user-name {
      font-weight: 600;
      color: var(--color-text);
    }

    .badge {
      display: inline-block;
      padding: 0.25rem 0.75rem;
      border-radius: var(--radius-full);
      font-size: 0.75rem;
      font-weight: 500;
      background-color: var(--color-primary);
      color: white;
      line-height: 1;
    }

    /* ========== 提示消息 ========== */
    .alert {
      padding: 1rem;
      margin-bottom: 1.5rem;
      border-radius: var(--radius-md);
      background-color: #fef2f2;
      border: 1px solid #fecaca;
      color: #991b1b;
      display: flex;
      align-items: center;
      gap: 0.75rem;
    }

    .alert::before {
      content: "⚠️";
      font-size: 1.25rem;
    }

    /* ========== 响应式设计 ========== */
    @media (max-width: 768px) {
      .container {
        padding: 1.5rem 1rem;
      }

      h1 {
        font-size: 1.5rem;
        flex-direction: column;
        align-items: flex-start;
        gap: 0.5rem;
      }

      .btn-group {
        flex-direction: column;
      }

      .btn {
        width: 100%;
        min-width: auto;
      }

      .user-info {
        flex-direction: column;
        align-items: flex-start;
        gap: 1rem;
      }
    }

    @media (max-width: 480px) {
      .panel {
        padding: 1.25rem;
      }

      .btn {
        height: 2.25rem;
        font-size: 0.8125rem;
      }
    }

    /* ========== 动画效果 ========== */
    @keyframes fadeIn {
      from { opacity: 0; transform: translateY(10px); }
      to { opacity: 1; transform: translateY(0); }
    }

    .fade-in {
      animation: fadeIn 0.3s ease-out forwards;
    }

    /* ========== 可访问性 ========== */
    @media (prefers-reduced-motion: reduce) {
      * {
        animation-duration: 0.01ms !important;
        animation-iteration-count: 1 !important;
        transition-duration: 0.01ms !important;
        scroll-behavior: auto !important;
      }
    }

    @media (prefers-color-scheme: dark) {
      :root {
        --color-bg: #0f172a;
        --color-panel: #1e293b;
        --color-text: #f8fafc;
        --color-text-muted: #94a3b8;
        --color-border: #334155;
        --color-border-hover: #475569;
        --color-border-active: #64748b;
        --color-btn-bg: #334155;
        --color-btn-bg-hover: #475569;
        --color-btn-bg-active: #64748b;
        --color-btn-text: #f8fafc;
      }

      .alert {
        background-color: #1f2937;
        border-color: #374151;
        color: #fecaca;
      }
    }
  </style>
</head>
<body>
<div class="container">
  <h1>
    项目管理系统
    <span class="subtitle">欢迎使用我们的服务平台</span>
  </h1>

  <%
    // 会话状态检查
    boolean loggedIn = (session != null) &&
            (session.getAttribute("loginUser") != null || session.getAttribute("username") != null);
    Boolean adminFlag = (session == null) ? null : (Boolean) session.getAttribute("isAdmin");
    boolean isAdmin = adminFlag != null && adminFlag;
    String ctx = request.getContextPath();
  %>

  <%-- 显示系统消息 --%>
  <%
    String msg = (String) request.getAttribute("msg");
    if (msg != null && !msg.isEmpty()) {
  %>
  <div class="alert fade-in"><%= msg %></div>
  <% } %>

  <div class="panel fade-in">
    <div class="btn-group">
      <%-- 普通用户功能 --%>
      <div class="btn-wrap">
        <a href="<%= ctx %>/showAccount">
          <button class="btn" type="button">我的账户</button>
        </a>
      </div>

      <div class="btn-wrap">
        <a href="<%= ctx %>/transaction.jsp">
          <button class="btn" type="button">存取款操作</button>
        </a>
      </div>

      <div class="btn-wrap">
        <a href="<%= ctx %>/easterEgg.jsp">
          <button class="btn" type="button">彩蛋</button>
        </a>
      </div>

      <%-- 管理员专属功能 --%>
      <% if (isAdmin) { %>
      <div class="btn-wrap">
        <a href="<%= ctx %>/showUsers">
          <button class="btn btn-primary" type="button">用户管理</button>
        </a>
      </div>
      <% } %>
    </div>

    <%-- 用户状态栏 --%>
    <div class="user-info">
      <% if (loggedIn) { %>
      <div class="user-details">
        <span class="muted">当前用户:</span>
        <span class="user-name"><%= (String)session.getAttribute("username") %></span>
        <% if (isAdmin) { %>
        <span class="badge">管理员</span>
        <% } %>
      </div>
      <div class="btn-wrap">
        <a href="<%= ctx %>/logout">
          <button class="btn" type="button">退出登录</button>
        </a>
      </div>
      <% } else { %>
      <div class="btn-wrap">
        <a href="<%= ctx %>/login.jsp">
          <button class="btn" type="button">登录</button>
        </a>
      </div>
      <div class="btn-wrap">
        <a href="<%= ctx %>/regist.jsp">
          <button class="btn btn-primary" type="button">注册</button>
        </a>
      </div>
      <% } %>
    </div>
  </div>
</div>
</body>
</html>