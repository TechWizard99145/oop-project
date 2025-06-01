import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.table.DefaultTableModel;

public class DiversionManagement extends JFrame {

    private JComboBox<String> routeIdCombo;
    private JTextField fromPlaceField, toPlaceField, startDateField, endDateField, totalKmField, statusField, descriptionField;
    private JButton saveButton, cancelButton, viewButton;

    public DiversionManagement() {
        setTitle("Traffic Monitoring System - Diversion Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(10, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        routeIdCombo = new JComboBox<>(new String[] {"Please Select", "R101", "R102", "R103"});
        fromPlaceField = new JTextField();
        toPlaceField = new JTextField();
        startDateField = new JTextField();
        endDateField = new JTextField();
        totalKmField = new JTextField();
        statusField = new JTextField();
        descriptionField = new JTextField();

        saveButton = new JButton("Save Data");
        cancelButton = new JButton("Cancel");
        viewButton = new JButton("View Saved Diversions");

        panel.add(new JLabel("Select Route ID*"));
        panel.add(routeIdCombo);

        panel.add(new JLabel("Diversion From Place*"));
        panel.add(fromPlaceField);

        panel.add(new JLabel("Diversion To Place*"));
        panel.add(toPlaceField);

        panel.add(new JLabel("From Start Date*"));
        panel.add(startDateField);

        panel.add(new JLabel("End Date*"));
        panel.add(endDateField);

        panel.add(new JLabel("Total KM*"));
        panel.add(totalKmField);

        panel.add(new JLabel("Diversion Status*"));
        panel.add(statusField);

        panel.add(new JLabel("Diversion Description*"));
        panel.add(descriptionField);

        panel.add(saveButton);
        panel.add(cancelButton);
        panel.add(viewButton);

        saveButton.addActionListener(e -> saveDiversionData());
        cancelButton.addActionListener(e -> clearFields());
        viewButton.addActionListener(e -> showSavedDiversions());

        add(panel);
    }

    private void saveDiversionData() {
        String[] data = {
                (String) routeIdCombo.getSelectedItem(),
                fromPlaceField.getText(),
                toPlaceField.getText(),
                startDateField.getText(),
                endDateField.getText(),
                totalKmField.getText(),
                statusField.getText(),
                descriptionField.getText().replace(",", ";") // Prevent breaking CSV format
        };

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("diversion_data.csv", true))) {
            bw.write(String.join(",", data));
            bw.newLine();
            JOptionPane.showMessageDialog(this, "Diversion data saved successfully!");
            clearFields();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving data: " + e.getMessage());
        }
    }

    private void clearFields() {
        routeIdCombo.setSelectedIndex(0);
        fromPlaceField.setText("");
        toPlaceField.setText("");
        startDateField.setText("");
        endDateField.setText("");
        totalKmField.setText("");
        statusField.setText("");
        descriptionField.setText("");
    }

    private void showSavedDiversions() {
        JFrame tableFrame = new JFrame("Saved Diversions");
        tableFrame.setSize(800, 400);
        tableFrame.setLocationRelativeTo(this);

        String[] columns = {"Route ID", "From", "To", "Start Date", "End Date", "KM", "Status", "Description"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable table = new JTable(model);

        try (BufferedReader br = new BufferedReader(new FileReader("diversion_data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                model.addRow(line.split(","));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "No saved diversion data found.");
        }

        tableFrame.add(new JScrollPane(table));
        tableFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DiversionManagement::new);
    }
}
