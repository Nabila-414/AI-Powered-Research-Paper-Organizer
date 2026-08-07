package controller;

import model.User;
import util.FileDatabase;
import util.PasswordUtil;

import java.util.regex.Pattern;

/**
 * RegisterController.java
 * -------------------------
 * Handles the REGISTRATION logic: validates input, checks duplicates,
 * hashes the password, and saves the new user through FileDatabase.
 */
public class RegisterController {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    /**
     * @return null if registration succeeded, otherwise an error message.
     */
    public String register(String fullName, String email, String username,
                            String password, String confirmPassword) {

        if (fullName == null || fullName.trim().isEmpty()) {
            return "Full name is required.";
        }
        if (email == null || !EMAIL_PATTERN.matcher(email.trim()).matches()) {
            return "Please enter a valid email address.";
        }
        if (username == null || username.trim().length() < 4) {
            return "Username must be at least 4 characters.";
        }
        if (password == null || password.length() < 6) {
            return "Password must be at least 6 characters.";
        }
        if (!password.equals(confirmPassword)) {
            return "Password and Confirm Password do not match.";
        }
        if (FileDatabase.findByUsername(username.trim()) != null) {
            return "This username is already taken.";
        }
        if (FileDatabase.findByEmail(email.trim()) != null) {
            return "An account with this email already exists.";
        }

        String newId = FileDatabase.generateNextUserId();
        String hashedPassword = PasswordUtil.hash(password);

        User newUser = new User(newId, fullName.trim(), email.trim(),
                username.trim(), hashedPassword);

        FileDatabase.addUser(newUser);
        return null; // null = success
    }
}
