
# Lab2 – Banking Demo (JSP/Servlet)

*(中文 / English Bilingual README)*

A minimal banking-style web app built with **JSP + Servlet + JDBC** on **Tomcat 8**. It supports user registration & login, account creation, deposit/withdraw, viewing own account, an **admin-only “list all users”** (via Filter), plus a **JSTL/EL demo: product list** page.

一个基于 **JSP + Servlet + JDBC**（Tomcat 8）的极简银行示例应用：注册/登录、开通账户、存取款、查询我的账户、**管理员专享“查询所有用户信息”**（Filter 拦截），并新增 **JSTL/EL 产品列表** 示例页面。

---

## ✅ What’s New | 本次更新

* `showUsers.jsp` 改造为 **JSTL + EL** 遍历显示用户集合（替代脚本片段）。
* 新增 **产品列表**功能（纯模拟数据，演示 JSTL/EL）：

  * `com.tianshi.entity.Product` 实体
  * `com.tianshi.servlet.ShowProductsServlet`（`/showProducts`）
  * `showProducts.jsp`（卡片式展示，含折扣价、库存、评分星星）
* UI 轻量美化：统一按钮样式、卡片悬浮、返回上一级按钮。
* 静态资源路径规范：图片置于 `src/main/webapp/imgs/`，JSP 使用 `<c:url>` 自动拼接 Context Path。

---

## ✨ Features | 功能特性

* Register & Login（注册/登录）
* Create one account per user（每人一个账户）
* Deposit / Withdraw with transaction handling（存取款，含事务）
* Show **my** account balance（查询我的账户）
* **Admin-only**: show **all** users（仅管理员查看所有用户，`AdminAuthFilter` 保护）
* **JSTL/EL** demos:

  * `showUsers.jsp`：`<c:forEach>` + `${u.id}` / `${u.username}`
  * `showProducts.jsp`：列表卡片、折扣计算、库存分档、评分星星、图片 `<c:url>`

Session keys：`loginUser`, `userId`, `username`, `isAdmin`

---

## 🧱 Tech Stack | 技术栈

* **Java 8**, **Servlet/JSP**, **JSTL 1.2+**
* **Tomcat 8.0.x**
* **JDBC**（`JDBCUtil`）
* JSP + 少量 CSS（按钮与卡片 UI）

> 引用 JSTL Core：
> `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>`

---

## 📁 Project Layout | 目录结构（示例）

```
src/main/java/
  com/tianshi/entity/         # User, Account, Product   ← NEW
  com/tianshi/dao/            # UserDao, AccountDao (+impl)
  com/tianshi/service/        # UserService, AccountService (+impl)
  com/tianshi/servlet/        # LoginServlet, CreateAccountServlet, ShowProductsServlet ← NEW
  com/tianshi/filter/         # AdminAuthFilter
  com/tianshi/util/           # JDBCUtil

src/main/webapp/
  index.jsp
  login.jsp
  regist.jsp
  transaction.jsp
  showAccount.jsp
  accountResult.jsp
  showUsers.jsp               ← JSTL/EL 改造版
  showProducts.jsp            ← NEW（含“返回上一级”按钮）
  imgs/                       ← xiaomi.png / gree.png / huawei.png
  WEB-INF/web.xml
```

> **静态资源**：放在 `src/main/webapp/imgs/`；JSP 中使用：
>
> ```jsp
> <c:url value="/imgs/gree.png" var="imgUrl"/>
> <img src="${imgUrl}" alt="...">
> ```
>
> 这样会自动带上 Context Path，避免 404。

---

## 🔐 Admin Guard via Filter | 管理员过滤器

* 目标：仅管理员可访问 `GET /showUsers`。
* 方式：`AdminAuthFilter` 映射到 `/showUsers`。登录成功后设置 `session.isAdmin`（当前用用户名 `admin` 触发；可扩展到 DB 角色）。
* 可用注解 `@WebFilter(urlPatterns={"/showUsers"})` 或 `web.xml` 中 `<filter>` + `<filter-mapping>`。

---

## 🧪 Pages & Endpoints | 页面与端点

| Page / API             | Method   | Access     | Purpose                        |
| ---------------------- | -------- | ---------- | ------------------------------ |
| `/login.jsp`, `/login` | GET/POST | Public     | Login（登录）                      |
| `/regist.jsp`          | GET      | Public     | Register（注册）                   |
| `/index.jsp`           | GET      | All/Logged | Home（首页，含“查看产品列表”美化按钮）         |
| `/createAccount`       | POST     | Logged     | Create account（开通账户）           |
| `/showAccount`         | GET      | Logged     | Show my account（查询我的账户）        |
| `/transaction.jsp`     | GET      | Logged     | Deposit/Withdraw page（存取款页）    |
| `/showUsers`           | GET      | Admin only | List all users（JSTL/EL 渲染用户表格） |
| `/showProducts`        | GET      | Public     | **Product list demo（JSTL/EL）** |

---

## 🧩 JSTL / EL Cheatsheet | 快速用法

* 遍历集合：

  ```jsp
  <c:forEach var="u" items="${users}">
    ${u.id} - ${u.username}
  </c:forEach>
  ```
* 条件与数值格式化：

  ```jsp
  <c:choose>
    <c:when test="${p.discount > 0}">
      <fmt:formatNumber value="${p.price * (1 - p.discount)}" maxFractionDigits="2"/>
    </c:when>
    <c:otherwise>${p.price}</c:otherwise>
  </c:choose>
  ```
* 截断描述：

  ```jsp
  <c:choose>
    <c:when test="${fn:length(p.description) > 50}">
      ${fn:substring(p.description, 0, 50)}...
    </c:when>
    <c:otherwise>${p.description}</c:otherwise>
  </c:choose>
  ```
* 图片 URL（自动加 Context Path）：

  ```jsp
  <c:url value="${p.image}" var="imgUrl"/><img src="${imgUrl}">
  ```

---

## 🖼️ Product Images | 产品图片

* 放置位置：`src/main/webapp/imgs/`
* 示例文件：`xiaomi.png`、`gree.png`、`huawei.png`
* **注意文件名一致性**：`ShowProductsServlet` 中第二条测试数据使用 `"/imgs/gree.png"`（不是 `geli.png`）。

---

## ⚙️ DB & Run | 数据库与运行

* 在 `JDBCUtil` 中配置 JDBC URL/账号/密码。
* 建库 / 建表（示例）：

  ```sql
  CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL
  );
  CREATE TABLE account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE NOT NULL,
    balance DECIMAL(18,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user(id)
  );
  ```
* 运行：IDE 配好 Tomcat（8.0.x），添加 Artifact，启动；或打包 WAR 放入 `tomcat/webapps/`。

---

## 🎨 UI Notes | 界面说明

* 全站统一按钮样式（首页“查看产品列表”按钮与产品页按钮同款）。
* `showProducts.jsp` 顶部提供 **“← 返回上一级”** 按钮，返回首页。
* 卡片悬浮、圆角、轻阴影与 `object-fit: contain` 确保图片等比缩放不变形。

---


需要把 README 里某些字段（比如项目包名、Context Path、端口）替换成你实际的环境，告诉我具体值我可以帮你再细化一版。
