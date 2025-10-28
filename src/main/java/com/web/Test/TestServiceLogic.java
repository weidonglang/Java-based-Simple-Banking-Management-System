package com.web.Test; // 您可以放在原来的 Test 包下

import com.web.entity.Account;
import com.web.entity.User;
import com.web.service.AccountService;
import com.web.service.AccountServiceImpl;
import com.web.service.TransactionResult;
import com.web.service.UserService;
import com.web.service.UserServiceImpl;

import java.util.List;
import java.util.Random;

/**
 * 业务逻辑层（Service Layer）测试类
 * 用于测试用户和账户相关的核心业务功能
 */
public class TestServiceLogic {

    // 实例化我们需要测试的 Service
    private static final UserService userService = new UserServiceImpl();
    private static final AccountService accountService = new AccountServiceImpl();

    public static void main(String[] args) {
        System.out.println("============== 业务逻辑测试开始 ==============");

        // 为了让测试可重复运行，我们每次都用一个随机的用户名
        String testUsername = "testUser" + new Random().nextInt(10000);
        String testPassword = "password123";

        // 依次执行各个功能的测试
        testUserRegistration(testUsername, testPassword);
        testUserLogin(testUsername, testPassword);
        testFindAllUsers();

        // 登录成功后，我们可以获取到新用户的ID，用于后续的账户测试
        User testUser = userService.login(testUsername, testPassword);
        if (testUser != null) {
            Integer testUserId = testUser.getId();
            System.out.println("\n--- 获取到测试用户ID: " + testUserId + "，开始账户功能测试 ---");
            testAccountCreation(testUserId);
            testTransactions(testUserId);
        } else {
            System.out.println("\n!!! 关键测试用户登录失败，无法继续进行账户功能测试 !!!");
        }

        System.out.println("\n============== 业务逻辑测试结束 ==============");
    }

    /**
     * 测试用户注册功能
     */
    private static void testUserRegistration(String username, String password) {
        System.out.println("\n--- 1. 测试用户注册 ---");
        System.out.println("尝试注册新用户: " + username);
        boolean success = userService.register(username, password);
        System.out.println("注册结果: " + (success ? "成功" : "失败"));

        System.out.println("尝试重复注册用户: " + username);
        boolean successAgain = userService.register(username, password);
        System.out.println("重复注册结果: " + (successAgain ? "成功 (测试失败！)" : "失败 (符合预期)"));
    }

    /**
     * 测试用户登录功能
     */
    private static void testUserLogin(String username, String password) {
        System.out.println("\n--- 2. 测试用户登录 ---");
        System.out.println("使用正确凭证登录 (" + username + " / " + password + ")");
        User user = userService.login(username, password);
        System.out.println("登录结果: " + (user != null ? "成功, 用户信息: " + user : "失败"));

        System.out.println("使用错误凭证登录 (wronguser / wrongpass)");
        User wrongUser = userService.login("wronguser", "wrongpass");
        System.out.println("登录结果: " + (wrongUser != null ? "成功 (测试失败！)" : "失败 (符合预期)"));
    }

    /**
     * 测试查询所有用户功能
     */
    private static void testFindAllUsers() {
        System.out.println("\n--- 3. 测试查询所有用户 ---");
        List<User> users = userService.findAllUsers();
        if (users != null && !users.isEmpty()) {
            System.out.println("查询成功，共找到 " + users.size() + " 位用户。");
            // 打印前5个用户以作展示
            users.stream().limit(5).forEach(System.out::println);
        } else {
            System.out.println("查询失败或系统中没有用户。");
        }
    }

    /**
     * 测试开通账户功能
     * @param userId 要开户的用户ID
     */
    private static void testAccountCreation(Integer userId) {
        System.out.println("\n--- 4. 测试开通银行账户 ---");
        System.out.println("为用户ID " + userId + " 首次开通账户...");
        int resultCode = accountService.createAccount(userId);
        System.out.println("首次开户结果码: " + resultCode + " (1表示成功, 符合预期)");

        System.out.println("为用户ID " + userId + " 再次开通账户...");
        int resultCodeAgain = accountService.createAccount(userId);
        System.out.println("再次开户结果码: " + resultCodeAgain + " (-1表示已存在, 符合预期)");
    }

    /**
     * 测试存取款事务功能
     * @param userId 要操作账户的用户ID
     */
    private static void testTransactions(Integer userId) {
        System.out.println("\n--- 5. 测试存取款事务 ---");
        Account account = accountService.getAccountByUserId(userId);
        if (account == null) {
            System.out.println("找不到账户，无法进行测试。");
            return;
        }
        System.out.println("初始余额: " + account.getBalance());

        // 测试存款
        System.out.println("\n尝试存款 1000.0 元...");
        TransactionResult depositResult = accountService.performTransaction(userId, 1000.0, "deposit");
        if (depositResult.isSuccess()) {
            System.out.println("存款成功! 新余额: " + depositResult.getNewBalance());
        } else {
            System.out.println("存款失败: " + depositResult.getMessage());
        }

        // 测试取款（成功）
        System.out.println("\n尝试取款 200.5 元...");
        TransactionResult withdrawResult = accountService.performTransaction(userId, 200.5, "withdraw");
        if (withdrawResult.isSuccess()) {
            System.out.println("取款成功! 新余额: " + withdrawResult.getNewBalance());
        } else {
            System.out.println("取款失败: " + withdrawResult.getMessage());
        }

        // 测试取款（失败，余额不足）
        System.out.println("\n尝试取款 99999.0 元 (余额不足)...");
        TransactionResult overdrawResult = accountService.performTransaction(userId, 99999.0, "withdraw");
        if (!overdrawResult.isSuccess()) {
            System.out.println("取款失败 (符合预期)! 原因: " + overdrawResult.getMessage() + ", 余额未变: " + overdrawResult.getNewBalance());
        } else {
            System.out.println("余额不足取款成功 (测试失败！)");
        }

        // 最终查询
        Account finalAccount = accountService.getAccountByUserId(userId);
        System.out.println("\n所有操作结束后，最终账户余额为: " + finalAccount.getBalance());
    }
}