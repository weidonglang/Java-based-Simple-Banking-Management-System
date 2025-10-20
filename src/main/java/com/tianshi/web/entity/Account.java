package com.tianshi.web.entity;

/**
 * 账户实体类，对应数据库中的 account 表
 */
public class Account {
    private Integer id;       // 账户ID
    private Double balance;   // 账户余额
    private Integer userId;   // 关联的用户ID (外键)

    // 无参构造方法
    public Account() {
    }

    // 全参构造方法
    public Account(Integer id, Double balance, Integer userId) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;
    }

    // Getter 和 Setter 方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", userId=" + userId +
                '}';
    }
}