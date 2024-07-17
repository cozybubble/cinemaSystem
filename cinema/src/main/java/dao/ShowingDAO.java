package main.java.dao;



import main.java.model.Showing;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface ShowingDAO {


    public void removeShowing(int showingId);

    public int addShowing(int cinemaId, int movieId, Timestamp startTime,double price) throws SQLException ;



    public List<Showing> getAllShowings() throws SQLException ;

    public List<Showing> getShowingsByMovieId(int movieId) throws SQLException ;



    public ResultSet getPriceByShowingId(int showingId) throws SQLException ;


    public ResultSet getStartTimeByShowingId(int showingId) throws SQLException ;


    public ResultSet getCinemaNameByShowingId(int showingId) throws SQLException ;

    public ResultSet getMovieNameByShowingId(int showingId) throws SQLException ;
}
