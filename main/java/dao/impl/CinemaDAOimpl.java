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
    public void addCinema(Cinema cinema) throws SQLException {
        String sql = "INSERT INTO cinemas (name, total_seats) VALUES ( ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, cinema.getName());
        stmt.setInt(2, cinema.getTotalSeats());
        stmt.executeUpdate();
    }

    public List<Cinema> getAllCinemas() throws SQLException {
        List<Cinema> cinemas = new ArrayList<>();
        String sql = "SELECT * FROM cinemas";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Cinema cinema = new Cinema();
            cinema.setId(rs.getInt("cinema_id"));
            cinema.setName(rs.getString("name"));
            cinema.setTotalSeats(rs.getInt("total_seats"));
            cinemas.add(cinema);
        }

        return cinemas;
    }
}