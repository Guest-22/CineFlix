package cineflix;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

public class Payment {
    private int paymentID; // PRIMARY KEY
    private int rentalID; // FOREIGN KEY references tblRentals (includes accountID & movieID).
    private double amount; // Paid amount (either upfront or full).
    private double overdueAmount; // Default 0.00.
    private Timestamp paymentDate; // Default NULL.
    private String paymentStatus; // Defaullt 'Pending'.

    // Extra variable for displaying for joined queries.
    private String movieTitle; // from tblMovies 

    // Extra var for rental joined queries.
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private Double rentalCost;
    private String rentalStatus;
    
    // Constructors
    public Payment() {}
    
    // Constructor that exludes cols with default value.
    public Payment(int rentalID) {
        this.rentalID = rentalID;
    }
    
    public Payment(int rentalID, double amount) {
        this.rentalID = rentalID;
        this.amount = amount;
    }

    // Constructor for joined rental, movie, and payment data (User My Payments).
    public Payment(int rentalID, String movieTitle, LocalDate rentalDate, LocalDate returnDate,
            BigDecimal rentalCost, BigDecimal overdueAmount, BigDecimal amount,
            String rentalStatus, String paymentStatus) {
        this.rentalID = rentalID;
        this.movieTitle = movieTitle;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalCost = rentalCost.doubleValue();
        this.overdueAmount = overdueAmount.doubleValue();
        this.amount = amount.doubleValue();
        this.rentalStatus = rentalStatus;
        this.paymentStatus = paymentStatus;
    }

    public Payment(int rentalID, double amount, double overdueAmount, String paymentStatus) {
        this.rentalID = rentalID;
        this.amount = amount;
        this.overdueAmount = overdueAmount;
        this.paymentStatus = paymentStatus;
        this.paymentDate = null;
    }

    // Getter method.
    public int getPaymentID() {
        return paymentID;
    }
    public int getRentalID() {
        return rentalID;
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
    public String getMovieTitle() {
        return movieTitle;
    }
    public LocalDate getRentalDate() {
        return rentalDate;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }
    public Double getRentalCost() {
        return rentalCost;
    }
    public String getRentalStatus() {
        return rentalStatus;
    }

    // Setter method.
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    public void setOverdueAmount(double overdueAmount) {
        this.overdueAmount = overdueAmount;
    }
    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    public void setRentalDate(LocalDate rentalDate) {
        this.rentalDate = rentalDate;
    }
    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
    public void setRentalCost(Double rentalCost) {
        this.rentalCost = rentalCost;
    }
    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }
}           