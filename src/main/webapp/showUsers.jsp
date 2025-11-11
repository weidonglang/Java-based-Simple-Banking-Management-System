<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>所有用户</title>
  <style>
    body{font-family:system-ui,Segoe UI,Arial;margin:24px;}
    table{border-collapse:collapse;width:100%;}
    th,td{border:1px solid #ddd;padding:10px;text-align:left;}
    th{background:#f6f6f6;}
    .empty{color:#888}
    .actions a{
      display:inline-block;padding:6px 10px;margin-right:6px;border:1px solid #cfd3d7;border-radius:6px;
      text-decoration:none;color:#1f2937;background:#fff;transition:all .15s ease;
    }
    .actions a:hover{background:#f3f4f6}
    .btn-danger{border-color:#fca5a5;color:#b91c1c;background:#fff0f0}
    .btn-danger:hover{background:#fee2e2}
    .msg{margin:12px 0;padding:10px;border-radius:6px;background:#eff6ff;border:1px solid #bfdbfe;color:#1e40af;}
    .toolbar{margin-bottom:12px}
  </style>
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h2>所有用户</h2>

<!-- 可选提示：支持从 request 属性或 ?msg= 提示 -->
<c:if test="${not empty requestScope.msg or not empty param.msg}">
  <div class="msg">
    <c:out value="${not empty requestScope.msg ? requestScope.msg : param.msg}"/>
  </div>
</c:if>

<div class="toolbar">
  <a href="${ctx}/index.jsp">← 返回首页</a>
</div>

<c:choose>
  <c:when test="${not empty users}">
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>用户名</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="u" items="${users}">
        <tr>
          <td><c:out value="${u.id}"/></td>
          <td><c:out value="${u.username}"/></td>
          <td class="actions">
            <!-- 删除：/deleteUserById?id=... -->
            <c:url var="delUrl" value="/deleteUserById">
              <c:param name="id" value="${u.id}"/>
            </c:url>
            <a class="btn-danger"
               href="${delUrl}"
               onclick="return confirm('确认删除ID为 ${u.id} 的用户吗？此操作不可撤销。');">删除</a>

            <!-- 修改：/toUpdateUser?id=... -->
            <c:url var="editUrl" value="/toUpdateUser">
              <c:param name="id" value="${u.id}"/>
            </c:url>
            <a href="${editUrl}">修改</a>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:when>
  <c:otherwise>
    <p class="empty">暂无用户数据。</p>
  </c:otherwise>
</c:choose>
</body>
</html>
