package main.java.dao;



import main.java.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import main.java.model.Ticket;
public interface TicketDAO {

    public void refundTicket(int showingId,int seatId,int userId);

    public void addTicket(int showingId, int seatId, int userId,double ticketPrice)  ;



    public void updateSeatAvailability(int seatId, int available) throws SQLException;

    public List<Ticket> getTicketsByUserId(int userId) throws SQLException;
}
