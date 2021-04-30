package com.SCBank.model;

public class Transaction {
    private int transactionNumber;
    private int customerId;
    private String transactionType;
    private double TransactionAmount;

    public Transaction(int transactionNumber, int customerId, String transactionType, double transactionAmount) {
        this.transactionNumber = transactionNumber;
        this.customerId = customerId;
        this.transactionType = transactionType;
        TransactionAmount = transactionAmount;
    }

    public int getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(int transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public double getTransactionAmount() {
        return TransactionAmount;
    }

    public void setTransactionAmount(double transactionAmount) {
        TransactionAmount = transactionAmount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionNumber=" + transactionNumber +
                ", customerId=" + customerId +
                ", transactionType='" + transactionType + '\'' +
                ", TransactionAmount=" + TransactionAmount +
                '}';
    }
}
