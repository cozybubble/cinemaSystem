package main.java.service;

import main.java.model.Cinema;

import java.sql.SQLException;
import java.util.List;

public interface CinemaService {

    public void addCinema(String name, int totalSeats);

    public List<Cinema> getAllCinemas() throws SQLException ;

    public void displayAllCinemas() ;
}