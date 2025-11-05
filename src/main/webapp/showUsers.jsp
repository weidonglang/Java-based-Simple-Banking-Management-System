<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>所有用户</title>
  <style>
    body{font-family:system-ui,Segoe UI,Arial; margin:24px;}
    table{border-collapse:collapse; width:100%;}
    th,td{border:1px solid #ddd; padding:10px; text-align:left;}
    th{background:#f6f6f6;}
    .empty{color:#888}
  </style>
</head>
<body>
<h2>所有用户</h2>

<c:choose>
  <c:when test="${not empty users}">
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>用户名</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="u" items="${users}">
        <tr>
          <!-- 支持 JavaBean 或 Map 取值：${u.id} / ${u['id']} 都可 -->
          <td>${u.id}</td>
          <td>${u.username}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </c:when>
  <c:otherwise>
    <p class="empty">暂无用户数据。</p>
  </c:otherwise>
</c:choose>

<p style="margin-top:16px;">
  <a href="${pageContext.request.contextPath}/index.jsp">返回首页</a>
</p>
</body>
</html>
