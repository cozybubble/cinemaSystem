package main.java.dao.impl;

import main.java.dao.MovieDAO;
import main.java.model.Movie;
import main.java.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieDAOimpl implements MovieDAO {

    /**
     * 删除指定ID的电影。
     * @param movieId 要删除的电影ID
     */
    public void removeMovie(int movieId) {
        String sql = "DELETE FROM movies WHERE movie_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
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
     * 添加一部新电影到数据库中。
     * @param movie 要添加的电影对象
     */
    public void addMovie(Movie movie) {
        String sql = "INSERT INTO movies (title, status) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getStatus());
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
     * 修改指定ID的电影状态。
     * @param status 新的状态
     * @param movieId 要修改状态的电影ID
     */
    public void alterMovieStatus(String status, int movieId) {
        String sql = "UPDATE movies SET status = ? WHERE movie_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, status);
            stmt.setInt(2, movieId);
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
     * 获取所有电影的列表。
     * @return 返回包含所有电影对象的列表
     */
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Movie movie = new Movie();
                movie.setId(rs.getInt("movie_id"));
                movie.setTitle(rs.getString("title"));
                movie.setStatus(rs.getString("status"));
                movies.add(movie);
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
        return movies;
    }

    /**
     * 根据电影标题获取电影。
     * @param title 电影标题
     * @return 返回包含指定标题电影信息的结果集
     */
    public ResultSet getMovieByTitle(String title) {
        String sql = "SELECT * FROM movies WHERE title = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, title);
            rs = stmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 根据电影标题获取电影ID。
     * @param title 电影标题
     * @return 返回电影ID，如果未找到则返回-1
     */
    public int getMovieIdByTitle(String title) {
        ResultSet rs = getMovieByTitle(title);
        try {
            if (rs != null && rs.next()) {
                return rs.getInt("movie_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    /**
     * 检查电影是否存在。
     * @param movieId 要检查的电影ID
     * @return 如果电影存在返回true，否则返回false
     */
    public boolean exists(int movieId) {
        String sql = "SELECT 1 FROM movies WHERE movie_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            rs = stmt.executeQuery();
            return rs.next();
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
        return false;
    }
}
