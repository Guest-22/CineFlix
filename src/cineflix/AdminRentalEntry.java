package cineflix;

import java.sql.Timestamp;

// Model for admin rental log table.
public class AdminRentalEntry {
    private int rentalID;
    private String fullName;
    private String movieTitle;
    private Timestamp rentalDate;
    private Timestamp returnDate;
     private String rentalStage;
    private String rentalStatus;
    private String paymentStatus;
    private double totalCost;

    public AdminRentalEntry(int rentalID, String fullName, String movieTitle,
            Timestamp rentalDate, Timestamp returnDate, String rentalStage, String rentalStatus,
            String paymentStatus, double totalCost) {
        this.rentalID = rentalID;
        this.fullName = fullName;
        this.movieTitle = movieTitle;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalStage = rentalStage;
        this.rentalStatus = rentalStatus;
        this.paymentStatus = paymentStatus;
        this.totalCost = totalCost;
    }

    // Getter methods.
    public int getRentalID() { return rentalID; }
    public String getFullName() { return fullName; }
    public String getMovieTitle() { return movieTitle; }
    public Timestamp getRentalDate() { return rentalDate; }
    public Timestamp getReturnDate() { return returnDate; }
    public String getRentalStage() { return rentalStage; }
    public String getRentalStatus() { return rentalStatus; }
    public String getPaymentStatus() { return paymentStatus; }
    public double getTotalCost() { return totalCost; }
}