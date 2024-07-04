package main.java.service.impl;



import main.java.dao.AdminDAO;
import main.java.dao.UserDAO;
import main.java.dao.impl.AdminDAOimpl;
import main.java.dao.impl.UserDAOimpl;
import main.java.model.Admin;
import main.java.model.User;
import main.java.service.LoginService;

import java.sql.SQLException;

public class LoginServiceImpl implements LoginService {
    private AdminDAO adminDAO;
    private UserDAO userDAO;

    public LoginServiceImpl() {
        adminDAO = new AdminDAOimpl();
        userDAO = new UserDAOimpl();
    }

    public Admin adminLogin(String adminName, String adminPassword) throws SQLException {
        for (Admin admin : adminDAO.getAllAdmins()) {
            if (admin.getAdminName().equals(adminName) && admin.getAdminPassword().equals(adminPassword)) {
                return admin;
            }
        }
        return null;
    }

    public User userLogin(String userName, String userPassword) throws SQLException {
        for (User user : userDAO.getAllUsers()) {
            if (user.getUserName().equals(userName) && user.getUserPassword().equals(userPassword)) {
                return user;
            }
        }
        return null;
    }
}
