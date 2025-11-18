package com.web.dao;
import com.web.entity.User;
import java.util.List;

public interface UserDao {
    // 现有功能
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
    boolean addUser(User user);
    List<User> findAll();

    // 编辑/删除所需
    User findById(int id);
    int update(User user);
    int deleteById(int id);
}
