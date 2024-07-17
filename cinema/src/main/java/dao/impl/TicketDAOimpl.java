package main.java.dao.impl;

import main.java.dao.TicketDAO;
import main.java.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import main.java.model.Ticket;

public class TicketDAOimpl implements TicketDAO {

    /**
     * 根据放映ID、座位ID和用户ID退款票。
     * @param showingId 放映ID
     * @param seatId 座位ID
     * @param userId 用户ID
     */
    public void refundTicket(int showingId, int seatId, int userId) {
        String sql = "DELETE FROM tickets WHERE showing_id = ? AND seat_id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showingId);
            stmt.setInt(2, seatId);
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加新的票。
     * @param showingId 放映ID
     * @param seatId 座位ID
     * @param userId 用户ID
     * @param ticketPrice 票价
     */
    public void addTicket(int showingId, int seatId, int userId, double ticketPrice) {
        String sql = "INSERT INTO tickets (showing_id, seat_id, user_id, ticket_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showingId);
            stmt.setInt(2, seatId);
            stmt.setInt(3, userId);
            stmt.setDouble(4, ticketPrice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新座位的可用性。
     * @param seatId 座位ID
     * @param available 是否可用
     * @throws SQLException SQL异常
     */
    public void updateSeatAvailability(int seatId, int available) {
        String sql = "UPDATE seats SET is_available = ? WHERE seat_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, available);
            stmt.setInt(2, seatId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户ID获取票的列表。
     * @param userId 用户ID
     * @return 返回包含指定用户ID的票对象的列表
     * @throws SQLException SQL异常
     */
    public List<Ticket> getTicketsByUserId(int userId) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ticket ticket = new Ticket();
                    ticket.setTicketId(rs.getInt("ticket_id"));
                    ticket.setUserId(rs.getInt("user_id"));
                    ticket.setShowingId(rs.getInt("showing_id"));
                    ticket.setSeatId(rs.getInt("seat_id"));
                    ticket.setTicketPrice(rs.getDouble("ticket_price"));
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }
}
