package com.tianshi.web.service;

import com.tianshi.web.entity.Account;

/**
 * 账户业务逻辑接口
 */
public interface AccountService {

    /**
     * 开通银行账户的业务
     * @param userId 要开通账户的用户ID
     * @return 返回一个结果码：
     * 1 代表成功
     * 0 代表失败 (未知错误)
     * -1 代表用户已拥有账户，无需重复开通
     */
    int createAccount(Integer userId);

    /**
     * 根据用户ID获取账户信息
     * @param userId 用户ID
     * @return Account 对象或 null
     */
    Account getAccountByUserId(Integer userId);

    /**
     * 执行转账业务 (存款/取款)
     * @param userId 用户ID
     * @param amount 操作金额
     * @param type 操作类型 ("deposit" 或 "withdraw")
     * @return 返回一个结果对象，包含操作是否成功及最新余额等信息
     */
    TransactionResult performTransaction(Integer userId, double amount, String type);
}