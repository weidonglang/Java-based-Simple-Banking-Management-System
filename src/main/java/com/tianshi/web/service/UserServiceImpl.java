package com.tianshi.web.service;
import com.tianshi.web.dao.UserDao;
import com.tianshi.web.dao.UserDaoImpl;
import com.tianshi.web.entity.User;
import java.util.List;
public class UserServiceImpl implements UserService {
    private final UserDao userDao = new UserDaoImpl();
    @Override
    public User login(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return null;
        }
        return userDao.findByUsernameAndPassword(username, password);
    }
    @Override
    public boolean register(String username, String password) {
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            return false;
        }
        User existingUser = userDao.findByUsername(username);
        if (existingUser != null) {
            System.out.println("注册失败：用户名 '" + username + "' 已存在。");
            return false;
        }
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password);
        return userDao.addUser(newUser);
    }
    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }
}