package cineflix;

import java.sql.Timestamp;

// Model for admin rental log table.
public class AdminRentalEntry {
    private int rentalID;
    private String fullName;
    private String movieTitle;
    private Timestamp rentalDate;
    private Timestamp returnDate;
    private String rentalStatus;
    private String paymentStatus;
    private double totalFee;

    public AdminRentalEntry(int rentalID, String fullName, String movieTitle,
            Timestamp rentalDate, Timestamp returnDate, String rentalStatus,
            String paymentStatus, double totalFee) {
        this.rentalID = rentalID;
        this.fullName = fullName;
        this.movieTitle = movieTitle;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalStatus = rentalStatus;
        this.paymentStatus = paymentStatus;
        this.totalFee = totalFee;
    }

    // Getter methods.
    public int getRentalID() { return rentalID; }
    public String getFullName() { return fullName; }
    public String getMovieTitle() { return movieTitle; }
    public Timestamp getRentalDate() { return rentalDate; }
    public Timestamp getReturnDate() { return returnDate; }
    public String getRentalStatus() { return rentalStatus; }
    public String getPaymentStatus() { return paymentStatus; }
    public double getTotalFee() { return totalFee; }
}
