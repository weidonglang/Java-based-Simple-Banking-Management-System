package com.tianshi.web.service;

public class TransactionResult {
    private final boolean success;
    private final String message;
    private final Double newBalance;

    public TransactionResult(boolean success, String message, Double newBalance) {
        this.success = success;
        this.message = message;
        this.newBalance = newBalance;
    }

    public boolean isSuccess() { return success; }
    public String getMessage() { return message; }
    public Double getNewBalance() { return newBalance; }
}