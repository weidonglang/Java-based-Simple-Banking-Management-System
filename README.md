# Java Simple Banking Management System

A small JSP/Servlet/JDBC banking demo for Tomcat 8. The app includes login,
account creation, balance operations, and admin-only user management.

## Security Baseline

This project is still an educational servlet example, but the dangerous defaults
have been tightened:

- User deletion is a state-changing operation and must use `POST /deleteUserById`.
  `GET /deleteUserById?id=...` now returns HTTP 405.
- Admin state-changing forms require a CSRF token. The token is stored in the
  session and submitted through a hidden `csrfToken` field.
- Passwords are not stored as plain text for new registrations or password
  changes. They are stored as salted PBKDF2 hashes in the existing `user.password`
  column.
- Existing plain-text passwords are accepted only for compatibility. On the next
  successful login, they are upgraded to the PBKDF2 format automatically.

This does not make the project production-grade banking software. For real
financial systems, use a maintained framework, mature authentication/session
controls, audited transaction boundaries, security logging, rate limiting, TLS
hardening, and dedicated security review.

## Stack

- Java 8
- Servlet 3.x / JSP / JSTL
- Tomcat 8
- JDBC with Druid connection pool
- MySQL / InnoDB

## Main Endpoints

| Endpoint | Method | Access | Purpose |
| --- | --- | --- | --- |
| `/index.jsp` | GET | All | Home page |
| `/login` | GET/POST | Public | Login |
| `/regist` | POST | Public | Register |
| `/createAccount` | POST | Logged in | Create one account |
| `/showAccount` | GET | Logged in | Show current user's account |
| `/showUsers` | GET | Admin | List users |
| `/showUsersByPage` | GET | Admin | Paginated user list |
| `/searchUser` | GET | Admin | Search users |
| `/toUpdateUser?id=` | GET | Admin | Open edit form and issue CSRF token |
| `/updateUserById` | POST | Admin | Update username/password with CSRF validation |
| `/deleteUserById` | POST | Admin | Delete user with CSRF validation |

## Database Schema

```sql
CREATE TABLE `user` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `username` VARCHAR(64) UNIQUE NOT NULL,
  `password` VARCHAR(255) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `account` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `user_id` INT NOT NULL UNIQUE,
  `balance` DECIMAL(18,2) NOT NULL DEFAULT 0,
  CONSTRAINT `account_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user`(`id`)
) ENGINE=InnoDB;
```

The `password` column should be at least `VARCHAR(255)` because the PBKDF2
storage format includes the algorithm marker, iteration count, salt, and hash:

```text
pbkdf2_sha256$120000$<base64-salt>$<base64-hash>
```

## Delete Behavior

The application keeps the existing foreign key without `ON DELETE CASCADE`.
`UserServiceImpl.deleteUserDeep(id)` deletes dependent `account` rows first and
then deletes the `user` row in one JDBC transaction:

```text
setAutoCommit(false)
DELETE FROM account WHERE user_id=?
DELETE FROM user WHERE id=?
commit()
rollback() on error
```

## Run

1. Configure the database connection in `src/main/resources/db.properties`.
2. Create the schema above.
3. Build the WAR:

```bash
mvn clean package
```

4. Deploy `target/Lab2_war.war` to Tomcat 8.

If redirect messages contain non-ASCII text, configure the Tomcat HTTP Connector
with `URIEncoding="UTF-8"`.
