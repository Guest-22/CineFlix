package cineflix;

import java.sql.Timestamp;

public class Payment {
    private int paymentID; // PRIMARY KEY
    private int rentalID; // FOREIGN KEY references tblRentals (includes accountID & movieID)
    private double amount;
    private double overdueAmount; // Default 0.00.
    private Timestamp paymentDate; // Default NULL.
    private String paymentStatus; // Defaullt 'Pending'.

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
}
