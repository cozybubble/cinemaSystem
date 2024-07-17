package main.java.service;

import main.java.dao.SeatDAO;
import main.java.model.Seat;
import main.java.model.Showing;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SeatService {

    public void removeSeatByShowingId(int showingId);

    public void addSeat(int showingId, String seatNumber) throws SQLException;


    public String getSeatNumberBySeatId(int seatId) throws SQLException ;

    public void displaySeatsByShowingId(int showingId) ;

    public void displayRemainedSeatsByShowingId(int showingId) ;

}