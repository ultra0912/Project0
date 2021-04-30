package com.SCBank.model;

public class SavingsAccount {

    private int savingsAccountId;
    private int customerId;
    private double savingsAmount;

    public SavingsAccount(int savingsAccountId, int customerId, double savingsAmount) {
        this.savingsAccountId = savingsAccountId;
        this.customerId = customerId;
        this.savingsAmount = savingsAmount;
    }

    public SavingsAccount() {

    }

    public int getSavingsAccountId() {
        return savingsAccountId;
    }

    public void setSavingsAccountId(int savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getSavingsAmount() {
        return savingsAmount;
    }

    public void setSavingsAmount(double savingsAmount) {
        this.savingsAmount = savingsAmount;
    }


    @Override
    public String toString() {
        return "SavingsAccount{" +
                "savingsAccountId=" + savingsAccountId +
                ", customerId=" + customerId +
                ", savingsAmount=" + savingsAmount +
                '}';
    }
}
