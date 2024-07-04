package main.java.service.impl;




import main.java.dao.SeatDAO;
import main.java.dao.ShowingDAO;
import main.java.dao.impl.ShowingDAOimpl;
import main.java.model.Showing;
import main.java.service.ShowingService;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public class ShowingServiceImpl implements ShowingService {
    private ShowingDAO showingDAO = new ShowingDAOimpl();



    public void removeShowing(int removeShowingId) throws SQLException {
        List<Showing> showings = showingDAO.getAllShowings();
        if(showings.isEmpty()){
            System.out.println("暂无放映场次！");
            System.out.println();
            return;
        }
        boolean exist = false;

        for(Showing showing:showings){
            if(removeShowingId == showing.getShowingId()){
                exist = true;
                break;
            }
        }

        if(!exist){
            System.out.println("您输入的场次id不存在！");
            System.out.println();
            return;
        }
        SeatServiceImpl seatService = new SeatServiceImpl();
        seatService.removeSeatByShowingId(removeShowingId);
        showingDAO.removeShowing(removeShowingId);
        System.out.println("场次删除成功！");
        System.out.println();
    }

    public List<Showing> getAllShowings() throws SQLException {
        return showingDAO.getAllShowings();
    }

    public void addShowing(int cinemaId, int movieId, Timestamp startTime,double price) {
        try {
            SeatServiceImpl seatService = new SeatServiceImpl();
            int showingId = showingDAO.addShowing(cinemaId, movieId, startTime,price);
            if (showingId != -1) {
                // 生成30个座位，五排六列
                for (int row = 1; row <= 5; row++) {
                    for (int column = 1; column <= 6; column++) {
                        String seatNumber = String.format("%d排%d列", row, column);
                        seatService.addSeat(showingId, seatNumber);
                    }
                }
                System.out.println("放映场次添加成功！");
            } else {
                System.out.println("添加放映失败。");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("添加放映失败。");
        }
    }

    public double getPriceByShowingId(int showingId) throws SQLException {
        ResultSet rs = showingDAO.getPriceByShowingId(showingId);
        if (rs.next()) {
            return rs.getInt("price");
        }
        return -1;
    }


    public Timestamp getStartTimeByShowingId(int showingId) throws SQLException {
        ResultSet rs = showingDAO.getStartTimeByShowingId(showingId);
        if (rs.next()) {
            return rs.getTimestamp("start_time");
        }
        return null;
    }

    public String getMovieNameByShowingId(int showingId) throws SQLException {
        ResultSet rs = showingDAO.getMovieNameByShowingId(showingId);
        if (rs.next()) {
            return rs.getString("title");
        }
        return null;
    }

    public String getCinemaNameByShowingId(int showingId) throws SQLException {
        ResultSet rs = showingDAO.getCinemaNameByShowingId(showingId);
        if (rs.next()) {
            return rs.getString("name");
        }
        return null;
    }

    public void displayAllShowings() {
        try {
            List<Showing> showings = showingDAO.getAllShowings();
            if(showings.isEmpty()){
                System.out.println("暂无放映信息！");
                System.out.println();
                return;
            }
            System.out.println("-------------------------放映信息------------------------------");
            System.out.println();
            System.out.println("放映ID\t影院ID\t\t电影名称\t\t\t\t     开始时间                   票价");
            System.out.println();
            for (Showing showing : showings) {
                String movieName = getMovieNameByShowingId(showing.getShowingId());
                System.out.printf("%-8s %-8s   %-18s %-20s   %8.1f元%n",
                        showing.getShowingId(), showing.getCinemaId(),
                        movieName,  showing.getStartTime(),showing.getPrice());
            }

            System.out.println();
            System.out.println("-------------------------------------------------------------");
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取放映信息失败。");
        }
    }
}
