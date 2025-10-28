<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <title>lab3_2：九九乘法表</title>
  <style>
    body { font-family: system-ui, Arial; margin: 40px; }
    table { border-collapse: collapse; }
    td {
      border: 1px solid #ccc; padding: 6px 10px; text-align: center;
      min-width: 70px;
    }
    .title { margin-bottom: 12px; }
  </style>
</head>
<body>
<h2 class="title">lab3_2：九九乘法表</h2>
<table>
  <tbody>
  <% for (int i = 1; i <= 9; i++) { %>
  <tr>
    <% for (int j = 1; j <= i; j++) { %>
    <td><%= j %> × <%= i %> = <%= (i*j) %></td>
    <% } %>
  </tr>
  <% } %>
  </tbody>
</table>
</body>
</html>
