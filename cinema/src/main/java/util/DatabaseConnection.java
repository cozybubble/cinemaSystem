package main.java.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 管理数据库连接
 */
public class DatabaseConnection {
    private static Connection connection;

    static {
        // 加载配置文件
        Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find config.properties");
            }
            properties.load(input);

            // 从配置文件中读取数据库连接信息
            String url = properties.getProperty("jdbc.url");
            String user = properties.getProperty("jdbc.user");
            String password = properties.getProperty("jdbc.password");

            // 建立数据库连接
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                // 加载配置文件
                Properties properties = new Properties();
                try (FileInputStream input = new FileInputStream("config.properties")) {
                    if (input == null) {
                        System.out.println("Sorry, unable to find config.properties");
                        return null;
                    }
                    properties.load(input);

                    // 从配置文件中读取数据库连接信息
                    String url = properties.getProperty("jdbc.url");
                    String user = properties.getProperty("jdbc.user");
                    String password = properties.getProperty("jdbc.password");

                    // 建立数据库连接
                    connection = DriverManager.getConnection(url, user, password);
                } catch (IOException | SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
