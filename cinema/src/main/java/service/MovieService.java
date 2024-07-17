package main.java.service;

import main.java.dao.MovieDAO;
import main.java.dao.ShowingDAO;
import main.java.model.Movie;
import main.java.model.Showing;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

/**
 * MovieService接口定义了与电影相关的操作，包括添加、删除、修改状态和获取所有电影信息等。
 */
public interface MovieService {

    /**
     * 根据电影ID删除电影。
     * @param movieId 要删除的电影的ID。
     * @throws SQLException 如果数据库操作出现异常。
     */
    public void removeMovieById(int movieId) throws SQLException;

    /**
     * 添加一部新电影。
     * @param title 电影的标题。
     * @param status 电影的状态。
     */
    public void addMovie(String title, String status) ;

    /**
     * 获取所有电影的信息。
     * @return 包含所有电影信息的列表。
     * @throws SQLException 如果数据库操作出现异常。
     */
    public List<Movie> getAllMovies() throws SQLException ;

    /**
     * 修改指定电影的状态。
     * @param movie_id 要修改状态的电影的ID。
     * @param status 新的电影状态。
     */
    public void alterMovieStatus(int movie_id, String status) ;

    /**
     * 显示所有电影的信息。
     */
    public void displayAllMovies() ;
}
