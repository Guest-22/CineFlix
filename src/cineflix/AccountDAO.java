package cineflix;

import java.sql.*;

public class AccountDAO {
    private Connection conn;
    
    // Database variables.
    private final static String TABLE_ACCOUNT = "tblAccounts";
    private final static String COL_ID = "accountID"; // PRIMARY KEY.
    private final static String COL_USERNAME = "username"; // UNIQUE.
    private final static String COL_PASSWORD = "password";
    private final static String COL_ROLE = "role"; // ENUM ('User,'Admin') DEFAULT 'User'.
    
    // Default constructor.
    public AccountDAO (){}
    
    // Receives a DBConnection to enable CRUD operations on tblAccounts.
    public AccountDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Checks if a username already exists in tblAccounts.
    public boolean isUsernameExist(String username) {
        String sql = 
                "SELECT " + COL_ID + " FROM " + TABLE_ACCOUNT + 
                " WHERE " + COL_USERNAME + " = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // returns true if the username exists.
        } catch (SQLException e) {
            
            Message.error("Username validation failed:\n" + e.getMessage());
        }
        return false; // Error or not found.
    }

    // Inserts a new account with default role 'User'; returns the generated accountID if successful, else -1.
    public int insertAccount(String username, String password) {
        // Insert query; creates a new account; automatically assigns user as default role.
        String sql = 
                "INSERT INTO " + TABLE_ACCOUNT + 
                "(" + COL_USERNAME + ", " + COL_PASSWORD + ", " + COL_ROLE + ") " + 
                "VALUES (?, ?, 'User')";
        
        try (
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys(); // Gets the PK.
            if (rs.next()) {
                return rs.getInt(1); // Returns the PK (to be stored for reference).
            }
        } catch (SQLException e) {
            
            Message.error("Account insertion failed:\n" + e.getMessage());
        }
        return -1; // Insertion failed.
    }

    // Checks existing credentials; returns true if username and password match a certain record inside DB.
    public boolean verifyAccount(String username, String password) {
        // Read query; looks for an existing account inside DB.
        String sql = 
                "SELECT * FROM " + TABLE_ACCOUNT + 
                " WHERE " + COL_USERNAME + " = ?" + " AND " + COL_PASSWORD + " = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // return true if match has been found.
        } catch (SQLException e) {
            
            Message.error("Verify account failed:\n" + e.getMessage());
        }
        return false; // Else, return false; account not found.
    }

    // Get role of a user by username; returns role as a String or null if not found.
    public String getRole(String username) {
        String sql = 
                "SELECT " + COL_ROLE + " FROM " + TABLE_ACCOUNT + 
                " WHERE " + COL_USERNAME + " = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString(COL_ROLE); // Returns role if found.
            }
        } catch (SQLException e) {
            
            Message.error("Get role failed:\n" + e.getMessage());
        }
        return null; // Not found/error.
    }
    
    // Gets accountID by username and password (used after login).
    public int getAccountID(String username, String password) {
        String sql = "SELECT " + COL_ID + " FROM " + TABLE_ACCOUNT +
                     " WHERE " + COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(COL_ID); // Return acc ID if found.
            }
        } catch (SQLException e) {
            
            Message.error("Retrieve account ID failed:\n" + e.getMessage());
        }
        return -1; // Not found.
    }
    
    // Gets the password via accountID (FK in tblPersonalInfo).
    public String getPasswordByAccountID(int accountID) {
        String sql = 
                "SELECT " + COL_PASSWORD + " FROM " + TABLE_ACCOUNT +
                " WHERE " + COL_ID + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        } catch (SQLException e) {
            
            Message.error("Password retrieval failed:\n" + e.getMessage());
        }
        return null;
    }
    
    // Update account details; role not included.
    public boolean updateAccount(int accountID, String username, String password) {
        String sql = 
                "UPDATE " + TABLE_ACCOUNT + 
                " SET " + COL_USERNAME + " = ?, " + COL_PASSWORD + " = ? "
                + "WHERE " + COL_ID + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setInt(3, accountID); // WHERE clause target; update this specific ID.
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            
            Message.error("Account update failed:\n" + e.getMessage());
        }
        return false;
    }
    
    // Deletes account data.
    public boolean deleteAccount(int accountID) {
        String sql = 
                "DELETE FROM " + TABLE_ACCOUNT + 
                " WHERE " + COL_ID + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accountID); // Target row
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            
            Message.error("Account deletion failed:\n" + e.getMessage());
        }
        return false;
    }
    
    // Returns the total number of accounts created in tblAccounts.
    public int getAccountTotalCount(String role) {
        String sql = 
                "SELECT COUNT(*) FROM " + TABLE_ACCOUNT + 
                " WHERE " + COL_ROLE + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            Message.error("Error counting accounts by role:\n" + e.getMessage());
        }
        return 0;
    }
}