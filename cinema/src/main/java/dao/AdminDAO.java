package main.java.dao;


import main.java.model.Admin;
import main.java.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public interface AdminDAO {

    public void addAdmin(Admin admin) throws SQLException ;

    public Admin getAdminById(int adminId) throws SQLException ;
    public List<Admin> getAllAdmins() throws SQLException ;
}
