import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

public class RouteManagement extends JFrame {

    private JComboBox<String> routeTypeCombo;
    private JTextField vehicleField, routeNameField, startPlaceField, endPlaceField;
    private JTextField totalKmField, routeStatusField, descriptionField;

    public RouteManagement() {
        setTitle("Traffic Monitoring System - Route Management");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initUI();
        setVisible(true);
    }

    private void initUI() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Route Management"));

        // Route Type ComboBox
        panel.add(new JLabel("Select Route Type*"));
        routeTypeCombo = new JComboBox<>(new String[]{"State Highway", "National Highway", "Rural Road"});
        panel.add(routeTypeCombo);

        // Route Name
        panel.add(new JLabel("Route Name*"));
        routeNameField = new JTextField();
        panel.add(routeNameField);

        // Allowed Vehicle
        panel.add(new JLabel("Route Allowed For Vehicle*"));
        vehicleField = new JTextField();
        panel.add(vehicleField);

        // Starting Place
        panel.add(new JLabel("From Starting Place*"));
        startPlaceField = new JTextField();
        panel.add(startPlaceField);

        // Ending Place
        panel.add(new JLabel("Ending Place*"));
        endPlaceField = new JTextField();
        panel.add(endPlaceField);

        // Total KM
        panel.add(new JLabel("Total KM*"));
        totalKmField = new JTextField();
        panel.add(totalKmField);

        // Route Status
        panel.add(new JLabel("Route Status*"));
        routeStatusField = new JTextField();
        panel.add(routeStatusField);

        // Route Description
        panel.add(new JLabel("Route Description*"));
        descriptionField = new JTextField();
        panel.add(descriptionField);

        // Buttons
        JButton saveButton = new JButton("Save Data");
        JButton cancelButton = new JButton("Cancel");
        JButton viewButton = new JButton("View Saved Routes");

        saveButton.addActionListener(new SaveButtonListener());
        cancelButton.addActionListener(e -> clearFields());
        viewButton.addActionListener(e -> showSavedRoutes());

        panel.add(saveButton);
        panel.add(cancelButton);
        panel.add(viewButton);

        add(panel);
    }

    private class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String[] data = {
                    (String) routeTypeCombo.getSelectedItem(),
                    routeNameField.getText(),
                    vehicleField.getText(),
                    startPlaceField.getText(),
                    endPlaceField.getText(),
                    totalKmField.getText(),
                    routeStatusField.getText(),
                    descriptionField.getText().replace(",", ";") // Avoid breaking CSV
            };

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("route_data.csv", true))) {
                writer.write(String.join(",", data));
                writer.newLine();
                JOptionPane.showMessageDialog(null, "Route data saved successfully!");
                clearFields();
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error saving data: " + ex.getMessage());
            }
        }
    }

    private void clearFields() {
        routeTypeCombo.setSelectedIndex(0);
        routeNameField.setText("");
        vehicleField.setText("");
        startPlaceField.setText("");
        endPlaceField.setText("");
        totalKmField.setText("");
        routeStatusField.setText("");
        descriptionField.setText("");
    }

    private void showSavedRoutes() {
        JFrame tableFrame = new JFrame("Saved Route Data");
        tableFrame.setSize(900, 400);
        tableFrame.setLocationRelativeTo(this);

        String[] columns = {"Route Type", "Route Name", "Allowed Vehicle", "From", "To", "KM", "Status", "Description"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        try (BufferedReader reader = new BufferedReader(new FileReader("route_data.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                model.addRow(line.split(","));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No saved route data found.");
        }

        tableFrame.add(new JScrollPane(table));
        tableFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RouteManagement::new);
    }
}
