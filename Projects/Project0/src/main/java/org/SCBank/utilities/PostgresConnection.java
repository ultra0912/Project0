package org.SCBank.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresConnection {


    private PostgresConnection() { }
    private static Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://firstdb.c9umhrq9x95g.us-east-2.rds.amazonaws.com:5432/postgres";
            String username = "postgres";
            String password = "qlalfqjsgh";
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
    }
    public static Connection getConnection(){
        return connection;
    }
}
