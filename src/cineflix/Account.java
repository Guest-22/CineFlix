package cineflix;

// Account Model.
public class Account {
    private int accountID; // PK.
    private String username, password, role; // Other details.
    
    // Empty constructor (for inserts or updates).
    public Account() {}
    
    public Account(int accountID, String username, String password, String role) {
        this.accountID = accountID;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getter method.
    public int getAccountID() {
        return accountID;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRole() {
        return role;
    }
    
    // Setter method.
    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(String role) {
        this.role = role;
    }
}