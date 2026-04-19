<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <style>
        body{font-family:system-ui,Segoe UI,Arial;margin:24px;color:#111827}
        table{border-collapse:collapse;width:100%}
        th,td{border:1px solid #ddd;padding:10px;text-align:left}
        th{background:#f6f6f6}
        .empty{color:#6b7280}
        .msg{margin:12px 0;padding:10px;border-radius:6px;background:#eff6ff;border:1px solid #bfdbfe;color:#1e40af}
        .toolbar{margin-bottom:12px;display:flex;align-items:center;gap:16px;flex-wrap:wrap}
        .toolbar input[type="text"]{padding:6px 10px;border-radius:6px;border:1px solid #cbd5e1;min-width:200px}
        .btn{display:inline-block;padding:6px 10px;border:1px solid #cfd3d7;border-radius:6px;text-decoration:none;color:#1f2937;background:#fff;cursor:pointer;font:inherit}
        .btn:hover{background:#f3f4f6}
        .btn-primary{border-color:#4f46e5;background:#4f46e5;color:#fff}
        .btn-primary:hover{background:#4338ca}
        .btn-danger{border-color:#fca5a5;color:#b91c1c;background:#fff0f0}
        .btn-danger:hover{background:#fee2e2}
        .actions{display:flex;gap:6px;align-items:center;flex-wrap:wrap}
        .actions form{margin:0}
        .pager{margin-top:12px;display:flex;flex-wrap:wrap;align-items:center;gap:12px;font-size:14px;color:#4b5563}
        .pager-links a,.pager-links span{display:inline-block;margin:0 3px;padding:3px 8px;border-radius:4px;text-decoration:none;border:1px solid #cbd5e1}
        .pager-links a{color:#1f2937;background:#fff}
        .pager-links a:hover{background:#f3f4f6}
        .pager-links .current{background:#4f46e5;border-color:#4f46e5;color:#fff;font-weight:600}
    </style>
</head>
<body>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h2>Users</h2>

<c:if test="${not empty requestScope.msg or not empty param.msg}">
    <div class="msg">
        <c:out value="${not empty requestScope.msg ? requestScope.msg : param.msg}"/>
    </div>
</c:if>

<div class="toolbar">
    <a class="btn" href="${ctx}/index.jsp">Home</a>
    <form action="${ctx}/searchUser" method="get">
        <input type="text"
               name="username"
               placeholder="Search username"
               value="${fn:escapeXml(searchKeyword != null ? searchKeyword : param.username)}">
        <button class="btn btn-primary" type="submit">Search</button>
        <c:if test="${not empty searchKeyword}">
            <span>Keyword: <strong><c:out value="${searchKeyword}"/></strong></span>
        </c:if>
    </form>
</div>

<c:choose>
    <c:when test="${not empty page and not empty page.data}">
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Username</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="u" items="${page.data}">
                <tr>
                    <td><c:out value="${u.id}"/></td>
                    <td><c:out value="${u.username}"/></td>
                    <td class="actions">
                        <c:url var="editUrl" value="/toUpdateUser">
                            <c:param name="id" value="${u.id}"/>
                        </c:url>
                        <a class="btn" href="${editUrl}">Edit</a>

                        <form action="${ctx}/deleteUserById" method="post"
                              onsubmit="return confirm('Delete user ID ${u.id}? This cannot be undone.');">
                            <input type="hidden" name="id" value="${u.id}">
                            <input type="hidden" name="csrfToken" value="${sessionScope.csrfToken}">
                            <button class="btn btn-danger" type="submit">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="pager">
            <span>Total ${page.total}, page ${page.currentPage} / ${page.totalPage}</span>
            <div class="pager-links">
                <c:if test="${page.currentPage > 1}">
                    <a href="${ctx}/showUsersByPage?currentPage=${page.currentPage - 1}">Prev</a>
                </c:if>
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
                <c:if test="${page.currentPage < page.totalPage}">
                    <a href="${ctx}/showUsersByPage?currentPage=${page.currentPage + 1}">Next</a>
                </c:if>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <p class="empty">No users found.</p>
    </c:otherwise>
</c:choose>
</body>
</html>
