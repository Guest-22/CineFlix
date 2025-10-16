package cineflix;

// Admin account isn't binded by this class
// This class will track the user's details/personal info.
// All collected info. will be used as reference on updating the records in each table
public class ActiveSession {
    // Store account infos.
    public static int loggedInUserID;       // From tblAccounts (id).
    public static int loggedInCustomerID;   // From tblCustomers (id).
    public static String loggedInUsername;  // For display and tracking.
    public static String role;              // 'User' or 'Admin'.

    // Clears session data after logout.
    public static void clearSession() {
        loggedInUserID = 0;
        loggedInCustomerID = 0;
        loggedInUsername = null;
        role = null;
    }
}