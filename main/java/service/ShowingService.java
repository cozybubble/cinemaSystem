package main.java.service;


import main.java.dao.SeatDAO;
import main.java.dao.ShowingDAO;
import main.java.model.Showing;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.List;

public interface ShowingService {


    public void removeShowing(int removeShowingId) throws SQLException;

    public List<Showing> getAllShowings() throws SQLException;

    public void addShowing(int cinemaId, int movieId, Timestamp startTime, double price);

    public double getPriceByShowingId(int showingId) throws SQLException;


    public Timestamp getStartTimeByShowingId(int showingId) throws SQLException ;

    public String getMovieNameByShowingId(int showingId) throws SQLException ;

    public String getCinemaNameByShowingId(int showingId) throws SQLException ;

    public void displayAllShowings() ;
}
