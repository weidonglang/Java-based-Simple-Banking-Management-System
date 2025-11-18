<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
    .toolbar{margin-bottom:12px;display:flex;align-items:center;gap:16px;flex-wrap:wrap;}
    .toolbar form input[type="text"]{
      padding:6px 10px;border-radius:6px;border:1px solid #cbd5e1;min-width:200px;
    }
    .toolbar form button{
      padding:6px 12px;border-radius:6px;border:1px solid #4f46e5;background:#4f46e5;
      color:#fff;cursor:pointer;transition:background .15s ease,border-color .15s ease;
    }
    .toolbar form button:hover{
      background:#4338ca;border-color:#4338ca;
    }
    /* 分页样式 */
    .pager{
      margin-top:12px;
      display:flex;
      flex-wrap:wrap;
      align-items:center;
      gap:12px;
      font-size:14px;
      color:#4b5563;
    }
    .pager-links a,
    .pager-links span{
      display:inline-block;
      margin:0 3px;
      padding:3px 8px;
      border-radius:4px;
      text-decoration:none;
      border:1px solid #cbd5e1;
    }
    .pager-links a{
      color:#1f2937;
      background:#fff;
    }
    .pager-links a:hover{
      background:#f3f4f6;
    }
    .pager-links .current{
      background:#4f46e5;
      border-color:#4f46e5;
      color:#fff;
      font-weight:600;
    }
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

  <!-- 搜索表单：提交到 /searchUser -->
  <form action="${ctx}/searchUser" method="get">
    <input type="text"
           name="username"
           placeholder="请输入用户名关键字"
           value="${fn:escapeXml(searchKeyword != null ? searchKeyword : param.username)}" />
    <button type="submit">搜索</button>
    <c:if test="${not empty searchKeyword}">
      <span style="margin-left:8px;color:#4b5563;">
        当前关键字：
        <strong>${fn:escapeXml(searchKeyword)}</strong>
      </span>
    </c:if>
  </form>
</div>

<c:choose>
  <c:when test="${not empty page and not empty page.data}">
    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>用户名</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="u" items="${page.data}">
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

    <!-- 分页信息和页码链接 -->
    <div class="pager">
      <span>
        共 ${page.total} 条记录，
        每页 ${page.size} 条，
        当前第 ${page.currentPage} / ${page.totalPage} 页
      </span>

      <div class="pager-links">
        <!-- 上一页 -->
        <c:if test="${page.currentPage > 1}">
          <a href="${ctx}/showUsersByPage?currentPage=${page.currentPage - 1}">上一页</a>
        </c:if>

        <!-- 具体页码 -->
        <c:forEach var="p" begin="1" end="${page.totalPage}">
          <c:choose>
            <c:when test="${p == page.currentPage}">
              <span class="current">${p}</span>
            </c:when>
            <c:otherwise>
              <a href="${ctx}/showUsersByPage?currentPage=${p}">${p}</a>
            </c:otherwise>
          </c:choose>
        </c:forEach>

        <!-- 下一页 -->
        <c:if test="${page.currentPage < page.totalPage}">
          <a href="${ctx}/showUsersByPage?currentPage=${page.currentPage + 1}">下一页</a>
        </c:if>
      </div>
    </div>

  </c:when>
  <c:otherwise>
    <p class="empty">暂无用户数据。</p>
  </c:otherwise>
</c:choose>
</body>
</html>
