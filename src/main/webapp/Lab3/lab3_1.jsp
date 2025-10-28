<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>lab3_1：1+2+…+100</title>
    <style>
        body { font-family: system-ui, Arial; margin: 40px; }
        code { background: #f6f8fa; padding: 2px 6px; border-radius: 4px; }
    </style>
</head>
<body>
<h2>lab3_1：求 1+2+…+100 的和</h2>
<%-- 计算求和 --%>
<%
    int sum = 0;
    for (int i = 1; i <= 100; i++) {
        sum += i;
    }
%>
<p>使用 JSP 脚本片段计算的结果：<strong><%= sum %></strong></p>
<jsp:useBean id="now" class="java.util.Date" />
<p>当前时间（演示 JSP 动作 useBean）：<code><%= now.toString() %></code></p>

</body>
</html>
