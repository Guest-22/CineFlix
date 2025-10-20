package cineflix;

import java.sql.*;

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
}