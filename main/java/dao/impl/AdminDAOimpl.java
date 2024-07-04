package main.java.dao.impl;


import main.java.dao.AdminDAO;
import main.java.model.Admin;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOimpl implements AdminDAO {

    public void addAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO admins (admin_name, admin_password) VALUES (?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, admin.getAdminName());
        stmt.setString(2, admin.getAdminPassword());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            admin.setAdminId(rs.getInt(1));
        }
    }

    public Admin getAdminById(int adminId) throws SQLException {
        String sql = "SELECT * FROM admins WHERE admin_id = ?";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, adminId);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            Admin admin = new Admin();
            admin.setAdminId(rs.getInt("admin_id"));
            admin.setAdminName(rs.getString("admin_name"));
            admin.setAdminPassword(rs.getString("admin_password"));
            return admin;
        } else {
            return null;
        }
    }
    public List<Admin> getAllAdmins() throws SQLException {
        String sql = "SELECT * FROM admins";
        Connection conn = DatabaseConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Admin> admins = new ArrayList<>();
        while (rs.next()) {
            Admin admin = new Admin();
            admin.setAdminId(rs.getInt("admin_id"));
            admin.setAdminName(rs.getString("admin_name"));
            admin.setAdminPassword(rs.getString("admin_password"));
            admins.add(admin);
        }
        return admins;
    }
    // 其他CRUD操作
}
