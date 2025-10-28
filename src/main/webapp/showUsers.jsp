<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="java.util.List, java.util.Map, java.text.SimpleDateFormat, java.util.Date" %>
<%
  // —— 探针：在响应头写入 “哪一份 JSP 在渲染 + 时间戳” ——
  response.addHeader("X-JSP", "showUsers.jsp@" + new SimpleDateFormat("HH:mm:ss.SSS").format(new Date()));

  // 取出 request 里的数据（来自 ShowUsersServlet）
  Object obj = request.getAttribute("users");
  List<Map<String,Object>> users = null;
  if (obj instanceof List) {
    users = (List<Map<String,Object>>) obj;
  }
  int count = (users == null) ? 0 : users.size();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>所有用户信息</title>
  <style>
    body{font-family:-apple-system,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,"Noto Sans","PingFang SC","Microsoft YaHei",sans-serif;}
    .toolbar{max-width:900px;margin:16px auto;display:flex;justify-content:space-between;align-items:center}
    .btn{padding:6px 12px;border:1px solid #ddd;border-radius:6px;background:#f7f7f7;text-decoration:none;color:#333}
    .btn:hover{background:#eee}
    table{border-collapse:collapse;width:100%;max-width:900px;margin:0 auto}
    th,td{border:1px solid #ddd;padding:8px 10px;text-align:left}
    th{background:#f7f7f7}
    .muted{color:#666;max-width:900px;margin:12px auto}
  </style>
</head>
<body>

<div class="toolbar">
  <h2>系统内所有用户信息</h2>
  <a class="btn" href="<%= request.getContextPath() %>/index.jsp">返回首页</a>
</div>

<p class="muted" style="max-width:900px;margin:8px auto;">
  当前渲染的 users.size = <strong><%= count %></strong>
</p>

<%
  if (count == 0) {
%>
<p class="muted" style="max-width:900px;margin:0 auto;">暂无用户数据。</p>
<%
} else {
%>
<table>
  <thead>
  <tr>
    <th>用户ID</th>
    <th>用户名</th>
  </tr>
  </thead>
  <tbody>
  <%
    for (Map<String, Object> row : users) {
      Object id = row.get("id");
      Object username = row.get("username");
  %>
  <tr>
    <td><%= id %></td>
    <td><%= username %></td>
  </tr>
  <%
    }
  %>
  </tbody>
</table>
<%
  }
%>

</body>
</html>
