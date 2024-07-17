package main.java.dao.impl;

import main.java.dao.SeatDAO;
import main.java.model.Seat;
import main.java.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAOimpl implements SeatDAO {

    /**
     * 根据放映ID删除座位。
     * @param showingId 要删除座位的放映ID
     */
    public void removeSeatByShowingId(int showingId) {
        String sql = "DELETE FROM seats WHERE showing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个新的座位到数据库中。
     * @param showingId 放映ID
     * @param seatNumber 座位号
     * @param available 座位是否可用
     */
    public void addSeat(int showingId, String seatNumber, int available) {
        String sql = "INSERT INTO seats (showing_id, seat_number, is_available) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showingId);
            stmt.setString(2, seatNumber);
            stmt.setInt(3, available);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有座位的列表。
     * @return 返回包含所有座位对象的列表
     */
    public List<Seat> getAllSeats() {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Seat seat = new Seat();
                seat.setSeatId(rs.getInt("seat_id"));
                seat.setSeatNumber(rs.getString("seat_number"));
                seat.setAvailable(rs.getInt("is_available"));
                seat.setShowingId(rs.getInt("showing_id"));
                seats.add(seat);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    /**
     * 根据放映ID获取座位列表。
     * @param showingId 放映ID
     * @return 返回包含指定放映ID的座位对象的列表
     */
    public List<Seat> getSeatsByShowingId(int showingId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE showing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showingId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat();
                    seat.setSeatId(rs.getInt("seat_id"));
                    seat.setSeatNumber(rs.getString("seat_number"));
                    seat.setAvailable(rs.getInt("is_available"));
                    seat.setShowingId(rs.getInt("showing_id"));
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }

    /**
     * 根据座位ID获取座位号。
     * @param seatId 座位ID
     * @return 返回包含指定座位ID的座位号的结果集
     */
    public ResultSet getSeatNumberBySeatId(int seatId) {
        String sql = "SELECT seat_number FROM seats WHERE seat_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, seatId);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 根据放映ID获取可用座位列表。
     * @param showingId 放映ID
     * @return 返回包含指定放映ID的可用座位对象的列表
     */
    public List<Seat> getAvailableSeatsByShowingId(int showingId) {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT seat_id, seat_number FROM seats WHERE showing_id = ? AND is_available = 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showingId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Seat seat = new Seat();
                    seat.setSeatId(rs.getInt("seat_id"));
                    seat.setSeatNumber(rs.getString("seat_number"));
                    seats.add(seat);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return seats;
    }
}
