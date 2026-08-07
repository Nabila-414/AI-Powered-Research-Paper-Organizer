package gui;

import controller.RegisterController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * RegisterForm.java
 * ------------------
 * Registration window. Same 1280x720 size and same visual theme as
 * LoginForm so the whole Auth module feels consistent.
 */
public class RegisterForm extends JFrame {

    private JTextField fullNameField, emailField, usernameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JLabel statusLabel;
    private final RegisterController registerController = new RegisterController();

    // Simple counter so every field/label gets its own row in GridBagLayout.
    private int rowCounter = 0;

    public RegisterForm() {
        setTitle("AI Powered Research Paper Organizer - Register");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Image logoImage = new ImageIcon("resources/logo.png").getImage();
        setIconImage(logoImage);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(24, 26, 43));
        setContentPane(root);

        JPanel card = new JPanel(new GridBagLayout());
        card.setPreferredSize(new Dimension(460, 640));
        card.setBackground(new Color(34, 37, 61));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;

        addComponent(card, c, new JLabel(new ImageIcon(
                logoImage.getScaledInstance(56, 56, Image.SCALE_SMOOTH))), new Insets(10, 24, 8, 24), true);

        JLabel title = new JLabel("Create your account");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(card, c, title, new Insets(0, 24, 12, 24), true);

        fullNameField = addLabeledField(card, c, "Full Name");
        emailField = addLabeledField(card, c, "Email");
        usernameField = addLabeledField(card, c, "Username");

        passwordField = new JPasswordField();
        addLabeledField(card, c, "Password", passwordField);

        confirmPasswordField = new JPasswordField();
        addLabeledField(card, c, "Confirm Password", confirmPasswordField);

        JButton registerBtn = new JButton("Register");
        stylePrimaryButton(registerBtn);
        addComponent(card, c, registerBtn, new Insets(18, 24, 8, 24), false);

        JButton backBtn = new JButton("Already have an account? Login");
        styleLinkButton(backBtn);
        addComponent(card, c, backBtn, new Insets(0, 24, 8, 24), false);

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(new Color(255, 120, 120));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        addComponent(card, c, statusLabel, new Insets(4, 24, 10, 24), false);

        root.add(card);

        registerBtn.addActionListener(this::onRegister);
        backBtn.addActionListener((ActionEvent e) -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    private void onRegister(ActionEvent e) {
        String fullName = fullNameField.getText();
        String email = emailField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirm = new String(confirmPasswordField.getPassword());

        String error = registerController.register(fullName, email, username, password, confirm);
        if (error != null) {
            statusLabel.setText(error);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "Account created successfully! Please log in.",
                "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
        new LoginForm().setVisible(true);
        dispose();
    }

    // ---------- Layout helpers ----------

    /** Adds a label above a new plain JTextField and returns the field. */
    private JTextField addLabeledField(JPanel card, GridBagConstraints c, String labelText) {
        JTextField field = new JTextField();
        addLabeledField(card, c, labelText, field);
        return field;
    }

    /** Adds a label above the given field (works for JTextField and JPasswordField). */
    private void addLabeledField(JPanel card, GridBagConstraints c, String labelText, JTextField field) {
        JLabel label = new JLabel(labelText);
        label.setForeground(Color.WHITE);
        addComponent(card, c, label, new Insets(6, 24, 2, 24), false);

        styleField(field);
        addComponent(card, c, field, new Insets(0, 24, 6, 24), false);
    }

    /** Places one component on its own dedicated grid row. */
    private void addComponent(JPanel card, GridBagConstraints c, Component comp, Insets insets, boolean center) {
        c.gridy = rowCounter++;
        c.insets = insets;
        c.anchor = center ? GridBagConstraints.CENTER : GridBagConstraints.LINE_START;
        card.add(comp, c);
    }

    private void styleField(JTextField field) {
        field.setBackground(new Color(46, 49, 78));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(new Color(94, 96, 206));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void styleLinkButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setForeground(new Color(150, 170, 255));
        button.setFont(new Font("SansSerif", Font.PLAIN, 12));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterForm().setVisible(true));
    }
}
