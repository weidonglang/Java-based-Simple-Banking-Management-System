<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="description" content="项目管理系统首页">
  <title>项目管理系统</title>
  <style>
    :root {
      --color-primary:#3b82f6; --color-primary-hover:#2563eb; --color-primary-active:#1d4ed8;
      --color-success:#10b981; --color-danger:#ef4444; --color-warning:#f59e0b;
      --color-bg:#f8fafc; --color-panel:#ffffff; --color-text:#1e293b; --color-text-muted:#64748b;
      --color-border:#e2e8f0; --color-border-hover:#cbd5e1; --color-border-active:#94a3b8;
      --color-btn-bg:#f1f5f9; --color-btn-bg-hover:#e2e8f0; --color-btn-bg-active:#cbd5e1; --color-btn-text:#334155;
      --shadow-sm:0 1px 2px rgba(0,0,0,.05); --shadow-md:0 4px 6px -1px rgba(0,0,0,.1),0 2px 4px -1px rgba(0,0,0,.06);
      --backdrop-blur:6px; --radius-sm:.25rem; --radius-md:.375rem; --radius-lg:.5rem; --radius-full:9999px;
    }
    *{box-sizing:border-box;margin:0;padding:0}
    html{height:100%;scroll-behavior:smooth}
    .btn{display:inline-flex;align-items:center;gap:6px;padding:10px 16px;border-radius:999px;text-decoration:none;font-weight:700;border:1px solid transparent;transition:transform .15s ease,box-shadow .15s ease,background-color .2s ease;color:#fff;background:var(--color-primary)}
    .btn:hover{transform:translateY(-1px);box-shadow:0 10px 24px rgba(45,108,223,.25);background:var(--color-primary-hover)}
    body{min-height:100%;font-family:-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue","PingFang SC","Microsoft YaHei","Noto Sans",sans-serif;color:var(--color-text);background:linear-gradient(135deg,var(--color-bg) 0%,#f1f5f9 100%);line-height:1.5;-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale}
    .container{max-width:1200px;margin:0 auto;padding:2rem 1.5rem}
    h1{font-size:1.75rem;font-weight:700;line-height:2rem;margin-bottom:.75rem;color:var(--color-text);display:flex;align-items:center;gap:.75rem}
    .subtitle{font-size:.875rem;color:var(--color-text-muted);margin-bottom:1.5rem}
    .panel{background:var(--color-panel);border:1px solid var(--color-border);border-radius:var(--radius-lg);padding:1.75rem;box-shadow:var(--shadow-sm);transition:box-shadow .2s ease,transform .2s ease}
    .panel:hover{box-shadow:var(--shadow-md);transform:translateY(-2px)}
    /* 新版按钮覆盖，保留原外观 */
    .btn{position:relative;display:inline-flex;align-items:center;justify-content:center;padding:.5rem 1rem;border:1px solid var(--color-border);border-radius:var(--radius-md);background-color:var(--color-btn-bg);color:var(--color-btn-text);font-size:.875rem;font-weight:500;line-height:1.5;cursor:pointer;transition:all .2s ease;user-select:none;min-width:8rem;height:2.5rem;text-decoration:none;overflow:hidden}
    .btn:hover{background-color:var(--color-btn-bg-hover);border-color:var(--color-border-hover);transform:translateY(-1px)}
    .btn:active{background-color:var(--color-btn-bg-active);border-color:var(--color-border-active);transform:translateY(0)}
    .btn:focus{outline:none;box-shadow:0 0 0 3px rgba(59,130,246,.15)}
    .btn:disabled{opacity:.6;cursor:not-allowed;transform:none}
    .btn-primary{background-color:var(--color-primary);border-color:var(--color-primary);color:#fff}
    .btn-primary:hover{background-color:var(--color-primary-hover);border-color:var(--color-primary-hover)}
    .btn-primary:active{background-color:var(--color-primary-active);border-color:var(--color-primary-active)}
    .btn-group{display:flex;flex-wrap:wrap;gap:.75rem;margin-bottom:1.5rem}
    .btn-wrap{display:inline-flex}
    .btn-wrap a{text-decoration:none;width:100%}
    .user-info{display:flex;flex-wrap:wrap;align-items:center;justify-content:space-between;gap:1rem;padding-top:1rem;border-top:1px solid var(--color-border)}
    .user-details{display:flex;align-items:center;gap:.75rem;font-size:.875rem}
    .user-name{font-weight:600;color:var(--color-text)}
    .badge{display:inline-block;padding:.25rem .75rem;border-radius:var(--radius-full);font-size:.75rem;font-weight:500;background-color:var(--color-primary);color:#fff;line-height:1}
    .alert{padding:1rem;margin-bottom:1.5rem;border-radius:var(--radius-md);background-color:#fef2f2;border:1px solid #fecaca;color:#991b1b;display:flex;align-items:center;gap:.75rem}
    .alert::before{content:"⚠️";font-size:1.25rem}
    @media (max-width:768px){.container{padding:1.5rem 1rem}h1{font-size:1.5rem;flex-direction:column;align-items:flex-start;gap:.5rem}.btn-group{flex-direction:column}.btn{width:100%;min-width:auto}.user-info{flex-direction:column;align-items:flex-start;gap:1rem}}
    @media (max-width:480px){.panel{padding:1.25rem}.btn{height:2.25rem;font-size:.8125rem}}
    @keyframes fadeIn{from{opacity:0;transform:translateY(10px)}to{opacity:1;transform:translateY(0)}}
    .fade-in{animation:fadeIn .3s ease-out forwards}
    @media (prefers-reduced-motion:reduce){*{animation-duration:.01ms!important;animation-iteration-count:1!important;transition-duration:.01ms!important;scroll-behavior:auto!important}}
    @media (prefers-color-scheme:dark){
      :root{--color-bg:#0f172a;--color-panel:#1e293b;--color-text:#f8fafc;--color-text-muted:#94a3b8;--color-border:#334155;--color-border-hover:#475569;--color-border-active:#64748b;--color-btn-bg:#334155;--color-btn-bg-hover:#475569;--color-btn-bg-active:#64748b;--color-btn-text:#f8fafc}
      .alert{background-color:#1f2937;border-color:#374151;color:#fecaca}
    }
  </style>
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="loggedIn" value="${not empty sessionScope.loginUser or not empty sessionScope.username}" />
<c:set var="isAdmin" value="${sessionScope.isAdmin == true}" />
<c:set var="displayName"
       value="${not empty sessionScope.username
                 ? sessionScope.username
                 : (not empty sessionScope.loginUser ? sessionScope.loginUser.username : '')}" />

<div class="container">
  <h1>
    项目管理系统
    <span class="subtitle">欢迎使用我们的服务平台</span>
  </h1>

  <!-- 系统消息（来自 requestScope.msg） -->
  <c:if test="${not empty requestScope.msg}">
    <div class="alert fade-in"><c:out value="${requestScope.msg}"/></div>
  </c:if>

  <div class="panel fade-in">

    <!-- 顶部按钮区：按登录与角色显示 -->
    <div class="btn-group">
      <c:choose>
        <%-- 未登录：显示 登录 / 注册 --%>
        <c:when test="${not loggedIn}">
          <div class="btn-wrap">
            <a class="btn" href="${ctx}/login.jsp">登录</a>
          </div>
          <div class="btn-wrap">
            <a class="btn btn-primary" href="${ctx}/regist.jsp">注册</a>
          </div>
        </c:when>

        <%-- 已登录且为管理员：显示 查询所有用户 / 退出登录 --%>
        <c:when test="${isAdmin}">
          <div class="btn-wrap">
            <a class="btn btn-primary" href="${ctx}/showUsers">查询所有用户</a>
          </div>
          <div class="btn-wrap">
            <a class="btn" href="${ctx}/logout">退出登录</a>
          </div>
        </c:when>

        <%-- 已登录但非管理员：显示 我的账户 / 退出登录 --%>
        <c:otherwise>
          <div class="btn-wrap">
            <a class="btn" href="${ctx}/showAccount">我的账户</a>
          </div>
          <div class="btn-wrap">
            <a class="btn" href="${ctx}/logout">退出登录</a>
          </div>
        </c:otherwise>
      </c:choose>
    </div>

    <!-- 登录后显示：产品列表 / 存取款 / 彩蛋 -->
    <c:if test="${loggedIn}">
      <div class="btn-group">
        <div class="btn-wrap">
          <a class="btn" href="${ctx}/showProducts">🛒 查看产品列表</a>
        </div>
        <div class="btn-wrap">
          <a class="btn" href="${ctx}/transaction.jsp">存取款操作</a>
        </div>
        <div class="btn-wrap">
          <a class="btn" href="${ctx}/easterEgg.jsp">彩蛋</a>
        </div>
      </div>
    </c:if>

    <!-- 用户状态栏 -->
    <div class="user-info">
      <c:choose>
        <%-- 已登录欢迎语 --%>
        <c:when test="${loggedIn}">
          <div class="user-details">
            <span class="muted">欢迎</span>
            <span class="user-name"><c:out value="${displayName}"/></span>
            <span class="muted">用户！</span>
            <c:if test="${isAdmin}">
              <span class="badge">管理员</span>
            </c:if>
          </div>
        </c:when>
        <%-- 未登录提示 --%>
        <c:otherwise>
          <div class="user-details">
            <span class="muted">您尚未登录，请先登录或注册以使用完整功能。</span>
          </div>
        </c:otherwise>
      </c:choose>
    </div>

  </div>
</div>
</body>
</html>
