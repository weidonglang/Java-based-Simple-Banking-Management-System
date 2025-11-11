package com.web.dao;

import com.web.entity.User;
import com.web.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        final String sql = "SELECT id, username, password FROM `user` WHERE username=? AND password=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.out.println("[DAO][findByUsernameAndPassword] sqlState=" + e.getSQLState() + ", code=" + e.getErrorCode());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User findByUsername(String username) {
        final String sql = "SELECT id, username, password FROM `user` WHERE username=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.out.println("[DAO][findByUsername] sqlState=" + e.getSQLState() + ", code=" + e.getErrorCode());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean addUser(User user) {
        final String sql = "INSERT INTO `user` (username, password) VALUES (?, ?)";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("[DAO][addUser] sqlState=" + e.getSQLState() + ", code=" + e.getErrorCode());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<User> findAll() {
        final String sql = "SELECT id, username, password FROM `user` ORDER BY id ASC";
        List<User> list = new ArrayList<>();
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                list.add(u);
            }
        } catch (SQLException e) {
            System.out.println("[DAO][findAll] sqlState=" + e.getSQLState() + ", code=" + e.getErrorCode());
            e.printStackTrace();
        }
        return list;
    }

    // ===== 新增：根据 id 查询，用于回显旧值 =====
    @Override
    public User findById(int id) {
        final String sql = "SELECT id, username, password FROM `user` WHERE id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println("[DAO] findById sql=" + sql + ", id=" + id);
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setPassword(rs.getString("password"));
                    return u;
                }
            }
        } catch (SQLException e) {
            System.out.println("[DAO][findById] sqlState=" + e.getSQLState() + ", code=" + e.getErrorCode());
            e.printStackTrace();
        }
        return null;
    }

    // ===== 新增：提交修改（仅示例更新 username；可按需扩展） =====
    @Override
    public int update(User user) {
        final String sql = "UPDATE `user` SET username=? WHERE id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println("[DAO] update sql=" + sql + ", id=" + user.getId() + ", username=" + user.getUsername());
            ps.setString(1, user.getUsername());
            ps.setInt(2, user.getId());
            int rows = ps.executeUpdate();
            System.out.println("[DAO] update affectedRows=" + rows);
            return rows;
        } catch (SQLException e) {
            System.out.println("[DAO][update] sqlState=" + e.getSQLState() + ", code=" + e.getErrorCode());
            e.printStackTrace();
        }
        return 0;
    }

    // ===== 新增：删除，并输出受影响行数 / 外键错误 =====
    @Override
    public int deleteById(int id) {
        final String sql = "DELETE FROM `user` WHERE id=?";
        try (Connection conn = JDBCUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            System.out.println("[DAO] deleteById sql=" + sql + ", id=" + id + ", autoCommit=" + conn.getAutoCommit());
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println("[DAO] deleteById affectedRows=" + rows);
            return rows;
        } catch (SQLIntegrityConstraintViolationException fk) {
            System.out.println("[DAO][FK] " + fk.getMessage() + ", sqlState=" + fk.getSQLState() + ", errorCode=" + fk.getErrorCode());
            throw new RuntimeException("FK_CONSTRAINT", fk);
        } catch (SQLException e) {
            System.out.println("[DAO][deleteById] " + e.getMessage() + ", sqlState=" + e.getSQLState() + ", code=" + e.getErrorCode());
            e.printStackTrace();
        }
        return 0;
    }
}
