package com.SCBank.DAO;

import com.SCBank.Utilities.PostgresConnection;
import com.SCBank.model.Customer;
import com.SCBank.model.Employee;
import com.SCBank.model.Transaction;
import javafx.geometry.Pos;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeDAOImpl implements EmployeeDAO {

    private static org.apache.log4j.Logger log = Logger.getLogger(EmployeeDAOImpl.class);

    @Override
    public Employee createEmployee() {
        Employee createEmployee = new Employee();
        Scanner scanner = new Scanner(System.in);

        log.info("Please enter your First Name:");
        String fname = scanner.nextLine();
        log.info("Please enter your Last Name:");
        String lname = scanner.nextLine();
        log.info("Please enter your Username:");
        String username = scanner.nextLine();
        log.info("Please enter your Password:");
        String password = scanner.nextLine();


        createEmployee.setEmployeeFname(fname);
        createEmployee.setEmployeeLname(lname);
        createEmployee.setUsername(username);
        createEmployee.setPassword(password);

        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "INSERT INTO \"SCBank_schema\".employee (first_name, last_name, user_name, user_password) VALUES(?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fname);
            preparedStatement.setString(2, lname);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);

            preparedStatement.execute();

        } catch (SQLException e) {
            log.warn(e);
        }
        return createEmployee;
    }


    @Override
    public Employee employeeLogIn() {
        Employee employeeLogIn = new Employee();
        Scanner scanner = new Scanner(System.in);

        log.info("Please Enter your Username:");
        String username = scanner.nextLine();
        log.info("Please enter your Password:");
        String password = scanner.nextLine();

        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "SELECT * FROM \"SCBank_schema\".employee WHERE User_Name = ? AND User_Password = ?";
            ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(1, password);

            ResultSet rs = preparedStatement.executeQuery();

        } catch (SQLException e) {
            log.warn(e);
        }
        return employeeLogIn;
    }


    /**
     * Need to work on this.
     * @param id
     */
    @Override
    public Customer selectCustomerId(int id) {
        Customer customer = null;
//
//        try (Connection connection = PostgresConnection.getConnection()) {
//
//            String sql = "SELECT * FROM customer AS c FULL JOIN checking_account AS ca ON ca.customer_id = c.customer_id " +
//                    "FULL JOIN savings_account sa ON sa.customer_id = c.customer_id WHERE c.customer_id = ?";
//
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                customer = new Customer()
//
//            }
//
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        return customer;
    }


        @Override
    public void deleteCustomerById(int id) {
        try(Connection connection = PostgresConnection.getConnection()){

            String sql = "delete FROM \"SCBank_schema\".savings_account WHERE customer_id = ?;"
                    + "delete FROM \"SCBank_schema\".checking_account WHERE customer_id = ?;"
                    + "DELETE FROM \"SCBank_schema\".Transactions WHERE customer_id = ?;"
                    + "delete FROM \"SCBank_schema\".customer WHERE customer_Id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            preparedStatement.setInt(3, id);
            preparedStatement.setInt(4, id);
            preparedStatement.execute();


        } catch (SQLException e) {
        }

    }

    /**
     * Need to work on this.
     * @return
     */

    @Override
    public List<Customer> selectAllCustomers() {
        List<Customer> customerList = new ArrayList<>();
//        try (Connection connection = PostgresConnection.getConnection()) {
//            String sql = "";
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//
//
//        }catch (SQLException e){
//        }
        return customerList;
    }



    @Override
    public List<Transaction> showTransactions() {
        List<Transaction> transList = new ArrayList<>();
        try (Connection connection = PostgresConnection.getConnection()) {
            String sql = "SELECT * FROM \"SCBank_schema\".Transactions";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                transList.add(new Transaction(
                        rs.getInt("Transaction_id"),
                        rs.getInt("Customer_id"),
                        rs.getString("Transaction_type"),
                        rs.getDouble("Transaction_amount")
                ));
            }

        } catch (SQLException e) {
        }
        return transList;
    }
}
