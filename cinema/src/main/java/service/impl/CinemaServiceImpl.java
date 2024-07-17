package main.java.service.impl;

import main.java.dao.CinemaDAO;
import main.java.dao.impl.CinemaDAOimpl;
import main.java.model.Cinema;
import main.java.service.CinemaService;

import java.sql.SQLException;
import java.util.List;

public class CinemaServiceImpl implements CinemaService {
    private CinemaDAO cinemaDAO = new CinemaDAOimpl();

    public void addCinema(String name, int totalSeats) {
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setTotalSeats(totalSeats);

        try {
            cinemaDAO.addCinema(cinema);
            System.out.println("影院添加成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("添加影院失败。");
        }
    }

    public List<Cinema> getAllCinemas() throws SQLException {
        return cinemaDAO.getAllCinemas();
    }

    public void displayAllCinemas() {
        try {
            List<Cinema> cinemas = cinemaDAO.getAllCinemas();
            if(cinemas.isEmpty()){
                System.out.println("暂无影厅信息！");
                System.out.println();
                return;
            }
            System.out.println("------------影厅信息--------------");
            System.out.println("影厅ID\t\t 影厅名称\t\t 总座位数");
            for (Cinema cinema : cinemas) {
                System.out.printf("%-10s   %-15s %-10s\n",
                        cinema.getId(), cinema.getName(), cinema.getTotalSeats());
            }
            System.out.println("--------------------------------");
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("获取影院信息失败。");
        }
    }
}