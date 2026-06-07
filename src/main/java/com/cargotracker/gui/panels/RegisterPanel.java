package com.cargotracker.gui.panels;

import com.cargotracker.gui.MainDashboardFrame;
import com.cargotracker.model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * Modern registration form with live validation and cost preview.
 * Website-like clean form layout using GridBagLayout.
 */
public class RegisterPanel extends JPanel {
    
    // Core business logic model and main application frame references
    private final CargoCompany company;
    private final MainDashboardFrame parentFrame;
    
    // Form input components
    private JComboBox<String> typeCombo;
    private JTextField senderField, recipientField, distanceField, weightField;

    // Form display components for live feedback
    private JLabel costPreviewLabel, insurancePreviewLabel, weightLimitLabel;
    private JButton registerBtn, clearBtn; // Action buttons
    
    public RegisterPanel(CargoCompany company, MainDashboardFrame parentFrame) {
        this.company = company;
        this.parentFrame = parentFrame;

        setLayout(new BorderLayout(12, 12));
        setBackground(new Color(245, 247, 250));
        setBorder(new EmptyBorder(20, 40, 20, 40));

        add(createFormHeader(), BorderLayout.NORTH);
        add(createForm(), BorderLayout.CENTER);
        add(createButtonBar(), BorderLayout.SOUTH);
    }

    private JPanel createFormHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        JLabel title = new JLabel("Register New Shipment");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(13, 27, 42));

        JLabel desc = new JLabel("Fill the form below. Cost and insurance are calculated live based on shipment type and parameters.");
        desc.setFont(new Font("SansSerif", Font.PLAIN, 13));
        desc.setForeground(new Color(90, 100, 120));

        p.add(title, BorderLayout.NORTH);
        p.add(desc, BorderLayout.SOUTH);
        return p;
    }

    private JPanel createForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235)),
                new EmptyBorder(24, 32, 24, 32)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Type
        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Shipment Type:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        typeCombo = new JComboBox<>(new String[]{
                "Standard (1.5 TL/km • max 30 kg • 5% insurance)",
                "Express  (3.0 TL/km • max 20 kg • 8% insurance)",
                "Same-Day (6.0 TL/km • max 10 kg • 12% insurance)"
        });
        typeCombo.setFont(new Font("SansSerif", Font.PLAIN, 14));
        form.add(typeCombo, gbc);
        gbc.gridwidth = 1;

        // Sender
        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Sender Name:"), gbc);
        gbc.gridx = 1;
        senderField = new JTextField(25);
        form.add(senderField, gbc);

        // Recipient
        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Recipient Name:"), gbc);
        gbc.gridx = 1;
        recipientField = new JTextField(25);
        form.add(recipientField, gbc);

        // Distance
        gbc.gridx = 0; gbc.gridy = 3;
        form.add(new JLabel("Distance (km):"), gbc);
        gbc.gridx = 1;
        distanceField = new JTextField(10);
        form.add(distanceField, gbc);

        // Weight
        gbc.gridx = 0; gbc.gridy = 4;
        form.add(new JLabel("Weight (kg):"), gbc);
        gbc.gridx = 1;
        weightField = new JTextField(10);
        form.add(weightField, gbc);

        // Dynamic weight limit
        gbc.gridx = 2; gbc.gridy = 4;
        weightLimitLabel = new JLabel("Max: — kg");
        weightLimitLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
        weightLimitLabel.setForeground(new Color(100, 110, 130));
        form.add(weightLimitLabel, gbc);

        // Live preview
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 3;
        JPanel preview = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        preview.setOpaque(false);
        costPreviewLabel = new JLabel("Estimated Cost: — TL");
        costPreviewLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        costPreviewLabel.setForeground(new Color(16, 185, 129));

        insurancePreviewLabel = new JLabel("Insurance Liability: — TL");
        insurancePreviewLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        insurancePreviewLabel.setForeground(new Color(245, 158, 11));

        preview.add(costPreviewLabel);
        preview.add(insurancePreviewLabel);
        form.add(preview, gbc);

        // Add listeners for live preview & validation
        DocumentListener liveListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { updatePreview(); }
            public void removeUpdate(DocumentEvent e) { updatePreview(); }
            public void changedUpdate(DocumentEvent e) { updatePreview(); }
        };
        distanceField.getDocument().addDocumentListener(liveListener);
        weightField.getDocument().addDocumentListener(liveListener);
        typeCombo.addActionListener(e -> {
            updateWeightLimit();
            updatePreview();
        });

        updateWeightLimit(); // initial

        return form;
    }

    private void updateWeightLimit() {
        int idx = typeCombo.getSelectedIndex();
        String limit = (idx == 0) ? "30.0" : (idx == 1) ? "20.0" : "10.0";
        weightLimitLabel.setText("Max: " + limit + " kg");
        weightLimitLabel.setForeground(new Color(16, 185, 129));
    }

    private void updatePreview() {
        try {
            double dist = Double.parseDouble(distanceField.getText().trim());
            double wt = Double.parseDouble(weightField.getText().trim());
            int idx = typeCombo.getSelectedIndex();

            Shipment temp;
            if (idx == 0) temp = new StandardShipment("preview", "preview", dist, wt);
            else if (idx == 1) temp = new ExpressShipment("preview", "preview", dist, wt);
            else temp = new SameDayShipment("preview", "preview", dist, wt);

            costPreviewLabel.setText(String.format("Estimated Cost: %.2f TL", temp.getCost()));
            insurancePreviewLabel.setText(String.format("Insurance: %.2f TL", temp.getInsuranceCost()));
        } catch (NumberFormatException ex) {
            costPreviewLabel.setText("Estimated Cost: — TL");
            insurancePreviewLabel.setText("Insurance: — TL");
        }
    }

    private JPanel createButtonBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        bar.setOpaque(false);

        clearBtn = new JButton("Clear Form");
        clearBtn.addActionListener(e -> clearForm());

        registerBtn = new JButton("✓ Register Shipment");

        registerBtn.setBackground(new Color(16, 185, 129));
        // registerBtn.setForeground(Color.WHITE);
        registerBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        registerBtn.setFocusPainted(false);
        registerBtn.setOpaque(true);
        registerBtn.setContentAreaFilled(true);
        registerBtn.setBorderPainted(true);
        registerBtn.setBorder(BorderFactory.createEmptyBorder(10, 24, 10, 24));
        registerBtn.addActionListener(e -> performRegistration());

        bar.add(clearBtn);
        bar.add(registerBtn);
        return bar;
    }

    private void performRegistration() {
        try {
            String sender = senderField.getText().trim();
            String recipient = recipientField.getText().trim();
            double dist = Double.parseDouble(distanceField.getText().trim());
            double wt = Double.parseDouble(weightField.getText().trim());

            if (sender.isEmpty() || recipient.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Sender and Recipient names are required.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Shipment s;
            int idx = typeCombo.getSelectedIndex();
            int nextId = company.getShipmentCount() + 1;
            if (idx == 0) s = new StandardShipment(nextId, sender, recipient, dist, wt);
            else if (idx == 1) s = new ExpressShipment(nextId, sender, recipient, dist, wt);
            else s = new SameDayShipment(nextId, sender, recipient, dist, wt);

            company.registerShipment(s);

            JOptionPane.showMessageDialog(this,
                    "Shipment registered successfully!\nID: " + s.getId() + " | " + s.typeLabel(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            clearForm();
            parentFrame.requestGlobalRefresh(); // refresh dashboard & table

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for distance and weight.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Capacity Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void refresh() {
        updateWeightLimit();
        updatePreview();
    }

    private void clearForm() {
        senderField.setText("");
        recipientField.setText("");
        distanceField.setText("");
        weightField.setText("");
        costPreviewLabel.setText("Estimated Cost: — TL");
        insurancePreviewLabel.setText("Insurance: — TL");
        typeCombo.setSelectedIndex(0);
        updateWeightLimit();
    }
}
