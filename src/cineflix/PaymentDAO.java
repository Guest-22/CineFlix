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
}