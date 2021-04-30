package com.SCBank.model;

public class Employee {

    private int employeeId;
    private String EmployeeFname;
    private String EmployeeLname;
    private String username;
    private String password;

    public Employee(int employeeId, String employeeFname, String employeeLname, String username, String password) {
        this.employeeId = employeeId;
        EmployeeFname = employeeFname;
        EmployeeLname = employeeLname;
        this.username = username;
        this.password = password;
    }

    public Employee() {

    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFname() {
        return EmployeeFname;
    }

    public void setEmployeeFname(String employeeFname) {
        EmployeeFname = employeeFname;
    }

    public String getEmployeeLname() {
        return EmployeeLname;
    }

    public void setEmployeeLname(String employeeLname) {
        EmployeeLname = employeeLname;
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

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", EmployeeFname='" + EmployeeFname + '\'' +
                ", EmployeeLname='" + EmployeeLname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
