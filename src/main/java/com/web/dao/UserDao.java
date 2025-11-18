package com.web.dao;

import com.web.entity.User;

import java.util.List;

/**
 * 用户相关的 DAO 接口
 */
public interface UserDao {

    // 登录用
    User findByUsernameAndPassword(String username, String password);

    // 按用户名精确查找（注册重名校验等）
    User findByUsername(String username);

    // 新增用户
    boolean addUser(User user);

    // 查询全部用户
    List<User> findAll();

    // 编辑 / 删除所需
    User findById(int id);

    int update(User user);

    int deleteById(int id);

    // 按用户名关键字模糊查询
    List<User> findByUsernameLike(String keyword);
}
