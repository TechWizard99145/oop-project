import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PoliceManagementSystem extends JFrame {

    private JTable dataTable;
    private DefaultTableModel tableModel;
    private JTextField nameField, feesField, contactField, imageField, specializationField;
    private JTextArea addressArea;
    private JButton saveButton, cancelButton;
    private JPanel mainPanel;

    public PoliceManagementSystem() {
        setTitle("Police Management System");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        initListeners();

        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));

        // Input Fields
        nameField = new JTextField();
        feesField = new JTextField();
        contactField = new JTextField();
        imageField = new JTextField();
        specializationField = new JTextField();
        addressArea = new JTextArea(3, 20);

        // Add fields to panel
        formPanel.add(new JLabel("Name:")); formPanel.add(nameField);
        formPanel.add(new JLabel("Fees:")); formPanel.add(feesField);
        formPanel.add(new JLabel("Contact:")); formPanel.add(contactField);
        formPanel.add(new JLabel("Image:")); formPanel.add(imageField);
        formPanel.add(new JLabel("Specialization:")); formPanel.add(specializationField);
        formPanel.add(new JLabel("Address:")); formPanel.add(new JScrollPane(addressArea));

        // Buttons
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        formPanel.add(saveButton); formPanel.add(cancelButton);

        // Table
        String[] columns = {"Name", "Fees", "Contact", "Image", "Specialization", "Address"};
        tableModel = new DefaultTableModel(columns, 0);
        dataTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(dataTable);

        // Add panels
        mainPanel.add(formPanel, BorderLayout.WEST);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        add(mainPanel);

        // Load data
        loadFromFile();
    }

    private void initListeners() {
        saveButton.addActionListener(e -> {
            saveToFile();
            JOptionPane.showMessageDialog(this, "Police record saved!");
        });

        cancelButton.addActionListener(e -> clearForm());
    }

    private void clearForm() {
        nameField.setText("");
        feesField.setText("");
        contactField.setText("");
        imageField.setText("");
        specializationField.setText("");
        addressArea.setText("");
    }

    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("police_data.csv", true))) {
            String[] data = {
                    nameField.getText(),
                    feesField.getText(),
                    contactField.getText(),
                    imageField.getText(),
                    specializationField.getText(),
                    addressArea.getText().replace("\n", " ")
            };
            bw.write(String.join(",", data));
            bw.newLine();
            tableModel.addRow(data);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadFromFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("police_data.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                tableModel.addRow(line.split(","));
            }
        } catch (IOException e) {
            // Ignore if file does not exist
        }
    }

    // For testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(PoliceManagementSystem::new);
    }
}
