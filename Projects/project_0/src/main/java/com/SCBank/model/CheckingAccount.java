package com.SCBank.model;

public class CheckingAccount {

    private int chekingAccountId;
    private int customerId;
    private double checkingAmount;


    public CheckingAccount(int chekingAccountId, int customerId, double checkingAmount) {
        this.chekingAccountId = chekingAccountId;
        this.customerId = customerId;
        this.checkingAmount = checkingAmount;
    }

    public CheckingAccount() {

    }

    public int getChekingAccountId() {
        return chekingAccountId;
    }

    public void setChekingAccountId(int chekingAccountId) {
        this.chekingAccountId = chekingAccountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getCheckingAmount() {
        return checkingAmount;
    }

    public void setCheckingAmount(double checkingAmount) {
        this.checkingAmount = checkingAmount;
    }

    @Override
    public String toString() {
        return "CheckingAccount{" +
                "chekingAccountId=" + chekingAccountId +
                ", customerId=" + customerId +
                ", checkingAmount=" + checkingAmount +
                '}';
    }
}



