package main.java.service;


import main.java.dao.*;
import main.java.model.Seat;
import main.java.model.Showing;
import main.java.model.Ticket;
import main.java.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public interface TicketService {


    public void displayUserTickets(User user);
    public void refundTicket(User user,int showingId,String seatNumber) throws SQLException ;

    public void purchaseTicket(String movieTitle, User user) ;



    public List<Ticket> getTicketsByUserId(int userId) throws SQLException;
}