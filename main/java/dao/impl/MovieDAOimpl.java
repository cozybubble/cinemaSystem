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

    public void removeMovie(int movieId){
        String sql = "DELETE FROM movies WHERE movie_id = ?";
        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addMovie(Movie movie) throws SQLException {
        String sql = "INSERT INTO movies (title, status) VALUES (?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, movie.getTitle());
        stmt.setString(2, movie.getStatus());
        stmt.executeUpdate();
    }

    public void alterMovieStatus(String status,int movieId) throws SQLException {
        String sql = "UPDATE movies SET status = ? WHERE movie_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,status);
        stmt.setString(2, String.valueOf(movieId));
        stmt.executeUpdate();
    }

    public List<Movie> getAllMovies() throws SQLException {
        List<Movie> movies = new ArrayList<>();
        String sql = "SELECT * FROM movies";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            Movie movie = new Movie();
            movie.setId(rs.getInt("movie_id"));
            movie.setTitle(rs.getString("title"));
            movie.setStatus(rs.getString("status"));
            movies.add(movie);
        }

        return movies;
    }

    public ResultSet getMovieByTitle(String title) throws SQLException {
        String sql = "SELECT * FROM movies WHERE title = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, title);
        return stmt.executeQuery();
    }

    public int getMovieIdByTitle(String title) throws SQLException {
        ResultSet rs = getMovieByTitle(title);
        if (rs.next()) {
            return rs.getInt("movie_id");
        }
        return -1;
    }

    public boolean exists(int movieId) throws SQLException {
        String sql = "SELECT 1 FROM movies WHERE movie_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, movieId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
