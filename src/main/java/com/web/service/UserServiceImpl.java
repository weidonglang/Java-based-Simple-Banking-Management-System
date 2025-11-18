package com.web.service;

import com.web.dao.UserDao;
import com.web.dao.UserDaoImpl;
import com.web.entity.User;
import com.web.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();

    @Override
    public User login(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }

    @Override
    public boolean register(String username, String password) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        return userDao.addUser(u);
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User getById(int id) {
        return userDao.findById(id);
    }

    @Override
    public boolean update(User user) {
        return userDao.update(user) > 0;
    }
    // 旧的“只删 user 表”保留
    @Override
    public boolean deleteById(int id) {
        return userDao.deleteById(id) > 0;
    }
    @Override
    public boolean deleteUserDeep(int userId) {
        final String delAccount = "DELETE FROM `account` WHERE user_id=?";
        final String delUser    = "DELETE FROM `user` WHERE id=?";

        try (Connection conn = JDBCUtil.getConnection()) {
            System.out.println("[DeepDel] begin, userId=" + userId);
            boolean oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                // ① 先删子表 account
                try (PreparedStatement ps1 = conn.prepareStatement(delAccount)) {
                    ps1.setInt(1, userId);
                    int accRows = ps1.executeUpdate();
                    System.out.println("[DeepDel] account deleted rows=" + accRows);
                }
                // ② 再删父表 user
                int userRows;
                try (PreparedStatement ps2 = conn.prepareStatement(delUser)) {
                    ps2.setInt(1, userId);
                    userRows = ps2.executeUpdate();
                    System.out.println("[DeepDel] user deleted rows=" + userRows);
                }
                conn.commit();
                System.out.println("[DeepDel] commit ok");
                // 恢复原状态
                conn.setAutoCommit(oldAutoCommit);
                return userRows > 0;
            } catch (SQLException e) {
                System.out.println("[DeepDel] exception: " + e.getMessage() +
                        ", sqlState=" + e.getSQLState() + ", code=" + e.getErrorCode());
                try { conn.rollback(); System.out.println("[DeepDel] rollback done"); } catch (SQLException ignore) {}
                try { conn.setAutoCommit(true); } catch (SQLException ignore) {}
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
