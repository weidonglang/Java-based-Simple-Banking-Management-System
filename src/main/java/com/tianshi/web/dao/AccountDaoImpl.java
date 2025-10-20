package com.tianshi.web.dao;

import com.tianshi.web.entity.Account;
import com.tianshi.web.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * AccountDao 接口的实现类
 * 使用 JDBC 操作数据库
 */
public class AccountDaoImpl implements AccountDao {

    @Override
    public Account findByUserId(Integer userId) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account account = null;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "SELECT id, balance, user_id FROM account WHERE user_id = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setBalance(rs.getDouble("balance"));
                account.setUserId(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 实际项目中应使用日志框架
        } finally {
            JDBCUtil.close(conn, ps, rs);
        }
        return account;
    }

    @Override
    public boolean addAccount(Account account) {
        Connection conn = null;
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            conn = JDBCUtil.getConnection();
            String sql = "INSERT INTO account(balance, user_id) VALUES (?, ?)";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, account.getBalance());
            ps.setInt(2, account.getUserId());
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(conn, ps);
        }
        return rowsAffected > 0;
    }

    @Override
    public Account findByUserIdForUpdate(Integer userId, Connection conn) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Account account = null;

        try {
            // 注意：这里使用传入的 conn 对象，而不是从 JDBCUtil 获取新连接
            String sql = "SELECT id, balance, user_id FROM account WHERE user_id = ? FOR UPDATE";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setBalance(rs.getDouble("balance"));
                account.setUserId(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 在事务中，只关闭 PreparedStatement 和 ResultSet，不关闭 Connection
            JDBCUtil.close(null, ps, rs);
        }
        return account;
    }

    @Override
    public boolean updateAccount(Account account, Connection conn) {
        PreparedStatement ps = null;
        int rowsAffected = 0;

        try {
            String sql = "UPDATE account SET balance = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setDouble(1, account.getBalance());
            ps.setInt(2, account.getId());
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(null, ps);
        }
        return rowsAffected > 0;
    }
}