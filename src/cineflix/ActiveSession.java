package cineflix;

// Admin account isn't binded by this class
// This class will track the user's details/personal info.
// All collected info. will be used as reference on updating the records in each table
public class ActiveSession {
    // Store account infos.
    public static int loggedInAccountID;    // From tblAccounts (id).
    public static int loggedInfoID;         // From tblCustomers (id).
    public static String loggedInUsername;  // Username for display and tracking.
    public static String role;              // 'User' or 'Admin'.

    // Clears session data after logout.
    public static void clearSession() {
        loggedInAccountID = 0;
        loggedInfoID = 0;
        loggedInUsername = null;
        role = null;
    }
}