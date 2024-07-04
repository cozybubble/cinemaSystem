package main.java.dao.impl;



import main.java.dao.SeatDAO;
import main.java.dao.ShowingDAO;
import main.java.model.Seat;
import main.java.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SeatDAOimpl implements SeatDAO {

    public void removeSeatByShowingId(int showingId){
        String sql = "DELETE FROM seats WHERE showing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSeat(int showingId, String seatNumber, int available)  {
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



    public List<Seat> getAllSeats() throws SQLException {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Seat seat = new Seat();
            seat.setSeatId(rs.getInt("seat_id"));
            seat.setSeatNumber(rs.getString("seat_number"));
            seat.setAvailable(rs.getInt("is_available"));
            seat.setShowingId(rs.getInt("showing_id"));
            seats.add(seat);
        }

        return seats;
    }

    public List<Seat> getSeatsByShowingId(int showingId) throws SQLException {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT * FROM seats WHERE showing_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, showingId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Seat seat = new Seat();
            seat.setSeatId(rs.getInt("seat_id"));
            seat.setSeatNumber(rs.getString("seat_number"));
            seat.setAvailable(rs.getInt("is_available"));
            seat.setShowingId(rs.getInt("showing_id"));
            seats.add(seat);
        }

        return seats;
    }

    public ResultSet getSeatNumberBySeatId(int seatId) throws SQLException {
        String sql = "SELECT seat_number FROM seats WHERE seat_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, seatId);
        return stmt.executeQuery();
    }

    public List<Seat> getAvailableSeatsByShowingId(int showingId) throws SQLException {
        List<Seat> seats = new ArrayList<>();
        String sql = "SELECT seat_id, seat_number FROM seats WHERE showing_id = ? AND is_available = 1";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, showingId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Seat seat = new Seat();
            seat.setSeatId(rs.getInt("seat_id"));
            seat.setSeatNumber(rs.getString("seat_number"));
            seats.add(seat);
        }
        return seats;
    }
}