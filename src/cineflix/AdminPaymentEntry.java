package cineflix;

import java.time.LocalDateTime;

public class AdminPaymentEntry {
    private int paymentID;
    private int rentalID;
    private String accountName;
    private String movieTitle;
    private LocalDateTime rentalDate;
    private LocalDateTime returnDate;
    private String rentalStage;
    private String rentalStatus;
    private String paymentStatus;
    private double totalCost;
    private double amountPaid;
    private double overdueAmount;
    private LocalDateTime paymentDate;

    // Constructors
    public AdminPaymentEntry() {}

    // Model for admin payment review.
    public AdminPaymentEntry(int paymentID, int rentalID, String accountName, String movieTitle,
            LocalDateTime rentalDate, LocalDateTime returnDate, String rentalStage, String rentalStatus, 
            String paymentStatus, double totalCost, double amountPaid, double overdueAmount, LocalDateTime paymentDate) {
        this.paymentID = paymentID;
        this.rentalID = rentalID;
        this.accountName = accountName;
        this.movieTitle = movieTitle;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalStage = rentalStage;
        this.rentalStatus = rentalStatus;
        this.paymentStatus = paymentStatus;
        this.totalCost = totalCost;
        this.overdueAmount = overdueAmount;
        this.amountPaid = amountPaid;
        this.paymentDate = paymentDate;
    }

    // Getter methods.
    public int getPaymentID() {
        return paymentID;
    }
    public int getRentalID() {
        return rentalID;
    }
    public String getAccountName() {
        return accountName;
    }
    public String getMovieTitle() {
        return movieTitle;
    }
    public LocalDateTime getRentalDate() {
        return rentalDate;
    }
    public LocalDateTime getReturnDate() {
        return returnDate;
    }
    public String getRentalStage() {
        return rentalStage;
    }
    public String getRentalStatus() {
        return rentalStatus;
    }
    public String getPaymentStatus() {
        return paymentStatus;
    }
    public double getTotalCost() {
        return totalCost;
    }
    public double getAmountPaid() {
        return amountPaid;
    }
    public double getOverdueAmount() {
        return overdueAmount;
    }
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    // Setter methods.
    public void setPaymentID(int paymentID) {
        this.paymentID = paymentID;
    }
    public void setRentalID(int rentalID) {
        this.rentalID = rentalID;
    }
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    public void setRentalDate(LocalDateTime rentalDate) {
        this.rentalDate = rentalDate;
    }
    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }
    public void setRentalStage(String rentalStage) {
        this.rentalStage = rentalStage;
    }
    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }
    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
    public void setOverdueAmount(double overdueAmount) {
        this.overdueAmount = overdueAmount;
    }
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
}