package main.java.dao;

import main.java.model.Movie;
import main.java.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface MovieDAO {

    /**
     * 根据电影ID删除电影。
     * @param movieId 电影ID
     */
    public void removeMovie(int movieId);

    /**
     * 添加电影到数据库。
     * @param movie 电影对象
     * @throws SQLException 如果数据库访问出错
     */
    public void addMovie(Movie movie) throws SQLException;

    /**
     * 修改电影的状态。
     * @param status 新的电影状态
     * @param movieId 电影ID
     * @throws SQLException 如果数据库访问出错
     */
    public void alterMovieStatus(String status, int movieId) throws SQLException;

    /**
     * 获取所有电影的列表。
     * @return 电影对象列表
     * @throws SQLException 如果数据库访问出错
     */
    public List<Movie> getAllMovies() throws SQLException;

    /**
     * 根据电影标题获取电影信息。
     * @param title 电影标题
     * @return 结果集对象，包含电影信息
     * @throws SQLException 如果数据库访问出错
     */
    public ResultSet getMovieByTitle(String title) throws SQLException;

    /**
     * 根据电影标题获取电影ID。
     * @param title 电影标题
     * @return 电影ID，如果电影不存在则返回-1
     * @throws SQLException 如果数据库访问出错
     */
    public int getMovieIdByTitle(String title) throws SQLException;

}
