package cineflix;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class PersonalInfoDAO {
    private Connection conn;

    // Database variables.
    private final static String TABLE_INFO = "tblPersonalInfo";
    private final static String COL_ID = "infoID"; // PRIMARY KEY.
    private final static String COL_ACCOUNT_ID = "accountID"; // FOREIGN KEY
    private final static String COL_FULLNAME = "fullName";
    private final static String COL_SEX = "sex"; // ENUM ('Male,'Female').
    private final static String COL_EMAIL = "email"; // UNIQUE.
    private final static String COL_CONTACT = "contactNum";
    private final static String COL_ADDRESS = "address";
    
    // Empty constructor.
    public PersonalInfoDAO(){}
    
    // Receives a DBConnection to enable CRUD operations on tblPersonalInfo.
    public PersonalInfoDAO(Connection conn) {
        this.conn = conn;
    }
    
    // Validates existing emails.
    public boolean isEmailExist(String email) {
        String sql = 
                "SELECT " + COL_EMAIL + " FROM " + TABLE_INFO + 
                " WHERE " + COL_EMAIL + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // Returns true if email already exists
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Email checker failed:\n" + e.getMessage());
        }
        return false;
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
            Message.error("Personal info insertion failed:\n" + e.getMessage());
        }
        return false; // Insert failed; return false.
    }
    
    // Retrieve accountID (FK) by referencing info ID (PK).
    public int getAccountIDByInfoID(int infoID) {
        String sql = 
                "SELECT " + COL_ACCOUNT_ID +" FROM " + TABLE_INFO + " "
                + "WHERE " + COL_ID + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, infoID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("accountID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Account ID retrieval failed:\n" + e.getMessage());
        }
        return -1;
    }

    
    // Returns a list of data from tblPersonalInfo with username.
    public List<PersonalInfo> getAllInfoWithUsername() {
        List<PersonalInfo> list = new ArrayList<>();
        String sql = 
                "SELECT p.infoID, p.accountID, p.fullName, p.sex, p.email, p.contactNum, p.address, a.username " +
                "FROM tblPersonalInfo p JOIN tblAccounts a ON p.accountID = a.accountID";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                PersonalInfo info = new PersonalInfo();
                info.setInfoID(rs.getInt("infoID"));
                info.setAccountID(rs.getInt("accountID"));
                info.setFullName(rs.getString("fullName"));
                info.setSex(rs.getString("sex"));
                info.setEmail(rs.getString("email"));
                info.setContactNum(rs.getString("contactNum"));
                info.setAddress(rs.getString("address"));
                info.setUsername(rs.getString("username"));
                list.add(info);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Retrieval of all personal infos & username failed:\n" + e.getMessage());
        }
        return list;
    }
    
    // Updates existing info data.
    public boolean updateInfo(PersonalInfo info) {
        String sql = 
                "UPDATE " + TABLE_INFO + 
                " SET " + COL_FULLNAME + " = ?, " + COL_SEX + " = ?, " + COL_EMAIL + 
                " = ?, " + COL_CONTACT + " = ?, " + COL_ADDRESS + " = ? WHERE " + COL_ID + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, info.getFullName());
            stmt.setString(2, info.getSex());
            stmt.setString(3, info.getEmail());
            stmt.setString(4, info.getContactNum());
            stmt.setString(5, info.getAddress());
            stmt.setInt(6, info.getInfoID()); // WHERE clause target; update this specific ID.
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Personal info update failed:\n" + e.getMessage());
        }
        return false;
    }
    
    // Delte personal info data.
    public boolean deleteInfo(int infoID) {
        String sql = 
                "DELETE FROM " + TABLE_INFO + 
                " WHERE " + COL_ID + " = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, infoID); // Target row.
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            Message.error("Personal info deletion failed:\n" + e.getMessage());
        }
        return false;
    }
}