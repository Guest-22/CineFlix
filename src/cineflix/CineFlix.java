package cineflix;

public class CineFlix {
    public static void main(String[] args) {
        // This video rental system is built and designed using Netbeans JFrame Builder.
        // MySQL Database has been integrated as part of the requirement.
        // Use (Ctrl + 7 shortcut) to inspect all of the variables used in this system project.
        // Shows the login JFrame by default.
        // Note: Preload the specific JFrame Dashboard below for layout testing; else use login for active session tracking of current user.
         
        // Pre-loads UI; avoid glitches.
        javax.swing.SwingUtilities.invokeLater(() -> {
            new Login().setVisible(true);    
        }); 
    }
}