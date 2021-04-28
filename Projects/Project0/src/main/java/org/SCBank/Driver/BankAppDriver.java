package org.SCBank.Driver;

import org.SCBank.DAO.UserDAO;
import org.SCBank.DAO.UserDAOImpl;
import org.SCBank.model.User;
import org.SCBank.utilities.PostgresConnection;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BankAppDriver {

    private static Logger log = Logger.getLogger(BankAppDriver.class);

    public static final String NOT_CUSTOMER = "You are not yet a customer with us.";
    public static final String NO_ACCOUNT = "Account not in our records.";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int numUsers = 10;
        boolean loggedIn = false;
        boolean isCustomer = false;


        try {
            Connection con = PostgresConnection.getConnection();
            log.warn(con.getMetaData().getDriverName());
        } catch (SQLException e) {
            log.warn(e.getMessage());
        }
        //check getUser()
        UserDAO udo = new UserDAOImpl();
        User client = new User();
        String usernameEntered = "";

        while (true) {
            log.info("===================");
            log.info("Welcome to SC Bank");
            log.info("===================");
            log.info("1: Sign up");
            log.info("2: Deposit");
            log.info("3: Withdraw");
            log.info("4: Balance");
            log.info("5: exit");
            log.info("Enter your choice(1-5) : ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        log.info("Thank you for choosing SC Bank");
                        log.info("Enter username");
                        usernameEntered = scanner.nextLine();

                        if (udo.getUser().contains(udo.getUserById(usernameEntered))) {
                            log.warn("username is taken");
                            break;
                        }

                        log.info("Enter password: ");
                        String password = scanner.nextLine();

                        log.info("Enter initial amount");
                        float depositAmount = Float.parseFloat(scanner.nextLine());

                        log.info("Thank you for sign up");
                        client = new User(usernameEntered, password, depositAmount);
                        udo.createUser(client);
                        numUsers++;
                        loggedIn = true;
                        isCustomer = true;
                        break;

                    case 2:
                        if (!loggedIn) {
                           log.info("\nPlease Enter your username: ");
                            usernameEntered = scanner.nextLine();
                        }

                        log.info("Please Enter your password: ");
                        password = scanner.nextLine();

                        if(udo.getUser().contains(udo.getUserById(usernameEntered))&& udo.getUserById(usernameEntered).getPassword().equals(password)) {
                            isCustomer = true;
                            log.info("Please Enter deposit amount: ");
                            depositAmount = Float.parseFloat(scanner.nextLine());
                            udo.getUserById(usernameEntered).deposit(udo.getUserById(usernameEntered),usernameEntered, depositAmount);
                        }
                        if (numUsers == 0 || !udo.getUser().contains(udo.getUserById(usernameEntered)) || !udo.getUserById(usernameEntered).getPassword().equals(password)) {
                            log.warn(NOT_CUSTOMER);
                            isCustomer = false;
                            loggedIn = false;
                            break;
                        }
                        if(!isCustomer) log.warn(NO_ACCOUNT);
                        break;
                    case 3:
                        if (!loggedIn) {
                            log.info("\nPlease Enter your username: ");
                            usernameEntered = scanner.nextLine();
                        }

                        log.info("Please Enter your password: ");
                        password = scanner.nextLine();

                        if(udo.getUser().contains(udo.getUserById(usernameEntered))&& udo.getUserById(usernameEntered).getPassword().equals(password)) {
                            isCustomer = true;
                            log.info("Please Enter the withdraw amount ");
                            depositAmount = Float.parseFloat(scanner.nextLine());
                            udo.getUserById(usernameEntered).withdraw(udo.getUserById(usernameEntered),usernameEntered, depositAmount);
                        }
                        if (numUsers == 0 || !udo.getUser().contains(udo.getUserById(usernameEntered)) || !udo.getUserById(usernameEntered).getPassword().equals(password)) {
                            log.warn(NOT_CUSTOMER);
                            isCustomer = false;
                            loggedIn = false;
                            break;
                        }
                        if(!isCustomer) log.warn(NO_ACCOUNT);
                        break;
                    case 4:
                        if (!loggedIn) {
                            log.info("\nPlease Enter your username: ");
                            usernameEntered = scanner.nextLine();
                        }
                        log.info("Please Enter your password: ");
                        password = scanner.nextLine();

                        if(udo.getUser().contains(udo.getUserById(usernameEntered)) && udo.getUserById(usernameEntered).getPassword().equals(password)) {
                            isCustomer = true;
                            log.info("Your current balance is: $" + udo.getUserById(usernameEntered).getBalance());
                        }
                        if (numUsers == 0 || !udo.getUser().contains(udo.getUserById(usernameEntered)) || !udo.getUserById(usernameEntered).getPassword().equals(password)) {
                            log.warn(NOT_CUSTOMER);
                            isCustomer = false;
                            loggedIn = false;
                            break;
                        }
                        if(!isCustomer) log.warn(NO_ACCOUNT);
                        break;
                    case 5:
                        log.info("You have chosen to exit. Thank you)");
                        log.info(0);
                        break;
                    default:
                        break;
                } // end switch
            } catch (NumberFormatException e) {
                log.warn(e.getMessage());
            }
        }
    }
}