/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

/**
 *
 * @author neutron
 */
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connection_sql {
    public Connection con() throws ClassNotFoundException {
        // SQLite connection string
        try {
           Class.forName("org.sqlite.JDBC");
           Connection conn= DriverManager.getConnection("jdbc:sqlite:src/databaseAttendance.db");
           return conn;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
