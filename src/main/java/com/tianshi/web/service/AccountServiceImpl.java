package com.tianshi.web.service;

import com.tianshi.web.dao.AccountDao;
import com.tianshi.web.dao.AccountDaoImpl;
import com.tianshi.web.entity.Account;
import com.tianshi.web.util.JDBCUtil;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * AccountService 接口的实现类
 */
public class AccountServiceImpl implements AccountService {

    // Service 层依赖 DAO 层
    private final AccountDao accountDao = new AccountDaoImpl();

    @Override
    public int createAccount(Integer userId) {
        // 1. 检查用户是否已经有账户
        Account existingAccount = accountDao.findByUserId(userId);
        if (existingAccount != null) {
            // 如果已经存在账户，返回特定代码 -1
            return -1;
        }

        // 2. 如果不存在，则创建新账户
        Account newAccount = new Account();
        newAccount.setUserId(userId);
        newAccount.setBalance(0.0); // 初始余额为0

        boolean success = accountDao.addAccount(newAccount);

        // 3. 根据 DAO 层返回的结果，返回业务代码
        return success ? 1 : 0;
    }

    @Override
    public Account getAccountByUserId(Integer userId) {
        return accountDao.findByUserId(userId);
    }

    @Override
    public TransactionResult performTransaction(Integer userId, double amount, String type) {
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
            // 1. 开始事务：关闭自动提交
            conn.setAutoCommit(false);

            // 2. 查询账户并使用行级锁（FOR UPDATE），防止并发问题
            Account account = accountDao.findByUserIdForUpdate(userId, conn);

            // 检查账户是否存在
            if (account == null) {
                conn.rollback(); // 回滚事务
                return new TransactionResult(false, "未找到您的账户。", null);
            }

            double currentBalance = account.getBalance();
            double newBalance;

            // 根据操作类型计算新余额
            if ("deposit".equals(type)) {
                // 存款
                newBalance = currentBalance + amount;
            } else if ("withdraw".equals(type)) {
                // 取款
                if (currentBalance < amount) {
                    conn.rollback(); // 余额不足，回滚事务
                    return new TransactionResult(false, "账户余额不足。", currentBalance);
                }
                newBalance = currentBalance - amount;
            } else {
                conn.rollback(); // 无效操作，回滚事务
                return new TransactionResult(false, "无效的操作类型。", currentBalance);
            }

            // 3. 更新数据库中的余额
            account.setBalance(newBalance);
            boolean updateSuccess = accountDao.updateAccount(account, conn);

            if (updateSuccess) {
                // 4. 如果更新成功，提交事务
                conn.commit();
                return new TransactionResult(true, "操作成功！", newBalance);
            } else {
                // 5. 如果更新失败，回滚事务
                conn.rollback();
                return new TransactionResult(false, "更新余额时发生错误。", currentBalance);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // 出现任何SQL异常都必须回滚
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return new TransactionResult(false, "系统错误，数据库操作失败。", null);
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // 确保数据库连接最终被关闭
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}