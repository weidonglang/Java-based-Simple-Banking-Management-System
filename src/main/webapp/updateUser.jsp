<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>修改用户信息</title>
    <style>
        body{font-family:system-ui,Segoe UI,Arial;margin:24px;}
        .card{max-width:520px;border:1px solid #ddd;border-radius:8px;padding:16px}
        .row{margin-bottom:12px}
        label{display:block;margin-bottom:6px;color:#374151}
        input[type=text]{width:100%;padding:8px 10px;border:1px solid #cfd3d7;border-radius:6px}
        .actions{display:flex;gap:8px;margin-top:12px}
        .btn{display:inline-block;padding:8px 12px;border:1px solid #cfd3d7;border-radius:6px;text-decoration:none;background:#fff;color:#111827}
        .btn:hover{background:#f3f4f6}
        .btn-primary{border-color:#60a5fa;background:#3b82f6;color:#fff}
        .btn-primary:hover{background:#2563eb}
    </style>
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h2>修改用户信息</h2>

<div class="card">
    <c:if test="${empty requestScope.user}">
        <p>未找到用户。</p>
        <p><a class="btn" href="${ctx}/showUsers">返回列表</a></p>
    </c:if>

    <c:if test="${not empty requestScope.user}">
        <form action="${ctx}/updateUserById" method="post" accept-charset="UTF-8">
            <c:if test="${not empty sessionScope.csrfToken}">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
            </c:if>
            <input type="hidden" name="id" value="${user.id}"/>

            <div class="row">
                <label for="username">用户名</label>
                <input id="username" type="text" name="username" value="${user.username}" required maxlength="64">
            </div>

            <div class="actions">
                <button class="btn btn-primary" type="submit">保存</button>
                <a class="btn" href="${ctx}/showUsers">取消</a>
            </div>
        </form>
    </c:if>
</div>
</body>
</html>
