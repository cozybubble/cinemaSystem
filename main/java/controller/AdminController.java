package main.java.controller;



import main.java.model.*;
import main.java.service.*;
import main.java.service.impl.*;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class AdminController {
    private MovieService movieService;
    private ShowingService showingService;
    private SeatService seatService;
    private CinemaService cinemaService;
    public AdminController() {
        movieService = (MovieService)new MovieServiceImpl();
        showingService =(ShowingService) new ShowingServiceImpl();
        seatService = new SeatServiceImpl();
        cinemaService = new CinemaServiceImpl();
    }

    public void handleAdminMenu(Scanner scanner, Admin admin) throws SQLException {
        while (true) {
            System.out.println("--------------管理员菜单------------");
            System.out.println("1. 添加影厅");
            System.out.println("2. 显示所有影厅");
            System.out.println("3. 添加电影");
            System.out.println("4. 显示所有电影");
            System.out.println("5. 添加座位");
            System.out.println("6. 显示场次座位");
            System.out.println("7. 添加放映场次");
            System.out.println("8. 显示所有放映场次");
            System.out.println("9. 修改电影状态");
            System.out.println("10. 删除场次");
            System.out.println("11. 下架电影");
            System.out.println("0. 返回主菜单");

            System.out.println("----------------------------------");
            System.out.print("请输入操作序号: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            System.out.println();
            switch (choice) {
                case 1:
                    addCinema(scanner);
                    break;
                case 2:
                    displayAllCinemas();
                    break;
                case 3:
                    addMovie(scanner);
                    break;
                case 4:
                    displayAllMovies();
                    break;
                case 5:
                    addSeat(scanner);
                    break;
                case 6:
                    displaySeatsByShowingId(scanner);
                    break;
                case 7:
                    addShowing(scanner);
                    break;
                case 8:
                    displayAllShowings();
                    break;
                case 9:
                    alterMovieStatus(scanner);
                    break;
                case 10:
                    removeShowing(scanner);
                    break;
                case 11:
                    removeMovieById(scanner);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("无效选择");
                    break;
            }
        }
    }

    private void removeShowing(Scanner scanner) throws SQLException {
        System.out.print("输入要删除的场次id: ");
        int removeShowingId = scanner.nextInt();
        showingService.removeShowing(removeShowingId);
    }

    private void removeMovieById(Scanner scanner) throws SQLException {
        System.out.print("输入要删除的电影id: ");
        int movieId = scanner.nextInt();
        movieService.removeMovieById(movieId);
    }
    private void alterMovieStatus(Scanner scanner){
        System.out.print("输入电影id: ");
        int movieId = scanner.nextInt();
        System.out.print("输入要改变的状态（上映中/即将下架）: ");
        scanner.nextLine(); // consume newline
        String alterStatus = scanner.nextLine();
        movieService.alterMovieStatus(movieId,alterStatus);
    }
    private void displaySeatsByShowingId(Scanner scanner){
        System.out.print("输入场次ID: ");
        int showingIdToDisplay = scanner.nextInt();
        seatService.displaySeatsByShowingId(showingIdToDisplay);
    }
    private void addCinema(Scanner scanner) {
        System.out.print("输入影厅名称: ");
        String name = scanner.nextLine();
        System.out.print("输入影厅总座位数: ");
        int totalSeats = scanner.nextInt();
        cinemaService.addCinema(name, totalSeats);
    }

    private void displayAllCinemas(){
        cinemaService.displayAllCinemas();
    }

    private void displayAllMovies(){
        movieService.displayAllMovies();
    }
    private void addMovie(Scanner scanner){
        System.out.print("输入电影名称: ");
        String title = scanner.nextLine();
        System.out.print("输入电影状态（上映中/即将下架）: ");
        String status = scanner.nextLine();
        movieService.addMovie(title, status);
    }

    private void addSeat(Scanner scanner) throws SQLException {
        System.out.print("输入场次ID: ");
        int showingId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        System.out.print("输入新增座位: ");
        String seatNumber = scanner.nextLine();
        seatService.addSeat(showingId, seatNumber);
    }

    private void addShowing(Scanner scanner) {
        System.out.print("输入影院ID: ");
        int cinemaId = scanner.nextInt();
        System.out.print("输入电影ID: ");
        int movieIdForShowing = scanner.nextInt();
        System.out.print("输入开始时间 (yyyy-mm-dd hh:mm:ss): ");
        scanner.nextLine(); // consume newline
        String startTimeStr = scanner.nextLine();
        Timestamp startTime = Timestamp.valueOf(startTimeStr);
        System.out.print("输入该场次的票价: ");
        double price = scanner.nextDouble();
        showingService.addShowing(cinemaId, movieIdForShowing, startTime,price);
    }
    private void displayAllShowings() {
        showingService.displayAllShowings();
    }

}