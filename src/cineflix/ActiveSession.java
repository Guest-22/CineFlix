package cineflix;

// Active session tracking.
// All collected infos will serve as a reference to cater the current user.
public class ActiveSession {
    // Store account infos.
    public static int loggedInAccountID;    // 'ID' from tblAccounts (PK); Used fpr accessing tblPersonalInfo.
    public static String loggedInUsername;  // Username for display and tracking.
    public static String role;              // 'User' or 'Admin'.

    // Clears session's data after logout.
    public static void clearSession() {
        loggedInAccountID = 0;
        loggedInUsername = "";
        role = null;
    }
}