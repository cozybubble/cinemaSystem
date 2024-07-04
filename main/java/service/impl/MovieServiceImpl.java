package main.java.service.impl;

import main.java.dao.MovieDAO;
import main.java.dao.ShowingDAO;
import main.java.dao.impl.MovieDAOimpl;
import main.java.dao.impl.ShowingDAOimpl;
import main.java.model.Movie;
import main.java.model.Showing;
import main.java.service.MovieService;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

public class MovieServiceImpl implements MovieService {
    private MovieDAOimpl movieDAO = new MovieDAOimpl();


    public void removeMovieById(int movieId) throws SQLException {
        List<Movie> movies =  movieDAO.getAllMovies();
        boolean exist = false;
        if(movies.isEmpty()){
            System.out.println("您输入的电影id不存在！");
            System.out.println();
            return;
        }
        for(Movie movie:movies){
            if(movie.getId() == movieId){
                exist  = true;
                break;
            }
        }
        if(!exist){
            System.out.println("您输入的电影id不存在！");
            System.out.println();
            return;
        }
        ShowingDAOimpl showingDAO = new ShowingDAOimpl();
        try {
            List<Showing> showings = showingDAO.getShowingsByMovieId(movieId);
            if(!showings.isEmpty()){
                for(Showing showing: showings){
                    showingDAO.removeShowing(showing.getShowingId());
                }
            }
            movieDAO.removeMovie(movieId);
            System.out.println("电影下架成功！ 相应场次已经删除！");
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addMovie(String title, String status) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setStatus(status);

        try {
            movieDAO.addMovie(movie);
            System.out.println("电影添加成功！");
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("添加电影失败。");
            System.out.println();
        }
    }


    public List<Movie> getAllMovies() throws SQLException {
        return movieDAO.getAllMovies();
    }

    public void alterMovieStatus(int movie_id, String status) {

        try {
            movieDAO.alterMovieStatus(status,movie_id);
            System.out.println("电影状态修改成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("电影状态修改成功失败。");
        }
    }

    public void displayAllMovies() {
        try {
            List<Movie> movies = movieDAO.getAllMovies();
            System.out.println("-----------影片信息-------------");
            System.out.println("电影ID\t\t 电影名称\t\t    电影状态");
            System.out.println();
            for (Movie movie : movies) {
                // 假设电影ID和状态的长度不会超过10个字符
                // 使用10.10s来限制字符宽度，即使字符宽度不同
                System.out.printf("%-10s %-15.15s %-15.15s\n", movie.getId(), movie.getTitle(), movie.getStatus());
            }

            System.out.println("--------------------------------");
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("获取电影信息失败。");
        }
    }
}
