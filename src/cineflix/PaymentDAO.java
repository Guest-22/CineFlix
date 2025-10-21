package cineflix;

import java.sql.*;
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
            e.printStackTrace();
            Message.error("Payment failed:\n" + e.getMessage());
        }
        return false;
    }
    
    // For populating user my payments table.
    public List<Payment> getPaymentRecordsByAccountID(int accountID) {
        List<Payment> records = new ArrayList<>();

        String sql = 
            "SELECT rental.rentalID, movie.title, rental.rentalDate, rental.returnDate, rental.rentalcost, " +
            "rental.rentalStatus, payment.paymentStatus, payment.amount, payment.overdueAmount " +
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
                    rs.getString("rentalStatus"),
                    rs.getString("paymentStatus"),
                    rs.getBigDecimal("amount"),
                    rs.getBigDecimal("overdueAmount")
                );
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Failed to load payment records:\n" + e.getMessage());
        }
        return records;
    }
    
    // For populating admin payment review table.
    public List<AdminPaymentEntry> getAdminPaymentReview() {
        List<AdminPaymentEntry> payments = new ArrayList<>();

        String sql = 
            "SELECT p.paymentID, pi.fullName, m.title AS movieTitle, " +
            "r.rentalDate, r.returnDate, r.rentalStatus, " +
            "p.amount, p.overdueAmount, p.paymentDate, p.paymentStatus " +
            "FROM tblPayments p " +
            "JOIN tblRentals r ON p.rentalID = r.rentalID " +
            "JOIN tblAccounts a ON r.accountID = a.accountID " +
            "JOIN tblPersonalInfo pi ON a.accountID = pi.accountID " +
            "JOIN tblMovies m ON r.movieID = m.movieID " +
            "ORDER BY p.paymentDate DESC";

        try (PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                AdminPaymentEntry entry = new AdminPaymentEntry(
                    rs.getInt("paymentID"),
                    rs.getString("fullName"),
                    rs.getString("movieTitle"),
                    rs.getTimestamp("rentalDate"),
                    rs.getTimestamp("returnDate"),
                    rs.getString("rentalStatus"),
                    rs.getDouble("amount"),
                    rs.getDouble("overdueAmount"),
                    rs.getTimestamp("paymentDate"),
                    rs.getString("paymentStatus")
                );
                payments.add(entry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Failed to retrieve admin payment review:\n" + e.getMessage());
        }
        return payments;
    }
}