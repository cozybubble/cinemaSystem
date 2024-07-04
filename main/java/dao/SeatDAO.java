package main.java.dao;



import main.java.model.Seat;
import main.java.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface SeatDAO {


    public void addSeat(int showingId, String seatNumber, int available);



    public List<Seat> getAllSeats() throws SQLException ;

    public List<Seat> getSeatsByShowingId(int showingId) throws SQLException ;

    public ResultSet getSeatNumberBySeatId(int seatId) throws SQLException ;

    public List<Seat> getAvailableSeatsByShowingId(int showingId) throws SQLException ;
}