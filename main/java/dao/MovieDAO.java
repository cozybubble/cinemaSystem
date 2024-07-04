package main.java.dao;



import main.java.model.Movie;
import main.java.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MovieDAO {

    public void removeMovie(int movieId);
    public void addMovie(Movie movie) throws SQLException ;

    public void alterMovieStatus(String status,int movieId) throws SQLException ;

    public List<Movie> getAllMovies() throws SQLException ;

    public ResultSet getMovieByTitle(String title) throws SQLException ;

    public int getMovieIdByTitle(String title) throws SQLException ;

    public boolean exists(int movieId) throws SQLException ;
}
