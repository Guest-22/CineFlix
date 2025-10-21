package cineflix;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {
    private Connection conn;
        
    public static final String TABLE_RENTALS = "tblRentals";
    public static final String COL_ID = "rentalID"; // PRIMARY KEY.
    public static final String COL_ACCOUNT_ID = "accountID"; // FK references tblAccounts.
    public static final String COL_MOVIE_ID = "movieID"; // FK references tblMovies.
    public static final String COL_RENTAL_DATE = "rentalDate"; // DATETIME.
    public static final String COL_RETURN_DATE = "returnDate"; // DATETIME.
    public static final String COL_RENTAL_COST = "rentalCost"; // total cost of the rent (priceperweek x rentedweekduration).
    public static final String COL_STATUS = "rentalStatus"; // ENUM('Ongoing', 'Returned', 'Late') DEFAULT 'Ongoing'.
    
    public RentalDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Inserts new rental record that refences both user account and their chosen movie/s.
    public int insertRental(Rental rental) {
        String sql = 
            "INSERT INTO " + TABLE_RENTALS + 
            " (" + COL_ACCOUNT_ID + ", " + COL_MOVIE_ID + ", " + 
            COL_RENTAL_DATE + ", " + COL_RETURN_DATE + ", " + COL_RENTAL_COST + 
            ") VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, rental.getAccountID());
            pst.setInt(2, rental.getMovieID());
            pst.setTimestamp(3, rental.getRentalDate());
            pst.setTimestamp(4, rental.getReturnDate());
            pst.setDouble(5, rental.getRentalCost()); // NEW: set rentalCost

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Rental insertion failed:\n" + e.getMessage());
        }
        return -1; // Insert unsuccessful
    }
    
    // Gets all the rental history of user; use ActiveSession.getGoggedInAccountID as reference.
    public List<Rental> getRentalLogByAccount(int accountID) {
        List<Rental> rentalLogs = new ArrayList<>();
        
        // Used joins for getting other values from another table; used the FK for reference (unique thats common to both table).
        String sql = 
                "SELECT movie.title, rental.rentalDate, rental.returnDate, " + 
                "rental.rentalStatus, payment.paymentStatus " +
                "FROM tblRentals rental " +
                "JOIN tblMovies movie ON rental.movieID = movie.movieID " +
                "LEFT JOIN tblPayments payment ON rental.rentalID = payment.rentalID " +
                "WHERE rental.accountID = ? " +
                "ORDER BY rental.rentalDate DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rental rental = new Rental(
                    rs.getString("title"),
                    rs.getTimestamp("rentalDate"),
                    rs.getTimestamp("returnDate"),
                    rs.getString("rentalStatus"),
                    rs.getString("paymentStatus")
                );
                rentalLogs.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Retrieving rental history failed:\n" + e.getMessage());
        }
        return rentalLogs;
    }
}