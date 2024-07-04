package main.java.service;

import main.java.dao.UserDAO;
import main.java.dao.impl.UserDAOimpl;
import main.java.model.Movie;
import main.java.model.User;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface UserService {

    public void updateAccount(User user);
    public void displayUserAccountBalance(int userId) throws SQLException;

    public void addUser(String userName,String userPassword) throws SQLException;

    public List<User> getAllUsers() throws SQLException;
}
