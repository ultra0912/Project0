package com.SCBank.model;

public class Customer {

    private int customerId;
    private String CustomerFname;
    private String CustomerLname;
    private String username;
    private String password;
    private int savingsAccountId;
    private int checkingAccountId;
    private double checkingAmount;
    private double savingsAmount;


    public Customer(int customerId) {
        this.customerId = customerId;
        CustomerFname = customerFname;
        CustomerLname = customerLname;
        this.username = username;
        this.password = password;
        this.savingsAccountId = savingsAccountId;
        this.checkingAccountId = checkingAccountId;
    }

    public Customer() {

    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerFname() {
        return CustomerFname;
    }

    public void setCustomerFname(String customerFname) {
        CustomerFname = customerFname;
    }

    public String getCustomerLname() {
        return CustomerLname;
    }

    public void setCustomerLname(String customerLname) {
        CustomerLname = customerLname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSavingsAccountId() {
        return savingsAccountId;
    }

    public void setSavingsAccountId(int savingsAccountId) {
        this.savingsAccountId = savingsAccountId;
    }

    public int getCheckingAccountId() {
        return checkingAccountId;
    }

    public void setCheckingAccountId(int checkingAccountId) {
        this.checkingAccountId = checkingAccountId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", CustomerFname='" + CustomerFname + '\'' +
                ", CustomerLname='" + CustomerLname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", savingsAccountId=" + savingsAccountId +
                ", checkingAccountId=" + checkingAccountId +
                ", checkingAmount=" + checkingAmount +
                ", savingsAmount=" + savingsAmount +
                '}';
    }
}
