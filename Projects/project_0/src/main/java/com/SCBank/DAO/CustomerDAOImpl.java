package com.SCBank.DAO;

import com.SCBank.Driver.Menu;
import com.SCBank.Utilities.PostgresConnection;
import com.SCBank.model.CheckingAccount;
import com.SCBank.model.Customer;
import com.SCBank.model.SavingsAccount;
import javafx.geometry.Pos;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class CustomerDAOImpl implements CustomerDAO {

    private static org.apache.log4j.Logger log = Logger.getLogger(CustomerDAOImpl.class);

    @Override
    public Customer createCustomer() {

        Customer createCustomer = new Customer();
        Scanner scanner = new Scanner(System.in);

        log.info("Please enter your First Name:");
        String fname = scanner.nextLine();
        log.info("Please enter your Last Name:");
        String lname = scanner.nextLine();
        log.info("Please enter your Username:");
        String username = scanner.nextLine();
        log.info("Please enter your Password:");
        String password = scanner.nextLine();


        createCustomer.setCustomerFname(fname);
        createCustomer.setCustomerLname(lname);
        createCustomer.setUsername(username);
        createCustomer.setPassword(password);

        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "INSERT INTO \"SCBank_schema\".customer (first_name, last_name, user_name, user_password) VALUES(?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, lname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);

            preparedStatement.execute();

        } catch (SQLException e) {
            log.warn(e);
        }
        return createCustomer;
    }


    @Override
    public Customer customerLogin() {
        Customer customerLogIn = new Customer();
        Customer customer = null;
        Scanner scanner = new Scanner(System.in);


        log.info("Please Enter your Username:");
        String username = scanner.nextLine();
        log.info("Please enter your Password:");
        String password = scanner.nextLine();


        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "SELECT c.customer_id, c.first_name, c.last_name FROM \"SCBank_schema\".Customer c  WHERE c.user_name = ? AND c.user_password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(1, password);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setCustomerId(rs.getInt("customer_Id"));
                customer.setCustomerFname(rs.getString("first_Name"));
                customer.setCustomerLname(rs.getString("last_Name"));
            } else {
                log.warn("Log In Failed.  Please Try Again");
                Menu.manageInput();
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerLogIn;
    }

    @Override
    public Customer createCheckingAccount(Customer customer) {
        Customer customerCheckingAccount = new Customer();

        String sql = "SELECT  Customer_id FROM customer WHERE first_name = ?";

        log.info("Please Enter Initial Deposit Amount on Checking Account");
        double amount = 0;
        Scanner sc = new Scanner(System.in);
        amount = sc.nextDouble();
        String sql2 = "INSERT INTO Checking_Account(customer_id, Checking_balance) VALUES (?,?)";

        if (amount >= 0) {
            try (Connection connection = PostgresConnection.getConnection()) {

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, customer.getCustomerFname());
                preparedStatement.execute();
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    customer.setCustomerId(rs.getInt("Customer_id"));
                }
                PreparedStatement preparedStatementSecond = connection.prepareStatement(sql2);
                preparedStatementSecond.setInt(1, customer.getCustomerId());
                preparedStatementSecond.setDouble(2, amount);
                preparedStatementSecond.execute();

            } catch (SQLException e) {
            }
        } else {
            log.warn("invalid input");
            createCheckingAccount(customer);
        }


        return customerCheckingAccount;
    }

    @Override
    public Customer createSavingsAccount(Customer customer) {
        Customer createSavingsAccount = new Customer();
        double amount = 0;

        String sql = "SELECT  Customer_id FROM customer WHERE first_name = ?";


        String sql2 = "INSERT INTO Savings_Account(customer_id, Savings_balance) VALUES (?,?)";
        log.info("Please Enter Initial Deposit Amount on Savings Account");

        Scanner sc = new Scanner(System.in);
        amount = sc.nextDouble();

        if (amount >= 0) {
            try (Connection connection = PostgresConnection.getConnection()) {

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, customer.getCustomerFname());
                preparedStatement.execute();
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    customer.setCustomerId(rs.getInt("Customer_id"));
                }

                PreparedStatement preparedStatementSecond = connection.prepareStatement(sql2);
                preparedStatementSecond.setInt(1, customer.getCustomerId());
                preparedStatementSecond.setDouble(2, amount);
                preparedStatementSecond.execute();

            } catch (SQLException e) {
            }

        } else {
            log.warn("invalid input");
            createSavingsAccount(customer);
        }

        return customer;
    }

    @Override
    public double checkingDeposit(Customer customer) {
        double balance = checkingTotal(customer);
        double amount = 0.0;

        Scanner sc = new Scanner(System.in);
        log.info("Please Enter Your Deposit Amount");
        amount = sc.nextDouble();

        if (balance >= 0 && amount >= 0) {

            balance = balance + amount;
            String sql = "UPDATE \"SCBank_schema\".Checking_account SET Checking_balance = ? WHERE customer_id = ? ";

            try (Connection connection = PostgresConnection.getConnection()) {

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDouble(1, balance);
                preparedStatement.setInt(2, customer.getCustomerId());
                preparedStatement.execute();


                String secondsql = "INSERT INTO \"SCBank_schema\".Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Checking Deposit', ?);";
                PreparedStatement preparedStatementSecond = connection.prepareStatement(secondsql);
                preparedStatementSecond.setInt(1, customer.getCustomerId());
                preparedStatementSecond.setDouble(2, amount);
                preparedStatementSecond.execute();


            } catch (SQLException e) {
            }

        } else {
            log.warn("invalid input Please try again.");
            checkingDeposit(customer);
        }
        return balance;
    }

    @Override
    public double savingsDeposit(Customer customer) {
        double balance = savingsTotal(customer);
        double amount = 0;

        Scanner sc = new Scanner(System.in);
        log.info("Please Enter Your Deposit Amount");
        amount = sc.nextDouble();

        if (balance >= 0 && amount >= 0) {

            balance = balance + amount;
            String sql = "UPDATE \"SCBank_schema\".Savings_account SET Savings_balance = ? WHERE customer_id = ? ";

            try (Connection connection = PostgresConnection.getConnection()) {

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDouble(1, balance);
                preparedStatement.setInt(2, customer.getCustomerId());
                preparedStatement.execute();


                String secondsql = "INSERT INTO \"SCBank_schema\".Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Savings Deposit', ?);";
                PreparedStatement preparedStatementSecond = connection.prepareStatement(secondsql);
                preparedStatementSecond.setInt(1, customer.getCustomerId());
                preparedStatementSecond.setDouble(2, amount);
                preparedStatementSecond.execute();


            } catch (SQLException e) {
            }

        } else {
            log.warn("invalid input Please try again.");
            savingsDeposit(customer);
        }
        return balance;
    }

    @Override
    public double checkingWithdraw(Customer customer) {
        double balance = checkingTotal(customer);
        double amount = 0;

        log.info("Please Enter the Amount you want to Withdraw");
        Scanner sc = new Scanner(System.in);
        amount = sc.nextDouble();
        if(balance - amount >= 0) {
            balance = balance - amount;
            String sql = "UPDATE \"SCBank_schema\".Checking_account SET Checking_balance = ? WHERE customer_id = ? ";

            try (Connection connection = PostgresConnection.getConnection()){

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDouble(1, balance);
                preparedStatement.setInt(2, customer.getCustomerId());
                preparedStatement.execute();

                String sqlT = "INSERT INTO \"SCBank_schema\".Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Checking Withdraw', ?);";
                PreparedStatement preparedStatementSecond = connection.prepareStatement(sqlT);
                preparedStatementSecond.setInt(1, customer.getCustomerId());
                preparedStatementSecond.setDouble(2, amount);
                preparedStatementSecond.execute();

                return balance;

            }catch(SQLException e) {
            }

        }else {
            log.warn("insufficient funds Available");
            Menu.customerMenu(customer);
        }

        return balance;
    }

    @Override
    public double savingsWithdraw(Customer customer) {
        double balance = savingsTotal(customer);
        double amount = 0;

        log.info("Please Enter the Amount you want to Withdraw");
        Scanner sc = new Scanner(System.in);
        amount = sc.nextDouble();
        if(balance - amount >= 0) {
            balance = balance - amount;
            String sql = "UPDATE \"SCBank_schema\".Savings_account SET Savings_account = ? WHERE customer_id = ? ";

            try (Connection connection = PostgresConnection.getConnection()){

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setDouble(1, balance);
                preparedStatement.setInt(2, customer.getCustomerId());
                preparedStatement.execute();

                String sqlT = "INSERT INTO \"SCBank_schema\".Transactions(customer_id, Transaction_type, Transaction_Amount) Values(?,'Savings Withdraw', ?);";
                PreparedStatement preparedStatementSecond = connection.prepareStatement(sqlT);
                preparedStatementSecond.setInt(1, customer.getCustomerId());
                preparedStatementSecond.setDouble(2, amount);
                preparedStatementSecond.execute();

                return balance;

            }catch(SQLException e) {
            }

        }else {
            log.warn("insufficient funds Available");
            Menu.customerMenu(customer);
        }

        return balance;
    }

    @Override
    public double checkingTotal(Customer customer) {
        String sql = "SELECT * FROM \"SCBank_schema\".Checking_Account WHERE customer_id = ? ";
        double cBalance = 0;
        CheckingAccount cAccount = null;
        try (Connection connection = PostgresConnection.getConnection()){

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer.getCustomerId());
            preparedStatement.execute();

            ResultSet result = preparedStatement.executeQuery();
            while(result.next()) {
                cAccount = new CheckingAccount();
                customer.setCheckingAccountId(result.getInt("Checking_Id"));
                cAccount.setCustomerId(result.getInt("Customer_Id"));
                cAccount.setChekingAccountId(result.getInt("Checking_Id"));
                cAccount.setCheckingAmount(result.getDouble("Checking_Balance"));
                cBalance = cAccount.getCheckingAmount();
            }

        }catch(SQLException e) {
        }
        return cBalance;
    }



    @Override
    public double savingsTotal(Customer customer) {
        String sql = "SELECT * FROM \"SCBank_schema\".Savings_Account WHERE customer_id = ? ";
        double sBalance = 0;
        SavingsAccount sAccount = null;
        try (Connection connection = PostgresConnection.getConnection()) {

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, customer.getCustomerId());
            preparedStatement.execute();

            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                sAccount = new SavingsAccount();
                customer.setSavingsAccountId(result.getInt("Savings_Id"));
                sAccount.setCustomerId(customer.getCustomerId());
                sAccount.setSavingsAccountId(result.getInt("Savings_Id"));
                sAccount.setSavingsAmount(result.getDouble("Savings_Balance"));
                sBalance = sAccount.getSavingsAmount();
            }


        } catch (SQLException e) {
        }
        return sBalance;
    }



    @Override
    public double Total(Customer customer) {
        double totalBalance = 0;
        double cBalance = checkingTotal(customer);
        double sBalance = savingsTotal(customer);

        totalBalance = cBalance + sBalance;

        return totalBalance;
    }
}
