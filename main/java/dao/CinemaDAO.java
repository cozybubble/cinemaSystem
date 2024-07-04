package main.java.dao;



import main.java.model.Cinema;
import main.java.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public interface CinemaDAO {
    public void addCinema(Cinema cinema) throws SQLException ;

    public List<Cinema> getAllCinemas() throws SQLException;
}