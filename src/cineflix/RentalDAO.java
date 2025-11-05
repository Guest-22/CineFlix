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
            "LEFT JOIN tblPayments p ON r.rentalID = p.rentalID";

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
            
            Message.error("Error updating rental stage:\n" + e.getMessage());
            return false;
        }
    }
    
    // Retrieves rentalID based on paymentID; used for cascading deletion from payment to rental.
    public int getRentalIDByPaymentID(int paymentID) {
        String sql = 
                "SELECT r.rentalID " +
                "FROM " + TABLE_PAYMENTS + " p " +
                "JOIN " + TABLE_RENTALS +" r ON p.rentalID = r.rentalID " +
                "WHERE p.paymentID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("rentalID");
            }
        } catch (Exception e) {
            Message.error("Retrieval of Rental ID via Payment ID failed:\n" + e.getMessage());
        }
        return -1; // Rental ID not found.
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
            Message.error("Error deleting rental:\n" + e.getMessage());
        }
        return false;
    }
    
    public int getRentalTotalCount() {
        String sql = 
                "SELECT COUNT(*) FROM " + TABLE_RENTALS;
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting rentals:\n" + e.getMessage());
        }
        return 0;
    }
    
    public int getStatusCount(String status) {
        String sql = 
                "SELECT COUNT(*) FROM " + TABLE_RENTALS + 
                " WHERE " + COL_STATUS + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting rentals by status:\n" + e.getMessage());
        }
        return 0;
    }
    
    public int getStageCount(String stage) {
        String sql = 
                "SELECT COUNT(*) FROM " + TABLE_RENTALS + 
                " WHERE " + COL_RENTAL_STAGE + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, stage);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting rentals by stage:\n" + e.getMessage());
        }
        return 0;
    }

    // Get all rental count based on accountID for user dashboard.
    public int getRentalCountByAccountID(int accountID) {
        String sql = 
                "SELECT COUNT(*) FROM " + TABLE_RENTALS + " WHERE accountID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting rentals:\n" + e.getMessage());
        }
        return 0;
    }

    // Get rental status count for user dashboard.
    public int getRentalStatusByAccountID(int accountID, String status) {
        String sql = 
                "SELECT COUNT(*) FROM " + TABLE_RENTALS + 
                     " WHERE accountID = ? AND " + COL_STATUS + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            stmt.setString(2, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting rentals by status:\n" + e.getMessage());
        }
        return 0;
    }
    
    // Get rental stage count for user dashboard.
    public int getRentalStageByAccountID(int accountID, String stage) {
        String sql = 
            "SELECT COUNT(*) FROM " + TABLE_RENTALS + 
            " WHERE accountID = ? AND " + COL_RENTAL_STAGE + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            stmt.setString(2, stage);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting rentals by stage:\n" + e.getMessage());
        }
        return 0;
    }
    
    // Gets the recent rental record by accountID with an expected limits.
    public List<Rental> getRecentRentalsByAccountID(int accountID, int limit) {
        List<Rental> rentals = new ArrayList<>();
        String sql = 
            "SELECT m.title, r.rentalDate, r.returnDate, r.rentalStatus, r.rentalCost " +
            "FROM " + TABLE_RENTALS + " r " +
            "JOIN tblMovies m ON r.movieID = m.movieID " +
            "WHERE r.accountID = ? " +
            "ORDER BY r.rentalDate DESC " +
            "LIMIT ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Rental rental = new Rental();
                rental.setMovieTitle(rs.getString("title"));
                rental.setRentalDate(rs.getTimestamp("rentalDate"));
                rental.setReturnDate(rs.getTimestamp("returnDate"));
                rental.setRentalStatus(rs.getString("rentalStatus"));
                rental.setRentalCost(rs.getDouble("rentalCost"));
                rentals.add(rental);
            }
        } catch (SQLException e) {
            Message.error("Error retrieving recent rentals:\n" + e.getMessage());
        }
        return rentals;
    }
    
    // Retrieve todays rental count/requests.
    public int getTodayRentalCount() {
        String sql = 
                "SELECT COUNT(*) FROM " + TABLE_RENTALS + 
                " WHERE DATE(" + COL_RENTAL_DATE + ") = CURDATE()";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting today's rental requests:\n" + e.getMessage());
        }
        return 0;
    }
}