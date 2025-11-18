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
        input[type=text],input[type=password]{width:100%;padding:8px 10px;border:1px solid #cfd3d7;border-radius:6px}
        .actions{display:flex;gap:8px;margin-top:12px}
        .btn{display:inline-block;padding:8px 12px;border:1px solid #cfd3d7;border-radius:6px;text-decoration:none;background:#fff;color:#111827}
        .btn:hover{background:#f3f4f6}
        .btn-primary{border-color:#60a5fa;background:#3b82f6;color:#fff}
        .btn-primary:hover{background:#2563eb}
        .help{font-size:12px;color:#6b7280;margin-top:4px}
        .error{background:#fee2e2;border:1px solid #fecaca;color:#991b1b;padding:8px;border-radius:6px;margin:8px 0}
        details{border:1px dashed #cfd3d7;padding:10px;border-radius:6px}
        details>summary{cursor:pointer;font-weight:600;margin-bottom:8px}
    </style>
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h2>修改用户信息</h2>

<div class="card">
    <c:if test="${not empty requestScope.error}">
        <div class="error">${requestScope.error}</div>
    </c:if>

    <c:if test="${empty requestScope.user}">
        <p>未找到用户。</p>
        <p><a class="btn" href="${ctx}/showUsers">返回列表</a></p>
    </c:if>

    <c:if test="${not empty requestScope.user}">
        <form id="form" action="${ctx}/updateUserById" method="post" accept-charset="UTF-8" autocomplete="off">
            <c:if test="${not empty sessionScope.csrfToken}">
                <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
            </c:if>
            <input type="hidden" name="id" value="${user.id}"/>

            <div class="row">
                <label for="username">用户名</label>
                <input id="username" type="text" name="username" value="${user.username}" required maxlength="64">
            </div>

            <!-- 可选：修改密码 -->
            <details class="row">
                <summary>修改密码（可选）</summary>

                <div class="row">
                    <label for="currentPassword">当前密码</label>
                    <input id="currentPassword" type="password" name="currentPassword" maxlength="64" autocomplete="current-password">
                    <div class="help">仅当你要修改密码时填写；用于校验。</div>
                </div>

                <div class="row">
                    <label for="newPassword">新密码</label>
                    <input id="newPassword" type="password" name="newPassword" maxlength="64" autocomplete="new-password">
                </div>

                <div class="row">
                    <label for="confirmPassword">确认新密码</label>
                    <input id="confirmPassword" type="password" name="confirmPassword" maxlength="64" autocomplete="new-password">
                    <div class="help">留空则不修改密码；如填写则需与上方一致。</div>
                </div>
            </details>

            <div class="actions">
                <button class="btn btn-primary" type="submit">保存</button>
                <a class="btn" href="${ctx}/showUsers">取消</a>
            </div>
        </form>

        <!-- 简单的前端一致性检查（后端仍会再次校验） -->
        <script>
            document.getElementById('form').addEventListener('submit', function (e) {
                var np = document.getElementById('newPassword').value.trim();
                var cp = document.getElementById('confirmPassword').value.trim();
                if (np.length > 0 && np !== cp) {
                    e.preventDefault();
                    alert('两次输入的新密码不一致');
                }
            });
        </script>
    </c:if>
</div>
</body>
</html>
