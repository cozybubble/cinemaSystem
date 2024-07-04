package main.java.dao.impl;



import main.java.dao.ShowingDAO;
import main.java.model.Showing;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShowingDAOimpl implements ShowingDAO {


    public void removeShowing(int showingId){
        String sql = "DELETE FROM showings WHERE showing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int addShowing(int cinemaId, int movieId, Timestamp startTime,double price) throws SQLException {
        String sql = "INSERT INTO showings (cinema_id, movie_id, start_time,price) VALUES (?, ?, ?,?)";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setInt(1, cinemaId);
        stmt.setInt(2, movieId);
        stmt.setTimestamp(3, startTime);
        stmt.setDouble(4, price);
        int affectedRows = stmt.executeUpdate();

        if (affectedRows == 0) {
            throw new SQLException("Adding showing failed, no rows affected.");
        }

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Adding showing failed, no ID obtained.");
            }
        }
    }

    public List<Showing> getAllShowings() throws SQLException {
        List<Showing> showings = new ArrayList<>();
        String sql = "SELECT * FROM showings";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Showing showing = new Showing();
            showing.setShowingId(rs.getInt("showing_id"));
            showing.setCinemaId(rs.getInt("cinema_id"));
            showing.setMovieId(rs.getInt("movie_id"));
            showing.setStartTime(rs.getTimestamp("start_time"));
            showing.setPrice(rs.getDouble("price"));
            showings.add(showing);
        }

        return showings;
    }

    public List<Showing> getShowingsByMovieId(int movieId) throws SQLException {
        List<Showing> showings = new ArrayList<>();
        String sql = "SELECT * FROM showings WHERE movie_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, movieId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Showing showing = new Showing();
            showing.setShowingId(rs.getInt("showing_id"));
            showing.setCinemaId(rs.getInt("cinema_id"));
            showing.setMovieId(rs.getInt("movie_id"));
            showing.setStartTime(rs.getTimestamp("start_time"));
            showings.add(showing);
        }

        return showings;
    }



    public ResultSet getPriceByShowingId(int showingId) throws SQLException {
        String sql = "SELECT price FROM showings WHERE showing_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, showingId);
        return stmt.executeQuery();
    }



    public ResultSet getStartTimeByShowingId(int showingId) throws SQLException {
        String sql = "SELECT start_time FROM showings WHERE showing_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, showingId);
        return stmt.executeQuery();
    }


    public ResultSet getCinemaNameByShowingId(int showingId) throws SQLException {
        String sql = "SELECT name FROM showings s natural join cinemas where showing_id=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, showingId);
        return stmt.executeQuery();
    }

    public ResultSet getMovieNameByShowingId(int showingId) throws SQLException {
        String sql = "SELECT title FROM showings s natural join movies where showing_id=?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, showingId);
        return stmt.executeQuery();
    }
}
