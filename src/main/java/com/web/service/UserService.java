package com.web.service;
import com.web.entity.User;
import java.util.List; // 确保导入 List
public interface UserService {
    User login(String username, String password);
    boolean register(String username, String password);
    List<User> findAllUsers();
}