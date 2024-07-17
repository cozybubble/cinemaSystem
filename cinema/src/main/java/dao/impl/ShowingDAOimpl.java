package main.java.dao.impl;

import main.java.dao.ShowingDAO;
import main.java.model.Showing;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShowingDAOimpl implements ShowingDAO {

    /**
     * 根据放映ID删除放映。
     * @param showingId 要删除的放映ID
     */
    public void removeShowing(int showingId) {
        String sql = "DELETE FROM showings WHERE showing_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, showingId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加新的放映并返回生成的放映ID。
     * @param cinemaId 电影院ID
     * @param movieId 电影ID
     * @param startTime 开始时间
     * @param price 票价
     * @return 返回生成的放映ID
     * @throws SQLException SQL异常
     */
    public int addShowing(int cinemaId, int movieId, Timestamp startTime, double price) throws SQLException {
        String sql = "INSERT INTO showings (cinema_id, movie_id, start_time, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, cinemaId);
            stmt.setInt(2, movieId);
            stmt.setTimestamp(3, startTime);
            stmt.setDouble(4, price);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("添加放映失败，没有影响到任何行。");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("添加放映失败，未获得ID。");
                }
            }
        }
    }

    /**
     * 获取所有放映的列表。
     * @return 返回包含所有放映对象的列表
     * @throws SQLException SQL异常
     */
    public List<Showing> getAllShowings() {
        List<Showing> showings = new ArrayList<>();
        String sql = "SELECT * FROM showings";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Showing showing = new Showing();
                showing.setShowingId(rs.getInt("showing_id"));
                showing.setCinemaId(rs.getInt("cinema_id"));
                showing.setMovieId(rs.getInt("movie_id"));
                showing.setStartTime(rs.getTimestamp("start_time"));
                showing.setPrice(rs.getDouble("price"));
                showings.add(showing);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showings;
    }

    /**
     * 根据电影ID获取放映列表。
     * @param movieId 电影ID
     * @return 返回包含指定电影ID的放映对象的列表
     * @throws SQLException SQL异常
     */
    public List<Showing> getShowingsByMovieId(int movieId) {
        List<Showing> showings = new ArrayList<>();
        String sql = "SELECT * FROM showings WHERE movie_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Showing showing = new Showing();
                    showing.setShowingId(rs.getInt("showing_id"));
                    showing.setCinemaId(rs.getInt("cinema_id"));
                    showing.setMovieId(rs.getInt("movie_id"));
                    showing.setStartTime(rs.getTimestamp("start_time"));
                    showings.add(showing);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return showings;
    }

    /**
     * 根据放映ID获取票价。
     * @param showingId 放映ID
     * @return 返回包含指定放映ID的票价的结果集
     * @throws SQLException SQL异常
     */
    public ResultSet getPriceByShowingId(int showingId) {
        String sql = "SELECT price FROM showings WHERE showing_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, showingId);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 根据放映ID获取开始时间。
     * @param showingId 放映ID
     * @return 返回包含指定放映ID的开始时间的结果集
     * @throws SQLException SQL异常
     */
    public ResultSet getStartTimeByShowingId(int showingId) {
        String sql = "SELECT start_time FROM showings WHERE showing_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, showingId);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 根据放映ID获取电影院名称。
     * @param showingId 放映ID
     * @return 返回包含指定放映ID的电影院名称的结果集
     * @throws SQLException SQL异常
     */
    public ResultSet getCinemaNameByShowingId(int showingId) {
        String sql = "SELECT name FROM showings s NATURAL JOIN cinemas WHERE showing_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, showingId);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 根据放映ID获取电影名称。
     * @param showingId 放映ID
     * @return 返回包含指定放映ID的电影名称的结果集
     * @throws SQLException SQL异常
     */
    public ResultSet getMovieNameByShowingId(int showingId) {
        String sql = "SELECT title FROM showings s NATURAL JOIN movies WHERE showing_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, showingId);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
