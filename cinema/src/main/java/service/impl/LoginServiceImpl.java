package main.java.service.impl;

import main.java.dao.AdminDAO;
import main.java.dao.UserDAO;
import main.java.dao.impl.AdminDAOimpl;
import main.java.dao.impl.UserDAOimpl;
import main.java.model.Admin;
import main.java.model.User;
import main.java.service.LoginService;
import java.sql.SQLException;

// LoginServiceImpl类实现了LoginService接口，提供了登录服务的具体实现
public class LoginServiceImpl implements LoginService {
    // 声明了管理员和用户的DAO对象
    private AdminDAO adminDAO;
    private UserDAO userDAO;

    // 构造方法，初始化DAO对象
    public LoginServiceImpl() {
        adminDAO = new AdminDAOimpl(); // 初始化管理员DAO实现
        userDAO = new UserDAOimpl();   // 初始化用户DAO实现
    }

    // 管理员登录方法，通过用户名和密码进行身份验证
    public Admin adminLogin(String adminName, String adminPassword) throws SQLException {
        // 遍历所有管理员并匹配用户名和密码
        for (Admin admin : adminDAO.getAllAdmins()) {
            if (admin.getAdminName().equals(adminName) && admin.getAdminPassword().equals(adminPassword)) {
                return admin; // 匹配成功，返回管理员对象
            }
        }
        return null; // 匹配失败，返回null
    }

    // 用户登录方法，通过用户名和密码进行身份验证
    public User userLogin(String userName, String userPassword) throws SQLException {
        // 遍历所有用户并匹配用户名和密码
        for (User user : userDAO.getAllUsers()) {
            if (user.getUserName().equals(userName) && user.getUserPassword().equals(userPassword)) {
                return user; // 匹配成功，返回用户对象
            }
        }
        return null; // 匹配失败，返回null
    }
}