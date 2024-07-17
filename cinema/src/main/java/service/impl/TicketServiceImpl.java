package main.java.service.impl;


import main.java.dao.*;
import main.java.dao.impl.*;
import main.java.model.Seat;
import main.java.model.Showing;
import main.java.model.Ticket;
import main.java.model.User;
import main.java.service.SeatService;
import main.java.service.ShowingService;
import main.java.service.TicketService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class TicketServiceImpl implements TicketService {
    private TicketDAOimpl ticketDAO = new TicketDAOimpl();
    private MovieDAOimpl movieDAO = new MovieDAOimpl();
    private SeatDAOimpl seatDAO = new SeatDAOimpl();
    private ShowingDAOimpl showingDAO = new ShowingDAOimpl();
    private ShowingServiceImpl showingService = new ShowingServiceImpl();
    private UserDAOimpl userDAO = new UserDAOimpl();

    public void displayUserTickets(User user){
        try {
            List<Ticket> tickets = getTicketsByUserId(user.getUserId());
            if (tickets.isEmpty()) {
                System.out.println("当前用户无购票记录");
                System.out.println();
            } else {
                System.out.println("---------------------------------------------------------购票记录----------------------------------------------");
                SeatServiceImpl seatService = new SeatServiceImpl();
                for (Ticket ticket : tickets) {
                    String seatNumber = seatService.getSeatNumberBySeatId(ticket.getSeatId());
                    String cinemaName = showingService.getCinemaNameByShowingId(ticket.getShowingId());
                    Timestamp startTime = showingService.getStartTimeByShowingId(ticket.getShowingId());
                    String movieName = showingService.getMovieNameByShowingId(ticket.getShowingId());
                    System.out.println("购票编号:" + ticket.getTicketId() +
                            ",    场次ID: " + ticket.getShowingId() +
                            ",    片名: " + movieName +
                            ",    影厅名: " + cinemaName +
                            ",    座位:  " + seatNumber +
                            ",    开始时间: " + startTime);
                }
                System.out.println("---------------------------------------------------------------------------------------------------------------");
                System.out.println();
            }

        } catch (SQLException e) {
            System.out.println("查询购票信息时出现错误: " + e.getMessage());
            System.out.println();
        }
    }

    public void refundTicket(User user,int showingId,String seatNumber) throws SQLException {
        List<Ticket> userTickets = getTicketsByUserId(user.getUserId());
        if(userTickets.isEmpty()){
            System.out.println("您似乎还没有买过票，快去买一张吧！");
            System.out.println();
            return;
        }
        SeatServiceImpl seatService = new SeatServiceImpl();
        for(Ticket ticket: userTickets){
            if(ticket.getShowingId() == showingId &&
                    seatService.getSeatNumberBySeatId(ticket.getSeatId()).equals(seatNumber)){
                double ticketPrice = ticket.getTicketPrice();
                user.setAccountBalance(user.getAccountBalance()+ticketPrice);
                UserServiceImpl userService = new UserServiceImpl();
                userService.updateAccount(user);
                ticketDAO.refundTicket(showingId,ticket.getSeatId(),user.getUserId());
                System.out.println("退票成功！ 票价: "+ticketPrice+"元已退回您的账户余额！");
                System.out.println();
                return;
            }
        }
        System.out.println("您想要退的这张票不存在!");
        System.out.println();
    }

    public void purchaseTicket(String movieTitle, User user) {
        try {
            // 获取电影ID
            int movieId = movieDAO.getMovieIdByTitle(movieTitle);
            if (movieId == -1) {
                System.out.println("电影未找到。");
                return;
            }

            // 显示此电影的所有放映场次
            List<Showing> showings = showingDAO.getShowingsByMovieId(movieId);
            System.out.println("-----------------------放映场次------------------------");
            System.out.println("放映ID\t影厅ID\t\t电影名称\t\t\t\t   开始时间");
            for (Showing showing : showings) {
                String movieName = showingService.getMovieNameByShowingId(showing.getShowingId());
                System.out.printf("%-8s %-8s   %-18s %-20s%n",  showing.getShowingId(), showing.getCinemaId(),  movieName,  showing.getStartTime());
            }
            System.out.println("------------------------------------------------------");


            // 用户选择一个放映场次
            Scanner scanner = new Scanner(System.in);
            int selectedShowingId = 0;

            while (true){
                try {
                    System.out.print("选择放映ID: ");
                    selectedShowingId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    break;
                } catch (Exception e) {
                    System.out.println("请输入数字！");
                    scanner.nextLine();
                }
            }



            double showingPrice = showingService.getPriceByShowingId(selectedShowingId);
            if (showingPrice > user.getAccountBalance()) {
                System.out.printf("购票失败！您的账户余额不足\n" +
                                "票价为：%.2f元， 您的账户余额为：%.2f元",
                        showingService.getPriceByShowingId(selectedShowingId), user.getAccountBalance());
                System.out.println();
                return;
            }

            // 显示该场次的可用座位
            List<Seat> availableSeats = seatDAO.getAvailableSeatsByShowingId(selectedShowingId);
            if(availableSeats == null || availableSeats.isEmpty()){
                System.out.println("座位不足！ 请选择其他场次。");
                System.out.println();
                return;
            }
            System.out.println("---------------------剩余座位---------------------");
            for (Seat availableSeat : availableSeats) {
                System.out.println("座位ID: " + availableSeat.getSeatId() +
                        ", 座位号: " + availableSeat.getSeatNumber());
            }
            System.out.println("-------------------------------------------------");


            // 用户选择自动分配座位还是手动选择座位
            System.out.println("请选择自动分配座位还是手动选择座位 (1: 自动分配, 2: 手动选择): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            int selectedSeatId = -1;
            if (choice == 1) {
                selectedSeatId = availableSeats.get(0).getSeatId();
            } else if (choice == 2) {
                System.out.print("请输入想要的座位ID: ");
                int seatNumber = scanner.nextInt();
                for (Seat availableSeat : availableSeats) {
                    if (availableSeat.getSeatId() == seatNumber)
                        selectedSeatId = seatNumber;
                }
            }

            if (selectedSeatId != -1) {
                ticketDAO.addTicket(selectedShowingId, selectedSeatId, user.getUserId(),showingPrice);
                ticketDAO.updateSeatAvailability(selectedSeatId, 0);
                double leftMoney =  user.getAccountBalance() - showingService.getPriceByShowingId(selectedShowingId);
                user.setAccountBalance(leftMoney);
                userDAO.updateAccount(user);
                ResultSet resultSet = showingDAO.getCinemaNameByShowingId(selectedShowingId);
                String cinemaName = "";
                String seatNumber = "";
                if (resultSet.next()) {
                    cinemaName = resultSet.getString("name");
                }
                resultSet = seatDAO.getSeatNumberBySeatId(selectedSeatId);
                if (resultSet.next()) {
                    seatNumber = resultSet.getString("seat_number");
                }

                System.out.printf("购票成功！  您的座位在: %s  %s\n", cinemaName, seatNumber);
                System.out.println();
            } else {
                System.out.println("所选座位不可用或不存在。");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("购票失败。");
        }
    }



    public List<Ticket> getTicketsByUserId(int userId) throws SQLException {
        return ticketDAO.getTicketsByUserId(userId);
    }
}