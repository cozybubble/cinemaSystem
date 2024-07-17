package main.java.dao.impl;

import main.java.dao.CinemaDAO;
import main.java.model.Cinema;
import main.java.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CinemaDAOimpl implements CinemaDAO {

    /**
     * 添加一个新的电影院到数据库中。
     * @param cinema 要添加的电影院对象
     */
    public void addCinema(Cinema cinema) {
        String sql = "INSERT INTO cinemas (name, total_seats) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, cinema.getName());
            stmt.setInt(2, cinema.getTotalSeats());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取所有电影院的列表。
     * @return 返回包含所有电影院对象的列表
     */
    public List<Cinema> getAllCinemas() {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM cinemas";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Cinema cinema = new Cinema();
                cinema.setId(rs.getInt("cinema_id"));
                cinema.setName(rs.getString("name"));
                cinema.setTotalSeats(rs.getInt("total_seats"));
                cinemas.add(cinema);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cinemas;
    }
}
