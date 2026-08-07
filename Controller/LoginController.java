package controller;

import model.User;
import util.FileDatabase;
import util.PasswordUtil;

/**
 * LoginController.java
 * ---------------------
 * Handles the LOGIN logic. The GUI (LoginForm) calls login(username, password)
 * and only gets back a simple result: either a User object (success) or
 * an error message (failure). The GUI never touches FileDatabase directly —
 * that is the whole point of MVC (Model - View - Controller).
 */
public class LoginController {

    // Keeps track of who is currently logged in during this app session.
    private static User currentUser;

    /**
     * Attempts to log in.
     * @return null if login succeeded (currentUser is set),
     *         or an error message String if it failed.
     */
    public String login(String username, String password) {
        if (username == null || username.trim().isEmpty()) {
            return "Username cannot be empty.";
        }
        if (password == null || password.isEmpty()) {
            return "Password cannot be empty.";
        }

        User user = FileDatabase.findByUsername(username.trim());
        if (user == null) {
            return "No account found with this username.";
        }

        if (!PasswordUtil.verify(password, user.getPasswordHash())) {
            return "Incorrect password. Please try again.";
        }

        currentUser = user;
        return null; // null = success
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = null;
    }
}
