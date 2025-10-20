<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>账户操作结果</title>
  <style>
    body { font-family: 'Segoe UI', sans-serif; background-color: #fdfaf0; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
    .container { text-align: center; background: #fff; padding: 40px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.1); border-top: 5px solid #ffd700; }
    h2 { color: #b8860b; margin-bottom: 15px; }
    p { font-size: 1.1em; color: #555; margin-bottom: 30px; }
    a { display: inline-block; padding: 12px 25px; background: #ffd700; color: #6b4d00; text-decoration: none; border-radius: 5px; font-weight: bold; transition: background-color 0.3s; }
    a:hover { background-color: #ffc107; }
  </style>
</head>
<body>

<div class="container">
  <h2>${requestScope.messageTitle}</h2>
  <p>${requestScope.messageBody}</p>
  <a href="index.jsp">返回首页</a>
</div>

</body>
</html>