package com.web.service;

import com.web.entity.User;
import java.util.List;

public interface UserService {
    User login(String username, String password);
    boolean register(String username, String password);
    List<User> findAllUsers();

    User getById(int id);
    boolean update(User user);

    // 旧的纯删除（单表）可保留：
    boolean deleteById(int id);

    // ✅ 新增：手动事务删除（先删子表 account，再删 user）
    boolean deleteUserDeep(int userId);
}
