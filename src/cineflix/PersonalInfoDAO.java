package cineflix;

import java.sql.*;

public class PersonalInfoDAO {
    private Connection conn;

    // Database variables.
    private final static String TABLE_INFO = "tblPersonalInfo";
    private final static String COL_ID = "infoID"; // PRIMARY KEY.
    private final static String COL_ACCOUNT_ID = "accountID"; // FOREIGN KEY
    private final static String COL_FULLNAME = "fullName";
    private final static String COL_SEX = "sex";
    private final static String COL_EMAIL = "email";
    private final static String COL_CONTACT = "contactNum";
    private final static String COL_ADDRESS = "address";
    
    // Empty constructor.
    public PersonalInfoDAO(){}
    
    // Receives a DBConnection to enable CRUD operations on tblPersonalInfo.
    public PersonalInfoDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Inserts personal info linked to an accountID.
    public boolean insertInfo(PersonalInfo info) {
        // Inserts personal info; accountID (FK) references tblAccounts.
        String sql = 
                "INSERT INTO " + TABLE_INFO + " (" + COL_ACCOUNT_ID + ", " + COL_FULLNAME +
                ", "+ COL_SEX +", "+ COL_EMAIL + ", "+ COL_CONTACT+", "+ COL_ADDRESS +") " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, info.getAccountID()); // FK from tblAccounts.
            stmt.setString(2, info.getFullName());
            stmt.setString(3, info.getSex());
            stmt.setString(4, info.getEmail());
            stmt.setString(5, info.getContactNum());
            stmt.setString(6, info.getAddress());

            return stmt.executeUpdate() > 0; // Return true if insert succeeded.
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Database error: " + e.getMessage());
        }
        return false; // Insert failed; return false.
    }
}