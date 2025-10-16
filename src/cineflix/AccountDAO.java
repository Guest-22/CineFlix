package cineflix;

import java.sql.*;

public class AccountDAO {
    private Connection conn;
    
    // Finalizing Tables & Columns variables.
    private final static String TABLE_NAME = "tblAccounts";
    private final static String COL_ID = "tblAccounts";
    private final static String COL_USER = "tblAccounts";
    private final static String COL_PASSWORD = "tblAccounts";
    private final static String COL_ROLE = "tblAccounts";
    
    // Default constructor
    public AccountDAO (){}
    
    // Receives a DBConnection to enable CRUD operations on tblAccounts.
    public AccountDAO(Connection conn) {
        this.conn = conn;
    }
}