package main.java.controller;


import main.java.model.*;
import main.java.service.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;
import main.java.controller.UserController;
import main.java.service.impl.*;

public class CinemaController {
    private CinemaService cinemaService;
    private MovieService movieService;
    private ShowingService showingService;
    private LoginService loginService;
    private UserController userController;
    private AdminController adminController;
    private UserService userService;
    public CinemaController() {
        cinemaService = new CinemaServiceImpl();
        movieService = new MovieServiceImpl();
        showingService = new ShowingServiceImpl();
        loginService = new LoginServiceImpl();
        userController = new UserController();
        adminController = new AdminController();
        userService = new UserServiceImpl();
    }

    public void start() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("--------------主菜单------------");
            System.out.println("1. 管理员登录");
            System.out.println("2. 用户登录");
            System.out.println("3. 显示所有影厅");
            System.out.println("4. 显示所有电影");
            System.out.println("5. 显示所有放映");
            System.out.println("6. 注册新用户");
            System.out.println("0. 退出");
            System.out.println("------------------------------");
            System.out.print("请输入操作序号: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            System.out.println();
            switch (choice) {
                case 1:
                    handleAdminLogin(scanner);
                    break;
                case 2:
                    handleUserLogin(scanner);
                    break;
                case 3:
                    displayAllCinemas();
                    break;
                case 4:
                    displayAllMovies();
                    break;
                case 5:
                    displayAllShowings();
                    break;
                case 6:
                    userRegister(scanner);
                    break;
                case 0:
                    System.out.println("退出系统");
                    return;
                default:
                    System.out.println("无效选择");
                    break;
            }
        }
    }

    public void userRegister(Scanner scanner) throws SQLException {
        System.out.print("请输入名称: ");
        String userName = scanner.nextLine();
        System.out.print("请输入密码: ");
        String userPassword = scanner.nextLine();
        userService.addUser(userName,userPassword);
    }
    private void handleAdminLogin(Scanner scanner) {
        System.out.print("请输入管理员用户名: ");
        String adminName = scanner.nextLine();
        System.out.print("请输入管理员密码: ");
        String adminPassword = scanner.nextLine();

        try {
            Admin admin = loginService.adminLogin(adminName, adminPassword);
            if (admin != null) {
                System.out.println("管理员登录成功");
                System.out.println();
                // 处理管理员操作
                adminController.handleAdminMenu(scanner,admin);
            } else {
                System.out.println("登录失败，用户名或密码错误");
            }
        } catch (SQLException e) {
            System.out.println("管理员登录时出现错误: " + e.getMessage());
        }
    }

    private void handleUserLogin(Scanner scanner) {
        System.out.print("请输入用户名: ");
        String userName = scanner.nextLine();
        System.out.print("请输入密码: ");
        String userPassword = scanner.nextLine();

        try {
            User user = loginService.userLogin(userName, userPassword);
            if (user != null) {
                System.out.println("用户登录成功");
                System.out.println();
                // 处理用户操作
                userController.handleUserMenu(scanner, user);
            } else {
                System.out.println("登录失败，用户名或密码错误");
                System.out.println();

            }
        } catch (SQLException e) {
            System.out.println("用户登录时出现错误: " + e.getMessage());
        }
    }

    private void displayAllCinemas() {
        cinemaService.displayAllCinemas();
    }

    private void displayAllMovies() {
        movieService.displayAllMovies();
    }

    private void displayAllShowings() {
        showingService.displayAllShowings();
    }

}
