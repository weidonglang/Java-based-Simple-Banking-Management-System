package com.tianshi.web.entity;

/**
 * 用户实体类，对应数据库中的 user 表
 */
public class User {
    private Integer id;       // 用户ID
    private String username;  // 用户名
    private String password;  // 密码

    // 无参构造方法
    public User() {
    }

    // 全参构造方法
    public User(Integer id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    // Getter 和 Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}