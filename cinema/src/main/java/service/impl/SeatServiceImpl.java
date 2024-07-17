package main.java.service.impl;

import main.java.dao.SeatDAO;
import main.java.dao.impl.SeatDAOimpl;
import main.java.model.Seat;
import main.java.model.Showing;
import main.java.service.SeatService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatServiceImpl implements SeatService {
    private SeatDAOimpl seatDAO = new SeatDAOimpl();

    public void removeSeatByShowingId(int showingId){
        seatDAO.removeSeatByShowingId(showingId);
    }

    public void addSeat(int showingId, String seatNumber) throws SQLException {
        ShowingServiceImpl showingService = new ShowingServiceImpl();
        List<Showing> showings = null;
        try {
            showings = showingService.getAllShowings();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        boolean existShowingId = false;
        for (Showing showing : showings) {
            if (showing.getShowingId() == showingId) {
                existShowingId = true;
            }
        }
        if (!existShowingId) {
            System.out.println("您输入的场次ID不存在！ 请重新输入");
            System.out.println();
            return;
        }
        List<Seat> seats = seatDAO.getSeatsByShowingId(showingId);
        boolean existSeatNumber = false;
        for(Seat seat:seats){
            if(seat.getSeatNumber().equals(seatNumber)){
                existSeatNumber = true;
            }
        }

        if(existSeatNumber){
            System.out.println("您输入的座位已存在！ 请重新输入");
            System.out.println();
            return;
        }
        try {
            seatDAO.addSeat(showingId, seatNumber, 1);
            System.out.println("座位添加成功！");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("座位添加失败。");
        }
    }


    public String getSeatNumberBySeatId(int seatId) throws SQLException {
        String seatNumber = "";
        ResultSet resultSet = seatDAO.getSeatNumberBySeatId(seatId);
        if (resultSet.next()) {
            seatNumber = resultSet.getString("seat_number");
        }
        return seatNumber;
    }

    public void displaySeatsByShowingId(int showingId) {
        try {
            List<Seat> seats = seatDAO.getSeatsByShowingId(showingId);
            if(seats.isEmpty()){
                System.out.println("该场次暂无座位信息！");
                System.out.println();
                return;
            }
            System.out.println("-------------------------------");
            System.out.println("场次ID: " + showingId);
            System.out.println("座位ID\t\t座位号\t  是否可用");
            for (Seat seat : seats) {
                System.out.printf("%-10s %-10s %-10s%n", seat.getSeatId(),  seat.getSeatNumber(),  seat.isAvailable()==1?"是":"否");
            }

            System.out.println("-------------------------------");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取座位信息失败。");
        }
    }

    public void displayRemainedSeatsByShowingId(int showingId) {
        try {
            List<Seat> seats = seatDAO.getSeatsByShowingId(showingId);
            List<Seat> remainedSeats = new ArrayList<>();
            int remainedSeatsNumber = 0;
            for (Seat seat : seats) {
                if(seat.isAvailable() == 1){
                    remainedSeats.add(seat);
                    remainedSeatsNumber++;
                }
            }
            System.out.println("-------------剩余座位-----------");
            System.out.println("场次ID: " + showingId + "  剩余座位数: " + remainedSeatsNumber);
            System.out.println();
            System.out.println("座位ID\t\t座位号\t   ");
            for (Seat seat : remainedSeats) {
                System.out.printf("%-10s %-10s \n", seat.getSeatId(),  seat.getSeatNumber());
            }

            System.out.println("------------------------------");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取座位信息失败。");
        }
    }
}