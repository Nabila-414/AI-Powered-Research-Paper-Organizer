package gui;

import controller.LoginController;
import controller.UserProfile;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * ProfileForm.java
 * -----------------
 * Shown right after a successful login. Lets the user:
 *   - view/edit their profile (name, institution, bio)
 *   - change their password
 *   - logout (returns to LoginForm)
 * Size fixed at 1280x720, same visual theme as the other forms.
 */
public class ProfileForm extends JFrame {

    private final UserProfile userProfile = new UserProfile();

    private JTextField fullNameField, institutionField;
    private JTextArea bioArea;
    private JLabel infoStatus;

    private JPasswordField oldPassField, newPassField, confirmPassField;
    private JLabel passStatus;

    public ProfileForm() {
        User user = LoginController.getCurrentUser();

        setTitle("AI Powered Research Paper Organizer - My Profile");
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Image logoImage = new ImageIcon("resources/logo.png").getImage();
        setIconImage(logoImage);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(new Color(24, 26, 43));
        setContentPane(root);

        // ---------- Top bar ----------
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(34, 37, 61));
        topBar.setBorder(BorderFactory.createEmptyBorder(14, 24, 14, 24));

        JLabel appTitle = new JLabel("  AI Powered Research Paper Organizer", 
                new ImageIcon(logoImage.getScaledInstance(32, 32, Image.SCALE_SMOOTH)), SwingConstants.LEFT);
        appTitle.setForeground(Color.WHITE);
        appTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        topBar.add(appTitle, BorderLayout.WEST);

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setBackground(new Color(210, 70, 70));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(this::onLogout);
        topBar.add(logoutBtn, BorderLayout.EAST);

        root.add(topBar, BorderLayout.NORTH);

        // ---------- Center: two cards side by side ----------
        JPanel center = new JPanel(new GridLayout(1, 2, 30, 0));
        center.setBackground(new Color(24, 26, 43));
        center.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        center.add(buildProfileCard(user));
        center.add(buildPasswordCard());

        root.add(center, BorderLayout.CENTER);
    }

    // ---------- Profile info card ----------
    private JPanel buildProfileCard(User user) {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(new Color(34, 37, 61));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(8, 24, 4, 24);
        int row = 0;

        JLabel title = new JLabel("Profile Information");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        c.gridy = row++;
        card.add(title, c);

        JLabel idLabel = new JLabel("User ID: " + user.getUserId() + "   |   Joined: " + user.getJoinDate());
        idLabel.setForeground(new Color(160, 160, 180));
        c.gridy = row++;
        card.add(idLabel, c);

        JLabel usernameLabel = new JLabel("Username: " + user.getUsername() + "   |   Email: " + user.getEmail());
        usernameLabel.setForeground(new Color(160, 160, 180));
        c.gridy = row++;
        c.insets = new Insets(2, 24, 16, 24);
        card.add(usernameLabel, c);
        c.insets = new Insets(8, 24, 4, 24);

        JLabel nameLbl = new JLabel("Full Name");
        nameLbl.setForeground(Color.WHITE);
        c.gridy = row++;
        card.add(nameLbl, c);

        fullNameField = new JTextField(user.getFullName());
        styleField(fullNameField);
        c.gridy = row++;
        card.add(fullNameField, c);

        JLabel instLbl = new JLabel("Institution");
        instLbl.setForeground(Color.WHITE);
        c.gridy = row++;
        card.add(instLbl, c);

        institutionField = new JTextField(user.getInstitution());
        styleField(institutionField);
        c.gridy = row++;
        card.add(institutionField, c);

        JLabel bioLbl = new JLabel("Bio");
        bioLbl.setForeground(Color.WHITE);
        c.gridy = row++;
        card.add(bioLbl, c);

        bioArea = new JTextArea(user.getBio(), 4, 20);
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setBackground(new Color(46, 49, 78));
        bioArea.setForeground(Color.WHITE);
        bioArea.setCaretColor(Color.WHITE);
        bioArea.setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
        JScrollPane bioScroll = new JScrollPane(bioArea);
        c.gridy = row++;
        card.add(bioScroll, c);

        JButton saveBtn = new JButton("Save Changes");
        stylePrimaryButton(saveBtn);
        c.gridy = row++;
        c.insets = new Insets(18, 24, 4, 24);
        card.add(saveBtn, c);

        infoStatus = new JLabel(" ");
        infoStatus.setForeground(new Color(120, 220, 150));
        c.gridy = row++;
        c.insets = new Insets(4, 24, 8, 24);
        card.add(infoStatus, c);

        saveBtn.addActionListener(this::onSaveProfile);
        return card;
    }

    // ---------- Change password card ----------
    private JPanel buildPasswordCard() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(new Color(34, 37, 61));
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(8, 24, 4, 24);
        int row = 0;

        JLabel title = new JLabel("Change Password");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 18));
        c.gridy = row++;
        card.add(title, c);

        JLabel oldLbl = new JLabel("Old Password");
        oldLbl.setForeground(Color.WHITE);
        c.gridy = row++;
        c.insets = new Insets(20, 24, 4, 24);
        card.add(oldLbl, c);

        oldPassField = new JPasswordField();
        styleField(oldPassField);
        c.gridy = row++;
        c.insets = new Insets(0, 24, 4, 24);
        card.add(oldPassField, c);

        JLabel newLbl = new JLabel("New Password");
        newLbl.setForeground(Color.WHITE);
        c.gridy = row++;
        card.add(newLbl, c);

        newPassField = new JPasswordField();
        styleField(newPassField);
        c.gridy = row++;
        card.add(newPassField, c);

        JLabel confirmLbl = new JLabel("Confirm New Password");
        confirmLbl.setForeground(Color.WHITE);
        c.gridy = row++;
        card.add(confirmLbl, c);

        confirmPassField = new JPasswordField();
        styleField(confirmPassField);
        c.gridy = row++;
        card.add(confirmPassField, c);

        JButton changeBtn = new JButton("Update Password");
        stylePrimaryButton(changeBtn);
        c.gridy = row++;
        c.insets = new Insets(18, 24, 4, 24);
        card.add(changeBtn, c);

        passStatus = new JLabel(" ");
        passStatus.setForeground(new Color(255, 120, 120));
        c.gridy = row++;
        c.insets = new Insets(4, 24, 8, 24);
        card.add(passStatus, c);

        changeBtn.addActionListener(this::onChangePassword);
        return card;
    }

    // ---------- Event handlers ----------

    private void onSaveProfile(ActionEvent e) {
        String result = userProfile.updateProfile(
                fullNameField.getText(), institutionField.getText(), bioArea.getText());
        if (result != null) {
            infoStatus.setForeground(new Color(255, 120, 120));
            infoStatus.setText(result);
        } else {
            infoStatus.setForeground(new Color(120, 220, 150));
            infoStatus.setText("Profile updated successfully.");
        }
    }

    private void onChangePassword(ActionEvent e) {
        String oldPass = new String(oldPassField.getPassword());
        String newPass = new String(newPassField.getPassword());
        String confirmPass = new String(confirmPassField.getPassword());

        String result = userProfile.changePassword(oldPass, newPass, confirmPass);
        if (result != null) {
            passStatus.setForeground(new Color(255, 120, 120));
            passStatus.setText(result);
        } else {
            passStatus.setForeground(new Color(120, 220, 150));
            passStatus.setText("Password updated successfully.");
            oldPassField.setText("");
            newPassField.setText("");
            confirmPassField.setText("");
        }
    }

    private void onLogout(ActionEvent e) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?", "Confirm Logout",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            userProfile.logout();
            new LoginForm().setVisible(true);
            dispose();
        }
    }

    // ---------- Styling helpers ----------

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
}
