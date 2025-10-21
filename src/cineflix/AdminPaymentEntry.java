package cineflix;

import java.sql.Timestamp;

// Model for populating admin payment review table: READ ONLY.
public class AdminPaymentEntry {
    private int paymentID;
    private String fullName;
    private String movieTitle;
    private Timestamp rentalDate;
    private Timestamp returnDate;
    private String rentalStatus;
    private double amount;
    private double overdueAmount;
    private Timestamp paymentDate;
    private String paymentStatus;

    public AdminPaymentEntry(int paymentID, String fullName, String movieTitle,
            Timestamp rentalDate, Timestamp returnDate, String rentalStatus, double amount, 
            double overdueAmount, Timestamp paymentDate, String paymentStatus) {
        this.paymentID = paymentID;
        this.fullName = fullName;
        this.movieTitle = movieTitle;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalStatus = rentalStatus;
        this.amount = amount;
        this.overdueAmount = overdueAmount;
        this.paymentDate = paymentDate;
        this.paymentStatus = paymentStatus;
    }

    // Getter methods.
    public int getPaymentID() {
        return paymentID;
    }
    public String getFullName() {
        return fullName;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public Timestamp getRentalDate() {
        return rentalDate;
    }
    public Timestamp getReturnDate() {
        return returnDate;
    }
    public String getRentalStatus() {
        return rentalStatus;
    }
    public double getAmount() {
        return amount;
    }
    public double getOverdueAmount() {
        return overdueAmount;
    }
    public Timestamp getPaymentDate() {
        return paymentDate;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
}