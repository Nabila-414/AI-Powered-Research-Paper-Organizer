import gui.LoginForm;

import javax.swing.*;

/**
 * Main.java
 * ----------
 * Entry point to run and demo ONLY Member 1's part (User Authentication
 * & Profile module) on its own. When the full team project is merged,
 * the main team Main class will call LoginForm the same way after
 * splash/setup, so this file also works as a reference for that.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}
