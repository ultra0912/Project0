package org.SCBank.model;

import org.SCBank.DAO.UserDAOImpl;
import org.apache.log4j.Logger;

public class User {

    private static Logger log = Logger.getLogger(User.class);

    private String userId;
    private String password;
    private float balance;

    public User(String userId, String password, float balance) {
        this.userId = userId;
        this.password = password;
        this.balance = balance;
    }

    public User() {

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public void deposit(User user, String id, float amount){
        if (amount > 0) {
            balance += amount;
            user.setBalance(balance);
            UserDAOImpl userDao = new UserDAOImpl();
            userDao.makeDeposit(id, balance);
            log.info("You have successfully deposited $"+amount+". "
                    + "Your balance is now: $" + balance);
        }
        else {
            log.warn("This amount is invalid. Please enter positive number.");
        }
    }


    public void withdraw(User user, String id, float amount) {
        double balanceChecker = balance;

        if (amount > 0) {
            balanceChecker -= amount;
            if (balanceChecker >= 0) {
                balance -= amount;
                user.setBalance(balance);
                UserDAOImpl userDao = new UserDAOImpl();
                userDao.makeWithdrawal(id, -balance);
                log.info("You have successfully withdrawn $"+amount+". "
                        + "Your balance is now: " + balance);
            }
            else {
                log.warn("Withdrawal of $"+amount+" unsuccessful due to "
                        + "Insufficient funds.");
            }
        }
        else {
            log.warn("This amount is invalid. Please enter positive amount.");
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
