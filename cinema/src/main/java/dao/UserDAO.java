package main.java.dao;

import main.java.model.User;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface UserDAO {

    public void addUser(User user) throws SQLException;

    public void updateAccount(User user) throws SQLException;


    public User getUserById(int userId) throws SQLException;

    public List<User> getAllUsers() throws SQLException;

}
