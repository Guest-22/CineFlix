package cineflix;

import java.sql.*;

public class RentalDAO {
    private Connection conn;
        
    public static final String TABLE_RENTALS = "tblRentals";
    public static final String COL_ID = "rentalID"; // PRIMARY KEY.
    public static final String COL_ACCOUNT_ID = "accountID"; // FK references tblAccounts.
    public static final String COL_MOVIE_ID = "movieID"; // FK references tblMovies.
    public static final String COL_RENTAL_DATE = "rentalDate"; // DATETIME.
    public static final String COL_RETURN_DATE = "returnDate"; // DATETIME.
    public static final String COL_STATUS = "rentalStatus"; // ENUM('Ongoing', 'Returned', 'Late') DEFAULT 'Ongoing'.
    
    public RentalDAO(Connection conn) {
        this.conn = conn;
    }
    
    public int insertRental(Rental rental) {
        String sql = 
                "INSERT INTO " + TABLE_RENTALS + 
                " (" + COL_ACCOUNT_ID + ", " + COL_MOVIE_ID + ", " + 
                COL_RENTAL_DATE + ", " + COL_RETURN_DATE + 
                ") VALUES (?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setInt(1, rental.getAccountID());
            pst.setInt(2, rental.getMovieID());
            pst.setTimestamp(3, rental.getRentalDate());
            pst.setTimestamp(4, rental.getReturnDate());

            pst.executeUpdate();

            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Rental insertion failed:\n" + e.getMessage());
        }
        return -1; // Insert unsuceesful.
    }
}