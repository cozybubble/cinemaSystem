package main.java.controller;


import main.java.model.*;
import main.java.service.*;

import main.java.service.impl.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private CinemaService cinemaService;
    private MovieService movieService;
    private ShowingService showingService;
    private SeatService seatService;
    private TicketService ticketService;
    private UserService userService;
    public UserController() {
        movieService = new MovieServiceImpl();
        showingService = new ShowingServiceImpl();
        seatService = new SeatServiceImpl();
        ticketService = new TicketServiceImpl();
        cinemaService = new CinemaServiceImpl();
        userService = new UserServiceImpl();
    }

    public void handleUserMenu(Scanner scanner, User user) throws SQLException {
        System.out.println("尊贵的 "+user.getUserName()+",欢迎您登录！");
        System.out.println();
        while (true) {
            System.out.println("--------------用户菜单------------");
            System.out.println("1. 购买电影票");
            System.out.println("2. 查询当前用户购票信息");
            System.out.println("3. 显示所有影厅");
            System.out.println("4. 显示所有电影");
            System.out.println("5. 显示所有放映场次");
            System.out.println("6. 显示场次剩余座位");
            System.out.println("7. 显示账户余额");
            System.out.println("8. 退票");
            System.out.println("0. 返回主菜单");
            System.out.println("---------------------------------");
            System.out.print("请输入操作序号: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            System.out.println();

            switch (choice) {
                case 1:
                    buyTicket(scanner, user);
                    break;
                case 2:
                    displayUserTickets(user);
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
                    displayRemainedSeatsByShowingId(scanner);
                    break;
                case 7:
                    displayUserAccountBalance(user.getUserId());
                    break;
                case 8:
                    refundTicket(user,scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("无效选择");
                    break;
            }
        }
    }

    private void refundTicket(User user,Scanner scanner){
        System.out.print("输入想要删除的场次ID: ");
        int showingId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("输入想要删除的座位: ");
        String seatNumber = scanner.nextLine();
        try {
            ticketService.refundTicket(user,showingId,seatNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void displayUserAccountBalance(int userId) throws SQLException {
        userService.displayUserAccountBalance(userId);
    }

    private void displayRemainedSeatsByShowingId(Scanner scanner){
        System.out.print("输入场次ID: ");
        int showingId = scanner.nextInt();
        seatService.displayRemainedSeatsByShowingId(showingId);
    }
    private void buyTicket(Scanner scanner, User user) {

        System.out.print("输入电影名称: ");
        String movieTitle = scanner.nextLine();

        ticketService.purchaseTicket(movieTitle, user);
    }

    private void displayUserTickets(User user) {
        ticketService.displayUserTickets(user);
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