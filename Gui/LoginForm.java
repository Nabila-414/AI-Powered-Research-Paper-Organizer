package gui;

import controller.LoginController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * LoginForm.java
 * ---------------
 * The window a user sees first. Size fixed at 1280x720 as required.
 * Uses the project logo (resources/logo.png) both as the window icon
 * and as a banner image inside the form.
 */
public class LoginForm extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private final LoginController loginController = new LoginController();

    public LoginForm() {
        setTitle("AI Powered Research Paper Organizer - Login");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Image logoImage = new ImageIcon("resources/logo.png").getImage();
        setIconImage(logoImage);

        JPanel root = new JPanel(new GridBagLayout());
        root.setBackground(new Color(24, 26, 43));
        setContentPane(root);

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(420, 480));
        card.setBackground(new Color(34, 37, 61));
        card.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.insets = new Insets(10, 20, 10, 20);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel logoLabel = new JLabel(scaledIcon(logoImage, 64, 64));
        c.gridy = 0;
        c.anchor = GridBagConstraints.CENTER;
        card.add(logoLabel, c);

        JLabel title = new JLabel("Research Paper Organizer");
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridy = 1;
        card.add(title, c);

        JLabel subtitle = new JLabel("Sign in to continue");
        subtitle.setForeground(new Color(160, 160, 180));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridy = 2;
        card.add(subtitle, c);

        JLabel userLbl = new JLabel("Username");
        userLbl.setForeground(Color.WHITE);
        c.gridy = 3;
        card.add(userLbl, c);

        usernameField = new JTextField();
        styleField(usernameField);
        c.gridy = 4;
        card.add(usernameField, c);

        JLabel passLbl = new JLabel("Password");
        passLbl.setForeground(Color.WHITE);
        c.gridy = 5;
        card.add(passLbl, c);

        passwordField = new JPasswordField();
        styleField(passwordField);
        c.gridy = 6;
        card.add(passwordField, c);

        JButton loginBtn = new JButton("Login");
        stylePrimaryButton(loginBtn);
        c.gridy = 7;
        c.insets = new Insets(20, 20, 10, 20);
        card.add(loginBtn, c);

        JButton registerBtn = new JButton("Create a new account");
        styleLinkButton(registerBtn);
        c.gridy = 8;
        c.insets = new Insets(0, 20, 10, 20);
        card.add(registerBtn, c);

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(new Color(255, 120, 120));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridy = 9;
        card.add(statusLabel, c);

        root.add(card);

        loginBtn.addActionListener(this::onLogin);
        registerBtn.addActionListener((ActionEvent e) -> {
            new RegisterForm().setVisible(true);
            dispose();
        });

        // Pressing Enter in password field triggers login
        passwordField.addActionListener(this::onLogin);
    }

    private void onLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        String error = loginController.login(username, password);
        if (error != null) {
            statusLabel.setText(error);
            return;
        }

        User user = LoginController.getCurrentUser();
        JOptionPane.showMessageDialog(this,
                "Welcome back, " + user.getFullName() + "!",
                "Login Successful", JOptionPane.INFORMATION_MESSAGE);

        new ProfileForm().setVisible(true);
        dispose();
    }

    private ImageIcon scaledIcon(Image image, int w, int h) {
        return new ImageIcon(image.getScaledInstance(w, h, Image.SCALE_SMOOTH));
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
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
