package com.web.dao;

import com.web.entity.Account;
import java.sql.Connection; // 需要导入

/**
 * 账户数据访问对象接口
 * 定义了针对 Account 表的数据库操作规范
 */
public interface AccountDao {

    /**
     * 根据用户ID查询账户信息
     * @param userId 用户ID
     * @return 如果找到，返回 Account 对象；否则返回 null
     */
    Account findByUserId(Integer userId);

    /**
     * 为指定用户创建一个新账户
     * @param account 包含新账户信息的 Account 对象 (通常 id 为 null, balance 为初始值)
     * @return 如果创建成功，返回 true；否则返回 false
     */
    boolean addAccount(Account account);

    /**
     * 根据用户ID查询账户信息，并为更新操作锁定该行
     * @param userId 用户ID
     * @param conn 外部传入的数据库连接，用于事务控制
     * @return 如果找到，返回 Account 对象；否则返回 null
     */
    Account findByUserIdForUpdate(Integer userId, Connection conn);

    /**
     * 更新账户信息 (如此处主要用于更新余额)
     * @param account 包含最新信息的 Account 对象
     * @param conn 外部传入的数据库连接，用于事务控制
     * @return 如果更新成功，返回 true；否则返回 false
     */
    boolean updateAccount(Account account, Connection conn);
}