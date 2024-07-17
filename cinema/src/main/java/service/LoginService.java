package main.java.service;




import main.java.model.Admin;
import main.java.model.User;

import java.sql.SQLException;

public interface LoginService {


    public Admin adminLogin(String adminName, String adminPassword) throws SQLException ;

    public User userLogin(String userName, String userPassword) throws SQLException ;
}
