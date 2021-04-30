package com.SCBank.DAO;

import com.SCBank.model.Customer;
import com.SCBank.model.Employee;
import com.SCBank.model.Transaction;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO {

    public Employee createEmployee();
    public Employee employeeLogIn();
    public Customer selectCustomerId(int id);
    public void deleteCustomerById(int id);
    public List<Customer> selectAllCustomers() throws SQLException;
    public List<Transaction> showTransactions();

}
