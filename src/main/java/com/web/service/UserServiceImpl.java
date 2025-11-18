package com.web.service;

import com.web.dao.UserDao;
import com.web.dao.UserDaoImpl;
import com.web.entity.Page;
import com.web.entity.User;
import com.web.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户业务层实现
 */
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

    @Override
    public boolean deleteById(int id) {
        return userDao.deleteById(id) > 0;
    }

    @Override
    public boolean deleteUserDeep(int userId) {
        final String delAccountSql = "DELETE FROM account WHERE user_id=?";
        final String delUserSql = "DELETE FROM user WHERE id=?";

        try (Connection conn = JDBCUtil.getConnection()) {
            boolean oldAutoCommit = conn.getAutoCommit();
            try {
                conn.setAutoCommit(false);

                // 1. 先删 account
                try (PreparedStatement ps1 = conn.prepareStatement(delAccountSql)) {
                    ps1.setInt(1, userId);
                    int accRows = ps1.executeUpdate();
                    System.out.println("[DeepDel] account deleted rows=" + accRows);
                }

                // 2. 再删 user
                int userRows;
                try (PreparedStatement ps2 = conn.prepareStatement(delUserSql)) {
                    ps2.setInt(1, userId);
                    userRows = ps2.executeUpdate();
                    System.out.println("[DeepDel] user deleted rows=" + userRows);
                }

                conn.commit();
                System.out.println("[DeepDel] commit ok");
                conn.setAutoCommit(oldAutoCommit);
                return userRows > 0;
            } catch (SQLException e) {
                System.out.println("[DeepDel] exception: " + e.getMessage()
                        + ", sqlState=" + e.getSQLState()
                        + ", code=" + e.getErrorCode());
                try {
                    conn.rollback();
                    System.out.println("[DeepDel] rollback done");
                } catch (SQLException ignore) {}
                try {
                    conn.setAutoCommit(oldAutoCommit);
                } catch (SQLException ignore) {}
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> findByUsernameLike(String keyword) {
        return userDao.findByUsernameLike(keyword);
    }

    @Override
    public Page<User> findUsersByPage(int currentPage, int size) {
        int total = userDao.countAll();
        if (size <= 0) size = 10;

        int totalPage = (int) Math.ceil(total * 1.0 / size);
        if (totalPage == 0) totalPage = 1;

        if (currentPage < 1) {
            currentPage = 1;
        } else if (currentPage > totalPage) {
            currentPage = totalPage;
        }

        int offset = (currentPage - 1) * size;
        List<User> data = userDao.findByPage(offset, size);

        Page<User> page = new Page<>();
        page.setTotal(total);
        page.setSize(size);
        page.setCurrentPage(currentPage);
        page.setTotalPage(totalPage);
        page.setData(data);

        return page;
    }
}
