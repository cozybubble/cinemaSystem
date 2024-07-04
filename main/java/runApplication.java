package main.java;

import main.java.controller.CinemaController;

import java.sql.SQLException;

public class runApplication {
    public static void main(String[] args) throws SQLException {
        new CinemaController().start();
    }
}
