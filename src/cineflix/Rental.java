package cineflix;

import java.sql.Timestamp;

public class Rental {
    private int rentalID;
    private int accountID;
    private int movieID;
    private Timestamp rentalDate;
    private Timestamp returnDate;
    private String rentalStatus;

    // Constructors
    public Rental() {}

    // Constructor thae exlude cols w/ default value.
    public Rental(int accountID, int movieID, Timestamp rentalDate, Timestamp returnDate) {
        this.accountID = accountID;
        this.movieID = movieID;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }
    
    public Rental(int accountID, int movieID, Timestamp rentalDate, Timestamp returnDate, String rentalStatus) {
        this.accountID = accountID;
        this.movieID = movieID;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
        this.rentalStatus = rentalStatus;
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
    public String getRentalStatus() {
        return rentalStatus;
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
    public void setRentalStatus(String rentalStatus) {
        this.rentalStatus = rentalStatus;
    }
}