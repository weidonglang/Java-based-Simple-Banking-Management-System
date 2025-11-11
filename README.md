# Lab2 – Banking Demo (JSP/Servlet)

*(中文 / English Bilingual README)*

A minimal banking-style web app built with **JSP + Servlet + JDBC** on **Tomcat 8**. It now supports **login-aware home page**, **admin-only user management** with **edit & delete**, JSTL/EL-based views, and **manual transactional cascade delete** (`account` → `user`).

这是一个基于 **JSP + Servlet + JDBC**（Tomcat 8）的极简银行示例应用；本次更新实现了**登录态感知首页**、**管理员用户管理（含修改/删除）**、基于 **JSTL/EL** 的视图渲染，以及**不改外键、在代码里按依赖顺序的手动事务级联删除**。

---

## ✅ What’s New | 本次更新

* **Home (index.jsp)**：使用 JSTL/EL 判断登录态与角色

  * 未登录：显示“登录/注册”；已登录：展示“欢迎，XX”；管理员：显示“用户管理”；普通用户：显示“我的账户/退出”。
  * 所有链接通过 `<c:url>` 生成，**自动带 ContextPath**，避免 `/app/app/...` 双前缀导致 404。([mail-archive.com][1])
* **User Management (showUsers.jsp)**：表格**新增操作列**（修改/删除）。
* **Edit User**：`toUpdateUser` → `updateUser.jsp`（回显）→ `updateUserById`（保存，**PRG** 避免刷新重复提交）。([geeksforgeeks.org][2])
* **Delete User**：新增 `DeleteUserByIdServlet`，在 **Service** 中开启事务：
  **先删 `account`（子表）→ 再删 `user`（父表）**，一次提交，避免 MySQL 外键 **1451** 错误。([Bytebase][3])
* **UTF-8 提示不再 “????”**：`sendRedirect(...?msg=中文)` 前做 `URLEncoder.encode`；Tomcat Connector 建议配 `URIEncoding="UTF-8"`。([baeldung.com][4])
* **Servlet 映射更稳**：使用 `@WebServlet` 的同时在 `web.xml` 可加显式 `<servlet-mapping>`；若 `metadata-complete="true"`，容器**会忽略**注解。([Stack Overflow][5])
* **调试输出**：DAO/Servlet 打印 SQL 影响行数与错误码，便于在 `catalina.log` 快速定位。

---

## ✨ Features | 功能特性

* Register & Login（注册/登录）
* Create one account per user（每人一个账户）
* Deposit / Withdraw（存取款；含事务）
* Show **my** account（查询我的账户）
* **Admin-only**: list/edit/delete users（仅管理员；带修改/删除）
* JSTL/EL views（`<c:forEach>`, `<c:choose>`, `<c:url>` …）

Session keys：`loginUser`, `userId`, `username`, `isAdmin`

---

## 🧱 Tech Stack | 技术栈

* **Java 8**, **Servlet 3.x / JSP**, **JSTL 1.2+**
* **Tomcat 8.0.x**
* **JDBC**（`JDBCUtil`）
* **MySQL**（InnoDB 外键）

> `<c:url value="/path">` 会自动拼接 ContextPath 并处理 URL 重写；无需再手动加 `${ctx}`。

---

## 📁 Project Layout | 目录

```
src/main/java/
  com/web/entity/             # User, Account, Product
  com/web/dao/                # UserDao + Impl, AccountDao + Impl
  com/web/service/            # UserService + Impl, AccountService + Impl
  com/web/servlet/            # Login, Regist, CreateAccount, ShowAccount, 
                              # ShowUsers, ShowProducts,
                              # ToUpdateUser, UpdateUserById, DeleteUserById   ← NEW
  com/web/filter/             # AdminAuthFilter（保护 /showUsers 等）
  com/web/util/               # JDBCUtil

src/main/webapp/
  index.jsp                   # 登录态感知首页（JSTL/EL）
  login.jsp / regist.jsp
  transaction.jsp
  showAccount.jsp / accountResult.jsp
  showUsers.jsp               # 操作列（修改/删除）
  updateUser.jsp              # 回显 + 提交保存
  showProducts.jsp
  imgs/                       # 静态图片
  WEB-INF/web.xml
```

---

## 🧪 Pages & Endpoints | 页面与端点

| Page / API             | Method   | Access       | Purpose                                   |
| ---------------------- | -------- | ------------ | ----------------------------------------- |
| `/index.jsp`           | GET      | All / Logged | Home（登录态/角色感知）                            |
| `/login.jsp`, `/login` | GET/POST | Public       | Login                                     |
| `/regist.jsp`          | GET      | Public       | Register                                  |
| `/createAccount`       | POST     | Logged       | Create account                            |
| `/showAccount`         | GET      | Logged       | Show my account                           |
| `/showUsers`           | GET      | **Admin**    | List users（JSTL 表格）                       |
| `/toUpdateUser?id=`    | GET      | **Admin**    | 跳转编辑页（表单回显）                               |
| `/updateUserById`      | POST     | **Admin**    | 提交保存（**PRG**） ([geeksforgeeks.org][2])    |
| `/deleteUserById?id=`  | GET      | **Admin**    | 删除（Service 事务内先删子表 `account` → 再删 `user`） |

---

## ⚙️ DB Schema | 数据库

```sql
CREATE TABLE `user` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(64) UNIQUE NOT NULL,
  `password` VARCHAR(128) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `account` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT NOT NULL UNIQUE,
  `balance` DECIMAL(18,2) NOT NULL DEFAULT 0,
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB;
```

> 为什么删除用户会被拦住？
> 当子表引用父表时，直接删父表会报 **ERROR 1451 (SQLSTATE 23000)**。我们采用**方案 B：不改外键**，在 Service 里**按依赖顺序手动删除**（`account` → `user`），一次事务提交。若未来改为库层级联，可把外键改成 `ON DELETE CASCADE`。

---

## 🔐 Security & Guard | 权限与拦截

* `AdminAuthFilter` 保护：`/showUsers`, `/toUpdateUser`, `/updateUserById`, `/deleteUserById`
* 可选 CSRF：`updateUser.jsp` 表单带 token，Servlet 校验。

---

## 🚀 Run | 运行

1. 配置 `JDBCUtil` 的 URL/用户名/密码；建库建表（上节 SQL）。
2. IDE 配置 Tomcat 8（添加 Artifact），或打包 WAR 丢到 `webapps/`。
3. 确保 GET/重定向参数使用 UTF-8：

   * 重定向消息：`URLEncoder.encode(msg, "UTF-8")`；
   * Tomcat `conf/server.xml` 的 HTTP Connector 增加 `URIEncoding="UTF-8"`（推荐）。([baeldung.com][4])

---

## 🧩 Implementation Notes | 实现要点

* **JSTL URL 生成**：使用

  ```jsp
  <c:url var="editUrl" value="/toUpdateUser"><c:param name="id" value="${u.id}"/></c:url>
  <a href="${editUrl}">修改</a>
  ```

  *不要*再手动拼 `${pageContext.request.contextPath}`，否则可能出现 `/context/context/...`。

* **PRG 模式**：编辑提交后 `sendRedirect("/showUsers?msg=...")`，避免刷新重复提交。

* **Servlet 映射**：

  * 默认使用 `@WebServlet("/deleteUserById")`；
  * 若 `web.xml` 设置了 `metadata-complete="true"`，容器会**忽略注解**，需在 `web.xml` **显式 `<servlet-mapping>`**。

* **手动事务级联删除（方案 B）**：
  在 `UserServiceImpl.deleteUserDeep(id)` 中：`setAutoCommit(false)` → `DELETE FROM account WHERE user_id=?` → `DELETE FROM user WHERE id=?` → `commit()`；异常 `rollback()`。
