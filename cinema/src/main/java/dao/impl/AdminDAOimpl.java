package main.java.dao.impl;

import main.java.dao.AdminDAO;
import main.java.model.Admin;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAOimpl implements AdminDAO {

    /**
     * 添加一个新的管理员到数据库中。
     * @param admin 要添加的管理员对象
     */
    public void addAdmin(Admin admin) {
        String sql = "INSERT INTO admins (admin_name, admin_password) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, admin.getAdminName());
            stmt.setString(2, admin.getAdminPassword());
            stmt.executeUpdate();

            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                admin.setAdminId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据管理员ID从数据库中获取管理员信息。
     * @param adminId 要获取的管理员ID
     * @return 返回对应ID的管理员对象，如果未找到则返回null
     */
    public Admin getAdminById(int adminId) {
        String sql = "SELECT * FROM admins WHERE admin_id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, adminId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setAdminName(rs.getString("admin_name"));
                admin.setAdminPassword(rs.getString("admin_password"));
                return admin;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取所有管理员的列表。
     * @return 返回包含所有管理员对象的列表
     */
    public List<Admin> getAllAdmins() {
        String sql = "SELECT * FROM admins";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Admin> admins = new ArrayList<>();

        try {
            conn = DatabaseConnection.getConnection();
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Admin admin = new Admin();
                admin.setAdminId(rs.getInt("admin_id"));
                admin.setAdminName(rs.getString("admin_name"));
                admin.setAdminPassword(rs.getString("admin_password"));
                admins.add(admin);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return admins;
    }
}
