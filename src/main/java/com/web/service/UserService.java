package com.web.service;

import com.web.entity.Page;
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

    // 查询所有
    List<User> findAllUsers();

    // 按 id 查
    User getById(int id);

    // 更新
    boolean update(User user);

    // 简单删除（只删 user 表）
    boolean deleteById(int id);

    // 深度删除（先删 account 再删 user）
    boolean deleteUserDeep(int userId);

    // 模糊查询
    List<User> findByUsernameLike(String keyword);

    // 分页查询
    Page<User> findUsersByPage(int currentPage, int size);
}
