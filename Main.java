import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Traffic System Dashboard");
        setSize(700, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));
        JButton trafficBtn = new JButton("Traffic Management System");
        JButton policeBtn = new JButton("Police Management System");
        JButton diversionBtn = new JButton("Diversion Management");
        JButton routeBtn = new JButton("Route Management");
        JButton accidentBtn = new JButton("Accident Management");
        JButton typeBtn = new JButton("Type Management");
        trafficBtn.addActionListener(e -> new TrafficManagementSystem().setVisible(true));
        policeBtn.addActionListener(e -> new PoliceManagementSystem().setVisible(true));
        diversionBtn.addActionListener(e -> new DiversionManagement().setVisible(true));
        routeBtn.addActionListener(e -> new RouteManagement().setVisible(true));
        accidentBtn.addActionListener(e -> new AccidentManagement().setVisible(true));
        typeBtn.addActionListener(e -> new TypeManagement().setVisible(true));
        add(trafficBtn);
        add(policeBtn);
        add(diversionBtn);
        add(routeBtn);
        add(accidentBtn);
        add(typeBtn);
    }
    public static void main(String[] args) {
        // Show splash screen for 5 seconds (blocking)
        SplashScreen splash = new SplashScreen();
        splash.showSplash(5000); // 5 seconds

        // After splash, open GUI
        SwingUtilities.invokeLater(() -> {
            // Optional traffic light
            new Main().setVisible(true);         // Main dashboard
        });
    }
}
