import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TrafficManagementSystem extends JFrame {

    private JPanel mainPanel;
    private JComboBox<String> routeComboBox;
    private JComboBox<String> policeComboBox;
    private JTextField titleField;
    private JTextField typeField;
    private JTextField dateField;
    private JTextField statusField;
    private JTextField locationField;
    private JTextField descriptionField;
    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JButton saveButton;
    private JButton cancelButton;

    public TrafficManagementSystem() {
        setTitle("Traffic Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        addListeners();

        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(9, 2, 5, 5));

        // Initialize fields
        routeComboBox = new JComboBox<>(new String[]{"Route A", "Route B", "Route C"});
        policeComboBox = new JComboBox<>(new String[]{"Officer 1", "Officer 2", "Officer 3"});
        titleField = new JTextField();
        typeField = new JTextField();
        dateField = new JTextField();
        statusField = new JTextField();
        locationField = new JTextField();
        descriptionField = new JTextField();

        // Add form inputs
        formPanel.add(new JLabel("Route:")); formPanel.add(routeComboBox);
        formPanel.add(new JLabel("Police:")); formPanel.add(policeComboBox);
        formPanel.add(new JLabel("Title:")); formPanel.add(titleField);
        formPanel.add(new JLabel("Type:")); formPanel.add(typeField);
        formPanel.add(new JLabel("Date:")); formPanel.add(dateField);
        formPanel.add(new JLabel("Status:")); formPanel.add(statusField);
        formPanel.add(new JLabel("Location:")); formPanel.add(locationField);
        formPanel.add(new JLabel("Description:")); formPanel.add(descriptionField);

        // Buttons
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        formPanel.add(saveButton);
        formPanel.add(cancelButton);

        // Table setup
        String[] columns = {"Route", "Police", "Title", "Type", "Date", "Status", "Location", "Description"};
        tableModel = new DefaultTableModel(columns, 0);
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        // Layout
        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        loadFromFile(); // Load previously saved data

        add(mainPanel);
    }

    private void addListeners() {
        saveButton.addActionListener(e -> {
            saveToFile();
            JOptionPane.showMessageDialog(this, "Data Saved Successfully!");
        });

        cancelButton.addActionListener(e -> resetForm());
    }

    private void resetForm() {
        routeComboBox.setSelectedIndex(0);
        policeComboBox.setSelectedIndex(0);
        titleField.setText("");
        typeField.setText("");
        dateField.setText("");
        statusField.setText("");
        locationField.setText("");
        descriptionField.setText("");
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("traffic_data.csv", true))) {
            String[] data = {
                    (String) routeComboBox.getSelectedItem(),
                    (String) policeComboBox.getSelectedItem(),
                    titleField.getText(),
                    typeField.getText(),
                    dateField.getText(),
                    statusField.getText(),
                    locationField.getText(),
                    descriptionField.getText()
            };
            bw.write(String.join(",", data));
            bw.newLine();
            tableModel.addRow(data); // Add to table
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("traffic_data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                tableModel.addRow(line.split(","));
            }
        } catch (IOException e) {
            // File might not exist on first run
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TrafficManagementSystem::new);
    }
}

