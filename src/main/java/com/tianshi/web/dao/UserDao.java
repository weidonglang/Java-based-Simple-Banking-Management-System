package com.tianshi.web.dao;
import com.tianshi.web.entity.User;
import java.util.List; // 确保导入 List
public interface UserDao {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    boolean addUser(User user);
    List<User> findAll();
}