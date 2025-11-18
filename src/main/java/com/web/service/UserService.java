package com.web.service;
import com.web.entity.User;
import java.util.List;
public interface UserService {
    User login(String username, String password);
    boolean register(String username, String password);
    List<User> findAllUsers();
    User getById(int id);
    boolean update(User user);
    boolean deleteById(int id);
    boolean deleteUserDeep(int userId);
}
