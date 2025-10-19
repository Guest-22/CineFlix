package cineflix;

// import java.sql.*;

public class CineFlix {

    public static void main(String[] args) {
        // This video rental system is built and designed using JFrame Form dropdown.
        // MySQL Database has been integrated as part of the requirement.
        // Use (Ctrl + 7 shortcut) to see all the variables used in this system project.
        // Shows the login JFrame by default.
         
        /*
        // Pre-loads UI; avoid glitches.
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);    
        }); */
        
        new AdminMovieInventory().setVisible(true);
        
        /*
        // You can check the database connection first by uncommenting the ff. code below & the java.sql import above.
        Connection conn = DBConnection.dbConnect();
        if (conn != null)System.out.println("Database connection established successfully!");
        else System.out.println("Failed to connect to the database."); */
    }
}