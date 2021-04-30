package com.SCBank.DAO;

import com.SCBank.model.Customer;

public interface CustomerDAO {

    public Customer createCustomer();
    public Customer customerLogin();
    public Customer createCheckingAccount(Customer customer);
    public Customer createSavingsAccount(Customer customer);
    public double checkingDeposit(Customer customer);
    public double savingsDeposit(Customer customer);
    public double checkingWithdraw(Customer customer);
    public double savingsWithdraw(Customer customer);
    public double checkingTotal(Customer customer);
    public double savingsTotal(Customer customer);
    public double Total(Customer customer);



}
