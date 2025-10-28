package cineflix;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {
    private Connection conn;
        
    public static final String TABLE_RENTALS = "tblRentals"; // Table name.
    public static final String TABLE_PAYMENTS = "tblPayments"; // From tblPayments.
    public static final String COL_ID = "rentalID"; // PRIMARY KEY.
    public static final String COL_ACCOUNT_ID = "accountID"; // FK references tblAccounts.
    public static final String COL_MOVIE_ID = "movieID"; // FK references tblMovies.
    public static final String COL_RENTAL_DATE = "rentalDate"; // DATETIME.
    public static final String COL_RETURN_DATE = "returnDate"; // DATETIME.
    public static final String COL_RENTAL_COST = "rentalCost"; // total cost of the rent (priceperweek x rentedweekduration).
    public static final String COL_RENTAL_STAGE = "rentalStage"; // For admin processes (Requested, Approved, Rejected).
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
    
    // Gets all the rental history of user; use ActiveSession.getLoggedInAccountID as reference.
    public List<Rental> getUserRentalHistory(int accountID) {
        List<Rental> history = new ArrayList<>();

        String sql =
            "SELECT r.rentalID, m.title AS movieTitle, r.rentalDate, r.returnDate, " +
            "r.rentalCost, r.rentalStage, r.rentalStatus, " +
            "p.amount AS amountPaid, " +
            "p.paymentStatus " +
            "FROM tblRentals r " +
            "JOIN tblMovies m ON r.movieID = m.movieID " +
            "LEFT JOIN tblPayments p ON r.rentalID = p.rentalID " +
            "WHERE r.accountID = ? " +
            "ORDER BY r.rentalDate DESC";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, accountID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                double rentalCost = rs.getDouble("rentalCost");
                double amountPaid = rs.getDouble("amountPaid");
                double remainingBalance = rentalCost - amountPaid;

                Rental rental = new Rental(
                    rs.getInt("rentalID"),
                    accountID,
                    0, // movieID not needed for display, can be 0 or skipped if constructor is overloaded
                    rs.getTimestamp("rentalDate"),
                    rs.getTimestamp("returnDate"),
                    rentalCost,
                    amountPaid,
                    remainingBalance,
                    rs.getString("rentalStage"),
                    rs.getString("rentalStatus"),
                    rs.getString("movieTitle"),
                    rs.getString("paymentStatus")
                );

                history.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Error retrieving rental history:\n" + e.getMessage());
        }

        return history;
    }
    
    // For populating admin rental logs table.
    public List<AdminRentalEntry> getAdminRentalLogs() {
        List<AdminRentalEntry> logs = new ArrayList<>();

        String sql = 
            "SELECT r.rentalID, pi.fullName, m.title AS movieTitle, " +
            "r.rentalDate, r.returnDate, r.rentalStage, r.rentalStatus, " +
            "p.paymentStatus, (r.rentalCost + IFNULL(p.overdueAmount, 0)) AS totalFee " +
            "FROM tblRentals r " +
            "JOIN tblAccounts a ON r.accountID = a.accountID " +
            "JOIN tblPersonalInfo pi ON a.accountID = pi.accountID " +
            "JOIN tblMovies m ON r.movieID = m.movieID " +
            "LEFT JOIN tblPayments p ON r.rentalID = p.rentalID " +
            "ORDER BY r.rentalDate ASC";

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                AdminRentalEntry entry = new AdminRentalEntry(
                    rs.getInt("rentalID"),
                    rs.getString("fullName"),
                    rs.getString("movieTitle"),
                    rs.getTimestamp("rentalDate"),
                    rs.getTimestamp("returnDate"),
                    rs.getString("rentalStage"),    
                    rs.getString("rentalStatus"),
                    rs.getString("paymentStatus"),
                    rs.getDouble("totalFee")
                );
                logs.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Failed to retrieve admin rental logs:\n" + e.getMessage());
        }
        return logs;
    }
    
    // For admin rental logs approve and reject button; updates rental stage for validation and order confirmation.
    public boolean updateRentalStage(int rentalID, String newStage) {
        String sql = 
                "UPDATE " + TABLE_RENTALS + 
                " SET " + COL_RENTAL_STAGE + " = ? " + 
                "WHERE " + COL_ID + " = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, newStage);
            pst.setInt(2, rentalID);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Error updating rental stage:\n" + e.getMessage());
            return false;
        }
    }
    
    // Updates rental status from tblRentals (AdminRentalLogs).
    public boolean updateRentalStatus(int rentalID, String newStatus) {
        String sql = 
                "UPDATE " + TABLE_RENTALS + 
                " SET " + COL_STATUS + " = ? WHERE " + COL_ID + " = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, newStatus);
            pst.setInt(2, rentalID);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Error updating rental status:\n" + e.getMessage());
            return false;
        }
    }
    
    // For admin rental logs delete button; delete existing record if the rentalStage = Requested or Rejected.
    public boolean deleteRental(int rentalID) {
        String deletePayments = 
                "DELETE FROM " + TABLE_PAYMENTS + " WHERE " + COL_ID + " = ?";
        
        String deleteRental = 
                "DELETE FROM " + TABLE_RENTALS + " WHERE " + COL_ID + " = ?";

        try {
            conn.setAutoCommit(false); // Begin transaction

            try (PreparedStatement pst1 = conn.prepareStatement(deletePayments);
                 PreparedStatement pst2 = conn.prepareStatement(deleteRental)) {

                pst1.setInt(1, rentalID);
                pst1.executeUpdate();

                pst2.setInt(1, rentalID);
                pst2.executeUpdate();

                conn.commit();
                conn.setAutoCommit(true); // Restore auto-commit
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Error deleting rental:\n" + e.getMessage());
        }
        return false;
    }
}