import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.event.*;
import java.util.ArrayList;

class AccidentReport implements Serializable {
    private String accidentDetails;
    private String medication;
    private String hospital;

    public AccidentReport(String accidentDetails, String medication, String hospital) {
        this.accidentDetails = accidentDetails;
        this.medication = medication;
        this.hospital = hospital;
    }

    public String toString() {
        return "Details: " + accidentDetails + ", Medication: " + medication + ", Hospital: " + hospital;
    }
}

public class AccidentManagement extends JFrame {

    private static final String FILE_NAME = "accident_reports.txt";

    private JTextArea detailsArea, medicationArea;
    private JComboBox<String> hospitalComboBox;
    private JLabel reportCountLabel;
    private ArrayList<AccidentReport> reports;

    public AccidentManagement() {
        setTitle("Accident Management - Traffic Monitoring System");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        reports = new ArrayList<>();

        loadReportsFromFile();  // Load existing reports at startup

        // Title Panel
        JLabel titleLabel = new JLabel("Accident Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(0, 102, 204));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setPreferredSize(new Dimension(600, 50));
        add(titleLabel, BorderLayout.NORTH);

        // Center Panel - Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Accident Details
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Accident Details:"), gbc);

        detailsArea = new JTextArea(3, 20);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(detailsArea), gbc);

        // Medication Given
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Medication Provided:"), gbc);

        medicationArea = new JTextArea(3, 20);
        gbc.gridx = 1;
        formPanel.add(new JScrollPane(medicationArea), gbc);

        // Hospital Referral
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Refer to Hospital:"), gbc);

        String[] hospitals = {"City Hospital", "Metro Care", "Green Cross", "Unity Health"};
        hospitalComboBox = new JComboBox<>(hospitals);
        gbc.gridx = 1;
        formPanel.add(hospitalComboBox, gbc);

        // Submit and Clear Buttons
        JPanel buttonPanel = new JPanel();
        JButton submitBtn = new JButton("Submit Report");
        JButton clearBtn = new JButton("Clear");

        buttonPanel.add(submitBtn);
        buttonPanel.add(clearBtn);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Bottom Panel - Report count
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        reportCountLabel = new JLabel("Total Accident Reports: " + reports.size());
        bottomPanel.add(reportCountLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        submitBtn.addActionListener(e -> submitReport());
        clearBtn.addActionListener(e -> clearForm());
    }

    private void submitReport() {
        String details = detailsArea.getText().trim();
        String medication = medicationArea.getText().trim();
        String hospital = (String) hospitalComboBox.getSelectedItem();

        if (details.isEmpty() || medication.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        AccidentReport report = new AccidentReport(details, medication, hospital);
        reports.add(report);
        reportCountLabel.setText("Total Accident Reports: " + reports.size());

        saveReportsToFile();  // Save all reports to file on every new submission

        JOptionPane.showMessageDialog(this, "Report submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    private void clearForm() {
        detailsArea.setText("");
        medicationArea.setText("");
        hospitalComboBox.setSelectedIndex(0);
    }

    // Load reports from the file into the reports list
    private void loadReportsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;  // No previous file, start fresh

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Each line should have the format: Details: ..., Medication: ..., Hospital: ...
                // We parse it accordingly:
                String details = "";
                String medication = "";
                String hospital = "";

                // Simple parsing based on your toString format
                String[] parts = line.split(", ");
                for (String part : parts) {
                    if (part.startsWith("Details: ")) {
                        details = part.substring(9);
                    } else if (part.startsWith("Medication: ")) {
                        medication = part.substring(12);
                    } else if (part.startsWith("Hospital: ")) {
                        hospital = part.substring(10);
                    }
                }
                if (!details.isEmpty() && !medication.isEmpty() && !hospital.isEmpty()) {
                    reports.add(new AccidentReport(details, medication, hospital));
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading reports from file: " + e.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Save all reports to the file (overwrite)
    private void saveReportsToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (AccidentReport report : reports) {
                pw.println(report.toString());
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving reports to file: " + e.getMessage(),
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AccidentManagement frame = new AccidentManagement();
            frame.setVisible(true);
        });
    }
}
