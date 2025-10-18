package cineflix;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

// Class for connecting to the Database.
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cineflixdb"; 
    private static final String USER = "root"; 
    private static final String PASSWORD = ""; 
    
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Helps locate MySQL JDBC Driver (Connector/J, version: 9.3.0).
            return DriverManager.getConnection(URL, USER, PASSWORD); // Retursn the DB connection.
        } catch (Exception e) {
            // Catch and print the error; failed DB connection.
            Message.error("Database Connection Error: " + e.getMessage());
            return null;
        }
    }
}