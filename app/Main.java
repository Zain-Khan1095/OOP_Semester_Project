package app;
import javax.swing.UIManager;
import db.DatabaseHandler;
import view.MainDashboard;
import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
        //Data System
        DatabaseHandler.setupDatabase();
        
    
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                MainDashboard dashboard = new MainDashboard();
                dashboard.setVisible(true);
                
                System.out.println("System launched successfully.");
            } catch (Exception e) {
                System.err.println("Launch Error: " + e.getMessage());
            }
        });
    }
}
