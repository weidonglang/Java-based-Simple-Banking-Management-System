package com.web.service;

import com.web.entity.User;

import java.util.List;

/**
 * 用户相关业务接口
 */
public interface UserService {

    // 登录
    User login(String username, String password);

    // 注册
    boolean register(String username, String password);

    // 查询所有用户
    List<User> findAllUsers();

    // 按 id 查询
    User getById(int id);

    // 更新用户
    boolean update(User user);

    // 只删 user 表（保留 account）
    boolean deleteById(int id);

    // 先删 account 再删 user 的“深度删除”
    boolean deleteUserDeep(int userId);

    // 按用户名关键字模糊查询
    List<User> findByUsernameLike(String keyword);
}
