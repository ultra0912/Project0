package org.SCBank.model;

import org.SCBank.Driver.BankAppDriver;
import org.apache.log4j.Logger;

public class Account {

    private static Logger log = Logger.getLogger(Account.class);

    private float balance;

    public Account(float balance) {
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }


    public void deposit(float amount) {
        if (amount > 0) {
            balance += amount;
            log.info("You have successfully deposited "+amount+". "
                    + "Your balance is now: " + balance);
        }
        else {
            log.warn("This amount is invalid. Please enter positive number.");
        }
    }

    public void withdraw(float amount) {
        double balanceChecker = balance;

        if (amount > 0) {
            balanceChecker -= amount;
            if (balanceChecker >= 0) {
                balance -= amount;
                log.info("You have successfully withdrawn "+amount+". "
                        + "Your balance is now: " + balance);
            }
            else {
                log.warn("Withdrawal of "+amount+" unsuccessful due to "
                        + "Insufficient funds.");
            }

        }
        else {
            log.warn("This amount is invalid. Please enter positive amount.");
        }
    }


    @Override
    public String toString() {
        return "Account{" +
                "balance=" + balance +
                '}';
    }
}
