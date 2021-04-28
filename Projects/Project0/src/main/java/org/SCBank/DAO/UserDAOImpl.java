package org.SCBank.DAO;

import org.SCBank.model.User;
import org.SCBank.utilities.PostgresConnection;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private static Logger log = Logger.getLogger(UserDAOImpl.class);

    @Override
    public List<User> getUser() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT * FROM BANK_USERS";

        try (Connection con = PostgresConnection.getConnection();
             Statement s = con.createStatement();
             ResultSet rs = s.executeQuery(sql);) {

            while (rs.next()) {
                User u = new User();
                String userId = rs.getString("username");
                u.setUserId(userId);

                String password = rs.getString("password");
                u.setPassword(password);

                float balance = rs.getFloat("balance");
                u.setBalance(balance);

                userList.add(u);
            }

        } catch (SQLException e) {
            log.warn((e.getMessage()));
        }

        return userList;
    }


    @Override
    public User getUserById(String id) {
        User u = null;
        String sql = "SELECT * FROM BANK_USERS WHERE username = ?";

        ResultSet rs = null;

        try (Connection con = PostgresConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("username");
                String password = rs.getString("password");
                float balance = rs.getFloat("balance");

                u = new User(userId, password, balance);
            }
        } catch (SQLException e) {
            log.warn((e.getMessage()));
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.warn((e.getMessage()));
                }
            }
        }

        return u;
    }


    @Override
    public User getUserById(String id, Connection con) {
        User u = null;
        String sql = "SELECT * FROM BANK_USERS WHERE username = ?";

        ResultSet rs = null;

        try (PreparedStatement ps = con.prepareStatement(sql);) {

            ps.setString(1, id);
            rs = ps.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("username");
                String password = rs.getString("password");
                float balance = rs.getFloat("balance");

                u = new User(userId, password, balance);
            }
        } catch (SQLException e) {
            log.warn((e.getMessage()));
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    log.warn((e.getMessage()));
                }
            }
        }

        return u;
    }

    @Override
    public int createUser(User user) {
        int usersCreated = 0;
        String sql = "INSERT INTO Bank_user (username, password, balance) VALUES (?,?,?)";

        try (Connection con = PostgresConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassword());
            ps.setFloat(3, user.getBalance());
            usersCreated = ps.executeUpdate();

        } catch (SQLException e) {
            log.warn((e.getMessage()));
        }
        return usersCreated;
    }

    @Override
    public int updateUser(User user) {
        int usersUpdated = 0;

        String sql = "UPDATE BANK_USERS "
                + "SET password = ?,"
                + "SET balance = ? "
                + "WHERE username = ?";

        try (Connection con = PostgresConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            con.setAutoCommit(false);
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getPassword());
            ps.setFloat(3, user.getBalance());
            usersUpdated = ps.executeUpdate();
            con.commit();

        } catch (SQLException e) {
            log.warn((e.getMessage()));
        }

        return usersUpdated;
    }

    @Override
    public int deleteUserById(String id) {
        int rowsDeleted = 0;

        String sql = "DELETE FROM BANK_USERS WHERE username = ?";

        try (Connection con = PostgresConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, id);
            rowsDeleted = ps.executeUpdate();

        } catch (SQLException e) {
            log.warn((e.getMessage()));
        }
        return rowsDeleted;
    }

    @Override
    public void makeDeposit(String userName, float amount) {
        String sql = "{call DEPOSIT_AMOUNT(?,?)}";

        try (Connection con = PostgresConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, userName);
            cs.setFloat(2, amount);
            cs.execute();
            log.info("deposit added");
        } catch (SQLException e) {
            log.warn((e.getMessage()));
        }
    }

    @Override
    public void makeWithdrawal(String userName, float amount) {
        String sql = "{call WITHDRAW_AMOUNT(?,?)}";

        try (Connection con = PostgresConnection.getConnection();
             CallableStatement cs = con.prepareCall(sql)) {

            cs.setString(1, userName);
            cs.setFloat(2, amount);
            cs.execute();
            log.info("amount withdrawn");
        } catch (SQLException e) {
            log.warn((e.getMessage()));
        }
    }
}
