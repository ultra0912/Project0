package com.SCBank.Driver;

import com.SCBank.DAO.CustomerDAOImpl;
import com.SCBank.DAO.EmployeeDAOImpl;
import com.SCBank.model.Customer;
import com.SCBank.model.Employee;
import com.SCBank.model.Transaction;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private static Logger log = Logger.getLogger(BankDriver.class);


    static CustomerDAOImpl cdi = new CustomerDAOImpl();
    static EmployeeDAOImpl edi = new EmployeeDAOImpl();

    public Menu(CustomerDAOImpl cdi, EmployeeDAOImpl edi) {
        this.cdi = cdi;
        this.edi = edi;
        Scanner scanner = new Scanner(System.in);
    }


    public static void manageInput() {
        Scanner scanner = new Scanner(System.in);
        int ch = 0;

        do {
            log.info("=======================");
            log.info("Welcome to SC Bank");
            log.info("=======================");
            log.info("1. Create Customer Account");
            log.info("2. Create Employee Account");
            log.info("3. Create Bank Account");
            log.info("4. Customer Log In");
            log.info("5. Employee Log In");
            log.info("6. EXIT");
            try {
                ch = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
            }
            switch (ch) {

                case 1:
                    Customer createCustomer = cdi.createCustomer();
                    break;

                case 2:
                    Employee createEmployee = edi.createEmployee();
                    break;

                case 3:
                    Customer customerAccount = cdi.createCustomer();
                    Menu.accountMenu(customerAccount);


                case 4:
                    Customer customer = cdi.customerLogin();
                    log.info("Welcome " + customer.getCustomerFname() + " " + customer.getCustomerLname());
                    Menu.customerMenu(customer);
                    break;

                case 5:
                    Employee employee = edi.employeeLogIn();
                    log.info("Welcome " + employee.getEmployeeFname() + " " + employee.getEmployeeLname());
                    Menu.employeeMenu(employee);

                    break;
                case 6:
                    log.info("Thank you, See you Soon");
                    System.exit(0);
                    break;
                default:
                    log.info("Please choose between 1 - 5");

            }
        } while (ch != 6);
    }

    public static void customerMenu(Customer customer) {
        Scanner sc = new Scanner(System.in);
        int ch = 0;
        do {
            log.info("+++++++++++++++++++++++++++++");
            log.info("1. Deposit to Checking Account");
            log.info("2. Deposit to Savings Account");
            log.info("3. Withdraw from Checking Account");
            log.info("4. Withdraw from  Savings Account");
            log.info("5. Checking Account Balance");
            log.info("6. Savings Account Balance");
            log.info("7. Total Balance");
            log.info("8. EXIT");

            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
            }


            switch (ch) {
                case 1:
                    double checkingDepositBalance = cdi.checkingDeposit(customer);
                    log.info("Your Checking Account Balance: $" + checkingDepositBalance);
                    customerMenu(customer);

                case 2:
                    double savingsDepositBalance = cdi.savingsDeposit(customer);
                    log.info("Your Savings Account Balance: $" + savingsDepositBalance);
                    customerMenu(customer);
                    break;

                case 3:
                    double checkingWithdrawBalance = cdi.checkingWithdraw(customer);
                    log.info("Your Checking Account Balance: $" + checkingWithdrawBalance);
                    customerMenu(customer);
                    break;

                case 4:
                    double savingsWithdrawBalance = cdi.savingsWithdraw(customer);
                    log.info("Your Savings Account Balance: $" + savingsWithdrawBalance);
                    customerMenu(customer);
                    break;

                case 5:
                    double checkingBalance = cdi.checkingTotal(customer);
                    log.info("Your Checking Account Balance: $" + checkingBalance);
                    customerMenu(customer);
                    break;


                case 6:
                    double savingsBalance = cdi.savingsTotal(customer);
                    log.info("Your Savings Account Balance: $" + savingsBalance);
                    customerMenu(customer);
                    break;

                case 7:
                    double totalBalance = cdi.Total(customer);
                    log.info("Your Savings Account Balance: $" + totalBalance);
                    customerMenu(customer);
                    break;

                case 8:
                    log.info("Thank You");
                    Menu.manageInput();
                    break;

                default:
                    log.warn("Please Choose between 1-8");
                    customerMenu(customer);
                    break;
            }

        } while (ch != 8);

    }


    public static void accountMenu(Customer customer) {

        Scanner sc = new Scanner(System.in);
        int ch = 0;

        do {
            log.info("+++++++++++++++++++++++++++++");
            log.info("Please Choose Accounts You Want to Open");
            log.info("1. Checking");
            log.info("2. Savings");
            log.info("3. EXIT");

            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
            }

            switch (ch) {

                case 1:
                    cdi.createCheckingAccount(customer);
                    log.info("Thank You for Choosing SC Bank.  You Opened Checking Account");
                    Menu.manageInput();
                    break;


                case 2:
                    cdi.createSavingsAccount(customer);
                    log.info("Thank You for Choosing SC Bank.  You Opened Savings Account");
                    Menu.manageInput();
                    break;

                case 3:
                    log.info("Thank You");
                    Menu.manageInput();

                default:
                    log.warn("Please Choose between 1-3");
            }
        } while (ch != 3);
    }


    public static void employeeMenu(Employee employee) {
        Scanner sc = new Scanner(System.in);
        int ch = 0;
        do {
            log.info("+++++++++++++++++++++++++++++");
            log.info("1. Find Customer by Customer ID");
            log.info("2. View All Customers' Accounts");
            log.info("3. Delete Customer's Account by ID");
            log.info("4. View All Transactions");
            log.info("5. EXIT");

            try {
                ch = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
            }

            switch (ch) {
                case 1:
                    log.info("Please Enter Customer ID");
                    int customerId = sc.nextInt();
                    Customer cId = edi.selectCustomerId(customerId);
                    log.info(customerId);
                    employeeMenu(employee);
                    break;

                case 2:
                    List<Customer> customerList = new ArrayList();
                    customerList = edi.selectAllCustomers();
                    for (Customer element : customerList) {
                        log.info(element);
                    }
                    employeeMenu(employee);
                    break;

                case 3:
                    log.info("Please Enter Customer ID to delete Account");
                    int deleteId = sc.nextInt();
                    edi.deleteCustomerById(deleteId);
                    employeeMenu(employee);
                    break;

                case 4:
                    List<Transaction> tList = new ArrayList();
                    tList = edi.showTransactions();
                    for (Transaction element : tList) {
                        log.info(element);
                    }
                    employeeMenu(employee);
                    break;

                case 5:
                    log.info("Thank You");
                    Menu.manageInput();
                    break;

                default:
                    log.warn("Please Choose between 1-5");
            }
        } while (ch != 5);
    }
}



