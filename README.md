# Lab2 – Banking Demo (JSP/Servlet)

*(中文 / English Bilingual README)*

A minimal banking-style web app built with **JSP + Servlet + JDBC** running on **Tomcat 8**. It supports user registration & login, account creation, deposit/withdraw, viewing own account, and an **admin-only “list all users”** feature enforced by a Servlet **Filter**.

一个基于 **JSP + Servlet + JDBC**（Tomcat 8）的极简银行示例应用，包含注册登录、开通账户、存取款、查询我的账户，以及通过 **Servlet 过滤器** 强制限制的 **管理员专享“查询所有用户信息”** 功能。

---

## ✨ Features | 功能特性

* User auth: register & login（注册/登录）
* One account per user: create account（每人一个账户，开通账户）
* Deposit / withdraw with transaction handling（存取款，带事务）
* Show **my** account balance（查询我的账户）
* **Admin-only**: show **all** users (protected by `AdminAuthFilter`)（仅管理员：查询所有用户，受 `AdminAuthFilter` 保护）
* Session keys used: `loginUser`, `userId`, `username`, `isAdmin`（会话键）

> The admin is recognized when `session.isAdmin == true`. In the current setup, logging in as username `admin` sets `isAdmin=true`. You can later switch to DB-backed roles. Filters intercept and process requests/responses as specified by the Servlet standard. ([Oracle][1])

---

## 🧱 Tech Stack | 技术栈

* **Java 8**, **Servlet/JSP**, **JSTL 1.2+**（核心标签库）
* **Tomcat 8.0.x**
* **JDBC**（数据库连接由 `JDBCUtil` 配置）
* Frontend: JSP + minimal CSS

> JSTL core taglib URI in your JSPs:
> `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>` ([docs.oracle.com][2])

---

## 📁 Project Layout | 目录结构（示例）

```
src/main/java/
  com/tianshi/web/entity/         # User, Account
  com/tianshi/web/dao/            # UserDao, AccountDao (+ impl)
  com/tianshi/web/service/        # UserService, AccountService (+ impl)
  com/tianshi/web/servlet/        # LoginServlet, CreateAccountServlet, ...
  com/tianshi/web/filter/         # AdminAuthFilter (管理员过滤器)
  com/tianshi/web/util/           # JDBCUtil

src/main/webapp/
  index.jsp
  login.jsp
  regist.jsp
  transaction.jsp
  showAccount.jsp
  accountResult.jsp
  easterEgg.jsp
  WEB-INF/web.xml  (如使用 XML 注册过滤器/Servlet)
```

---

## 🔐 Admin Guard with Servlet Filter | 管理员过滤器

**Goal**: Only admin can access `GET /showUsers`.

**Approach**: Add `AdminAuthFilter` and map it to `/showUsers`. In login success, set `session.isAdmin = (username.equalsIgnoreCase("admin"))`. Filters can be declared via `@WebFilter(urlPatterns=...)` or in `web.xml` with `<filter>` + `<filter-mapping>`. ([jakarta.ee][3])

**Why Filter?** Filters sit in the chain before servlets/JSPs to pre/post-process requests/responses in a reusable way (e.g., auth). ([Oracle][1])

**Annotation mapping example（注解示例）**

```java
@WebFilter(urlPatterns = {"/showUsers"})
public class AdminAuthFilter implements Filter {
  public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest r = (HttpServletRequest) req;
    HttpServletResponse p = (HttpServletResponse) resp;
    HttpSession s = r.getSession(false);

    Integer userId = (s==null)?null:(Integer) s.getAttribute("userId");
    if (userId == null) { p.sendRedirect(r.getContextPath()+"/login.jsp"); return; }

    boolean isAdmin = Boolean.TRUE.equals(s.getAttribute("isAdmin"));
    if (!isAdmin) {
      r.setAttribute("msg","权限不足：只有管理员可以访问“查询所有用户”。");
      r.getRequestDispatcher("/index.jsp").forward(r,p); return;
    }
    chain.doFilter(req, resp);
  }
}
```

**web.xml mapping example（XML 示例）**

```xml

<filter>
    <filter-name>AdminAuthFilter</filter-name>
    <filter-class>filter.com.web.AdminAuthFiltercom.web.filter.AdminAuthFilter</filter-class>
</filter>
<filter-mapping>
<filter-name>AdminAuthFilter</filter-name>
<url-pattern>/showUsers</url-pattern>
</filter-mapping>
```

> Note: Filters are invoked in the order they appear in the mapping list; URL patterns follow standard servlet matching rules. ([docs.oracle.com][4])

---

## 👤 Auth & Session | 认证与会话

* On login success (`POST /login`), the app sets:

  * `loginUser` (User object), `userId`, `username`
  * `isAdmin` (currently true iff username is `admin`)
* `index.jsp` shows “查询所有用户信息” button **only** if `sessionScope.isAdmin` is true (front-end guard). Real enforcement is the Filter (back-end guard).

---

## 🔀 Transactions | 事务与并发（简述）

* Service layer controls JDBC transactions (commit/rollback) for deposit/withdraw.
* DAO offers `findByUserIdForUpdate(..., conn)` + `updateAccount(..., conn)` to cooperate within one transaction.
* (Optional) For high concurrency, you can use a single atomic SQL for withdraw (e.g., `balance >= amount` guard) to avoid read-modify-write races.

---

## 🧪 Pages & Endpoints | 页面与端点（简表）

| Page / API             | Method   | Access         | Purpose                     |
| ---------------------- | -------- | -------------- | --------------------------- |
| `/login.jsp`, `/login` | GET/POST | Public         | Login（登录）                   |
| `/regist.jsp`          | GET      | Public         | Register（注册）                |
| `/index.jsp`           | GET      | Logged/All     | Home（首页）                    |
| `/createAccount`       | POST     | Logged         | Create account（开通账户）        |
| `/showAccount`         | GET      | Logged         | Show my account（查询我的账户）     |
| `/transaction.jsp`     | GET      | Logged         | Deposit/Withdraw page（存取款页） |
| `/showUsers`           | GET      | **Admin only** | List all users（查询所有用户）      |

> If you add more endpoints later, map them similarly and extend the filter patterns (e.g., `/admin/*`). You can map multiple URL patterns to one filter. ([Stack Overflow][5])

---

## ⚙️ Configure DB | 数据库配置

* Edit `JDBCUtil` to set your JDBC driver, URL, username/password.
* Minimal schema (参考示例，可按你现有表调整)：

  ```sql
  -- user table
  CREATE TABLE user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(64) UNIQUE NOT NULL,
    password VARCHAR(128) NOT NULL
    -- optional: is_admin TINYINT(1) DEFAULT 0
  );

  -- account table
  CREATE TABLE account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT UNIQUE NOT NULL,
    balance DECIMAL(18,2) NOT NULL DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES user(id)
  );
  ```
* 在当前实现里，“管理员”由登录名是否为 `admin` 来判定；你也可以在 `user` 表新增 `is_admin/role` 字段，并在登录成功后把它写入 `session.isAdmin`。

---

## 🚀 Run & Deploy | 运行与部署

### Local run in IDE | IDE 本地运行

1. Install **JDK 8** & **Tomcat 8.0.x**.
2. Import the project into IntelliJ IDEA / Eclipse as a **Web** app.
3. Configure Tomcat server in IDE, set project’s context path, add artifact, run.

### Build & deploy WAR | 打包 WAR 并部署

* Package the app as a `.war` and drop it under Tomcat’s `webapps/`. With **autoDeploy** enabled, Tomcat will hot-deploy it. You can also use the Manager App or `curl`-based deployment. ([Apache Tomcat][6])

---

## 🧩 JSP Notes | JSP 小贴士

* JSTL core taglib in JSP:
  `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>`
  Ensure JSTL libraries are on the container’s classpath. ([docs.oracle.com][2])
* Servlet basics & lifecycle: see Oracle/EE tutorials. ([docs.oracle.com][7])

---

## 🛡️ Security Notes | 安全提示

* Passwords should be stored hashed (e.g., BCrypt) instead of plain text.
* Prefer `BigDecimal` for money to avoid floating precision errors.
* Consider CSRF/Session fixation hardening if you extend features; Tomcat ships example filters and references. ([Apache Tomcat][8])

---

## 🧭 Roadmap | 后续路线

* [ ] Switch admin check to DB-backed role (`is_admin` column)
* [ ] Replace double with `BigDecimal` in balances
* [ ] Add `LogoutServlet` and session invalidation (if not already present)
* [ ] Validation & i18n for forms
* [ ] Unit/integration tests

---

## 🤝 Contributing | 参与贡献

Issues & PRs are welcome. Please format code consistently and keep DAO/Service/Servlet responsibilities separated.

---

## 📜 License | 许可证

Add your preferred license (e.g., MIT/Apache-2.0) at repository root as `LICENSE`.
