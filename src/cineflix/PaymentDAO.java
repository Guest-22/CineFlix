package cineflix;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {
    private Connection conn;

    // Table and column constants
    public static final String TABLE_PAYMENTS = "tblPayments";
    public static final String COL_ID = "paymentID"; // PRIMARY KEY.
    public static final String COL_RENTAL_ID = "rentalID"; // FK references tblRentals.
    public static final String COL_AMOUNT = "amount"; // DEFAULT 0.00.
    public static final String COL_OVERDUE = "overdueAmount"; // DEFAULT 0.00.
    public static final String COL_DATE = "paymentDate"; // DATETIME DEFAULT NULL.
    public static final String COL_STATUS = "paymentStatus"; // ENUM('Pending','Approved') DEFAULT 'Pending'.
    
    public PaymentDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Inserts payment record with only rentalID; all other fields use defaults.
    public boolean insertPayment(int rentalID) {
        String sql = 
                "INSERT INTO " + TABLE_PAYMENTS + 
                " (" + COL_RENTAL_ID + 
                ") VALUES (?)";

        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, rentalID);
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            
            Message.error("Payment failed:\n" + e.getMessage());
        }
        return false;
    }
    
    // For populating user my payments table.
    public List<Payment> getPaymentRecordsByAccountID(int accountID) {
        List<Payment> records = new ArrayList<>();

        String sql =
            "SELECT rental.rentalID, movie.title, rental.rentalDate, rental.returnDate, rental.rentalCost, " +
            "payment.overdueAmount, payment.amount, rental.rentalStatus, payment.paymentStatus " +
            "FROM tblRentals rental " +
            "JOIN tblMovies movie ON rental.movieID = movie.movieID " +
            "LEFT JOIN tblPayments payment ON rental.rentalID = payment.rentalID " +
            "WHERE rental.accountID = ? " +
            "ORDER BY rental.rentalDate DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Payment record = new Payment(
                    rs.getInt("rentalID"),
                    rs.getString("title"),
                    rs.getDate("rentalDate").toLocalDate(),
                    rs.getDate("returnDate").toLocalDate(),
                    rs.getBigDecimal("rentalCost"),
                    rs.getBigDecimal("overdueAmount"),
                    rs.getBigDecimal("amount"),
                    rs.getString("rentalStatus"),
                    rs.getString("paymentStatus")
                );
                records.add(record);
            }
        } catch (SQLException e) {
            
            Message.error("Failed to load payment records:\n" + e.getMessage());
        }
        return records;
    }
    
    // For populating admin payment review table.
    public List<AdminPaymentEntry> getAdminPaymentLogs() {
        List<AdminPaymentEntry> logs = new ArrayList<>();

        String sql = 
                "SELECT p.paymentID, r.rentalID, pi.fullName, m.title AS movieTitle, " +
                "r.rentalDate, r.returnDate, r.rentalStage, r.rentalStatus, " +
                "p.paymentStatus, r.rentalCost AS totalCost, p.amount AS amountPaid, p.overdueAmount, p.paymentDate " +
                "FROM tblPayments p " +
                "JOIN tblRentals r ON p.rentalID = r.rentalID " +
                "JOIN tblAccounts a ON r.accountID = a.accountID " +
                "JOIN tblPersonalInfo pi ON a.accountID = pi.accountID " +
                "JOIN tblMovies m ON r.movieID = m.movieID";

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                AdminPaymentEntry entry = new AdminPaymentEntry(
                    rs.getInt("paymentID"),
                    rs.getInt("rentalID"),
                    rs.getString("fullName"),
                    rs.getString("movieTitle"),
                    rs.getTimestamp("rentalDate").toLocalDateTime(),
                    rs.getTimestamp("returnDate").toLocalDateTime(),
                    rs.getString("rentalStage"),
                    rs.getString("rentalStatus"),
                    rs.getString("paymentStatus"),
                    rs.getDouble("totalCost"),
                    rs.getDouble("amountPaid"),
                    rs.getDouble("overdueAmount"),
                    rs.getTimestamp("paymentDate") != null ? rs.getTimestamp("paymentDate").toLocalDateTime() : null
                );
                logs.add(entry);
            }

        } catch (SQLException e) {
            Message.error("Failed to load payment records:\n" + e.getMessage());
        }

        return logs;
    }
    
    // For admin rental logs confirm transaction; updates data to add an upfront payment of 25% in col amount.
    public boolean updatePaymentStatusAndAmount(int rentalID, double amount, String status) {
        String sql = 
                "UPDATE " + TABLE_PAYMENTS + 
                " SET " + COL_AMOUNT + " = ?, " + COL_STATUS + " = ?, " + COL_DATE + " = NOW() " + 
                "WHERE " + COL_RENTAL_ID + " = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDouble(1, amount);
            pst.setString(2, status);
            pst.setInt(3, rentalID);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            
            Message.error("Error updating payment status & amount:\n" + e.getMessage());
            return false;
        }
    }
    
    // For admin payment review.
    public boolean confirmPaymentTransaction(int paymentID, String status, double amountPaid, LocalDateTime paymentDate) {
        String sql = 
                "UPDATE " + TABLE_PAYMENTS + 
                " SET " + COL_STATUS + " = ?, " + COL_AMOUNT + " = ?, " + COL_DATE +" = ? " + 
                "WHERE " + COL_ID + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setDouble(2, amountPaid);
            stmt.setTimestamp(3, Timestamp.valueOf(paymentDate));
            stmt.setInt(4, paymentID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            
            Message.error("Payment transaction failed:\n" + e.getMessage());
            return false;
        }
    }
   
    // For admin payment review.
    public boolean deletePaymentTransaction(int paymentID) {
        String sql = 
                "DELETE FROM " + TABLE_PAYMENTS + " WHERE " + COL_ID + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentID);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            
            Message.error("Payment transaction deletion failed:\n" + e.getMessage());
            return false;
        }
    }

    // For admin payment review; increments movie copy automatically when payments are made w/ the movie.
    public int getMovieIDByPaymentID(int paymentID) throws SQLException {
        String sql = 
                "SELECT r.movieID " +
                "FROM " + TABLE_PAYMENTS + " p " +
                "JOIN tblRentals r ON p.rentalID = r.rentalID " +
                "WHERE p.paymentID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, paymentID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("movieID");
            }
        } catch (Exception e) {
            Message.error("Retreival of Movie ID via Payment ID failed:\n" + e.getMessage());
        }
        return -1; // Movie ID not found.
    }
    
    // Get total payment records for admin dashboard stats.
    public int getPaymentTotalCount() {
        String sql = 
                "SELECT COUNT(*) FROM " + TABLE_PAYMENTS;
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting payment records:\n" + e.getMessage());
        }
        return 0;
    }
    
    // Retrieve total revenue (sum of all amounts paid).
    public double getSumAmount() {
        String sql = 
                "SELECT SUM(" + COL_AMOUNT + ") "
                + "FROM " + TABLE_PAYMENTS;
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            Message.error("Error calculating total revenue:\n" + e.getMessage());
        }
        return 0.0;
    }
    
    // Retrieve count of selected status (i.e., Pending, Paid Upfront, or Paid Full).
    public int getPaymentStatusCountByAccountID(int accountID, String status) {
        String sql = 
            "SELECT COUNT(*) FROM " + TABLE_PAYMENTS + " p " +
            "JOIN tblRentals r ON p.rentalID = r.rentalID " +
            "WHERE r.accountID = ? AND p.paymentStatus = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            stmt.setString(2, status);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting payments by status:\n" + e.getMessage());
        }
        return 0;
    }
    
    // Updates overdue amount in tblPayments (AdminPaymentLogs).
    public boolean updateOverdueAmount(int paymentID, double newAmount) {
        String sql = 
                "UPDATE " + TABLE_PAYMENTS + 
                " SET " + COL_OVERDUE + " = ? " + 
                "WHERE " + COL_ID + " = ?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDouble(1, newAmount);
            pst.setInt(2, paymentID);
            return pst.executeUpdate() > 0;
        } catch (SQLException e) {
            Message.error("Error updating overdue amount:\n" + e.getMessage());
            return false;
        }
    }
    
    // Retrieve total amount paid by a specific user.
    public double getTotalPaidByAccountID(int accountID) {
        String sql =
            "SELECT SUM(p.amount) " +
            "FROM " + TABLE_PAYMENTS + " p " +
            "JOIN tblRentals r ON p.rentalID = r.rentalID " +
            "WHERE r.accountID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            Message.error("Error retrieving total amount paid:\n" + e.getMessage());
        }
        return 0.0;
    }

    // Retrieve total unpaid balance by user (rentalCost + overdueAmount - amount).
    public double getTotalUnpaidBalanceByAccountID(int accountID) {
        String sql =
            "SELECT SUM((r.rentalCost + p.overdueAmount) - p.amount) " +
            "FROM " + TABLE_PAYMENTS + " p " +
            "JOIN tblRentals r ON p.rentalID = r.rentalID " +
            "WHERE r.accountID = ? AND p.paymentStatus != 'Paid Full'";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            Message.error("Error retrieving unpaid balance:\n" + e.getMessage());
        }
        return 0.0;
    }

    // Retrieve total overdue charges by user.
    public double getTotalOverdueChargesByAccountID(int accountID) {
        String sql =
            "SELECT SUM(p.overdueAmount) " +
            "FROM " + TABLE_PAYMENTS + " p " +
            "JOIN tblRentals r ON p.rentalID = r.rentalID " +
            "WHERE r.accountID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            Message.error("Error retrieving overdue charges:\n" + e.getMessage());
        }
        return 0.0;
    }

    // Retrieve last payment date by user.
    public LocalDateTime getLastPaymentDateByAccountID(int accountID) {
        String sql =
            "SELECT MAX(p.paymentDate) " +
            "FROM " + TABLE_PAYMENTS + " p " + 
            " JOIN tblRentals r ON p.rentalID = r.rentalID " +
            "WHERE r.accountID = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Timestamp ts = rs.getTimestamp(1);
                return ts != null ? ts.toLocalDateTime() : null;
            }
        } catch (SQLException e) {
            Message.error("Error retrieving last payment date:\n" + e.getMessage());
        }
        return null;
    }
    
    // Gets today payment record.
    public int getTodayPaymentCount() {
        String sql = 
                "SELECT COUNT(*) FROM " + TABLE_PAYMENTS + " WHERE DATE(" + COL_DATE + ") = CURDATE()";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting today's payments:\n" + e.getMessage());
        }
        return 0;
    }
}