import javax.swing.*;
import java.awt.*;

public class SplashScreen extends JWindow {

    public SplashScreen() {
        JPanel content = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Welcome to Traffic Management System", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        content.add(label, BorderLayout.CENTER);

        content.setBackground(Color.WHITE);
        setContentPane(content);
        setSize(500, 300);
        setLocationRelativeTo(null);
    }

    public void showSplash(int durationMillis) {
        setVisible(true);
        try {
            Thread.sleep(durationMillis); // Pause for given milliseconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setVisible(false);
        dispose(); // Close splash window
    }
}

