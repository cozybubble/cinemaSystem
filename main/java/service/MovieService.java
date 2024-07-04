package main.java.service;

import main.java.dao.MovieDAO;
import main.java.dao.ShowingDAO;
import main.java.model.Movie;
import main.java.model.Showing;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public interface MovieService {


    public void removeMovieById(int movieId) throws SQLException;

    public void addMovie(String title, String status) ;


    public List<Movie> getAllMovies() throws SQLException ;
    public void alterMovieStatus(int movie_id, String status) ;

    public void displayAllMovies() ;
}
