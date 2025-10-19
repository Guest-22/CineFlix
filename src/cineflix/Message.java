package cineflix;

import javax.swing.JOptionPane;

// Static methods; no instances needed. 
public class Message {
    // Shows a basic info message.
    public static void show(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    // Shows a message with custom title.
    public static void show(String message, String title) {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    // Shows an error message.
    public static void error(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    // Shows a confirmation dialog and returns true if user clicks YES.
    public static boolean confirm(String message, String title) {
        int result = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        return result == JOptionPane.YES_OPTION;
    }
}