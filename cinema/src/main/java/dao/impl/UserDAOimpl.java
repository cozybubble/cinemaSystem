package main.java.dao.impl;

import main.java.dao.UserDAO;
import main.java.model.User;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOimpl implements UserDAO {

    public void addUser(User user) throws SQLException {
        String sql = "INSERT INTO users (user_name, user_password, account_balance) VALUES (?, ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, user.getUserName());
        stmt.setString(2, user.getUserPassword());
        stmt.setDouble(3, user.getAccountBalance());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            user.setUserId(rs.getInt(1));
        }
    }

    public void updateAccount(User user) throws SQLException{
        String sql = "UPDATE users SET account_balance=? where user_id =?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setDouble(1,user.getAccountBalance());
        stmt.setInt(2,user.getUserId());
        stmt.executeUpdate();
    }

    public User getUserById(int userId) throws SQLException {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, userId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("user_name"));
            user.setUserPassword(rs.getString("user_password"));
            user.setAccountBalance(rs.getDouble("account_balance"));
            return user;
        } else {
            return null;
        }
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM users";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setUserId(rs.getInt("user_id"));
            user.setUserName(rs.getString("user_name"));
            user.setUserPassword(rs.getString("user_password"));
            user.setAccountBalance(rs.getDouble("account_balance"));
            users.add(user);
        }
        return users;
    }

}
