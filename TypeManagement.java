import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TypeManagement extends JFrame {

    // Components
    private JTextField nameField;
    private JTextArea descriptionArea;
    private JButton saveButton, cancelButton;

    // Constructor
    public TypeManagement() {
        setTitle("Traffic Monitoring System - Type Management");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Top Panel: Title
        JLabel title = new JLabel("Type Management", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setOpaque(true);
        title.setBackground(new Color(0, 153, 204));
        title.setForeground(Color.WHITE);
        title.setPreferredSize(new Dimension(500, 50));
        add(title, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(nameField, gbc);

        JLabel descLabel = new JLabel("Description:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(descLabel, gbc);

        descriptionArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(descriptionArea);
        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(scrollPane, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save Data");
        cancelButton = new JButton("Cancel");

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Button actions
        saveButton.addActionListener(e -> saveData());
        cancelButton.addActionListener(e -> clearForm());
    }

    // Logic methods
    private void saveData() {
        String name = nameField.getText();
        String description = descriptionArea.getText();

        if (name.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            // You can implement file/database saving logic here
            JOptionPane.showMessageDialog(this, "Data saved:\nName: " + name + "\nDescription: " + description, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearForm() {
        nameField.setText("");
        descriptionArea.setText("");
    }
}
