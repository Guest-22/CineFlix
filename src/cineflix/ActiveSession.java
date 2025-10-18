package cineflix;

// Admin account isn't binded by this class
// This class will track the user's details/personal info.
// All collected info. will be used as reference on updating the records in each table
public class ActiveSession {
    // Store account infos.
    public static int loggedInAccountID;    // 'ID' from tblAccounts (PK); Used fpr accessing tblPersonalInfo.
    public static String loggedInUsername;  // Username for display and tracking.
    public static String role;              // 'User' or 'Admin'.

    // Clears session data after logout.
    public static void clearSession() {
        loggedInAccountID = 0;
        loggedInUsername = null;
        role = null;
    }
}