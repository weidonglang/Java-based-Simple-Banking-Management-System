package com.web.dao;

import com.web.entity.User;

import java.util.List;

/**
 * 用户相关 DAO 接口
 */
public interface UserDao {

    // 登录
    User findByUsernameAndPassword(String username, String password);

    // 查重 / 精确查
    User findByUsername(String username);

    // 新增用户
    boolean addUser(User user);

    // 查询所有用户
    List<User> findAll();

    // 按 id 查询
    User findById(int id);

    // 更新用户
    int update(User user);

    // 删除用户
    int deleteById(int id);

    // 模糊查询（按用户名）
    List<User> findByUsernameLike(String keyword);

    // 统计用户总数（分页用）
    int countAll();

    // 分页查询
    List<User> findByPage(int offset, int size);
}
