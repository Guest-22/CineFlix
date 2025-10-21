package cineflix;

import java.sql.Timestamp;

public class Rental {
    private int rentalID;
    private int accountID;
    private int movieID;
    private Timestamp rentalDate;
    private Timestamp returnDate;
    private double rentalCost; // NEW: total rental cost based on weeks × pricePerWeek.
    private String rentalStatus;

    // Additional variable to populate user rental history/log.
    private String movieTitle; // from tblMovies.
    private String paymentStatus; // from tblPayments.
    
    // Constructors
    public Rental() {}
    
    // Constructor that exclude cols w/ default value.
    public Rental(int accountID, int movieID, Timestamp rentalDate, Timestamp returnDate, double rentalCost) {
        this.accountID = accountID;
        this.movieID = movieID;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalCost = rentalCost;   
    }

    // Constructor for user payments table.
    public Rental(String movieTitle, Timestamp rentalDate, Timestamp returnDate, String rentalStatus, String paymentStatus) {
        this.movieTitle = movieTitle;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalStatus = rentalStatus;
        this.paymentStatus = paymentStatus;
    }

    // Getter method.
    public int getRentalID() {
        return rentalID;
    }
    public int getAccountID() {
        return accountID;
    }
    public int getMovieID() {
        return movieID;
    }
    public Timestamp getRentalDate() {
        return rentalDate;
    }
    public Timestamp getReturnDate() {
        return returnDate;
    }
    public double getRentalCost() {
        return rentalCost;
    }
    public String getRentalStatus() {
        return rentalStatus;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    
    
    // Setter method.
    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }
    public void setRentalDate(Timestamp rentalDate) {
        this.rentalDate = rentalDate;
    }
    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }
    public void setRentalCost(double rentalCost) {
        this.rentalCost = rentalCost;
    }
    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}