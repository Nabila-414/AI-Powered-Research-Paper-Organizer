package controller;

import model.User;
import util.FileDatabase;
import util.PasswordUtil;

/**
 * UserProfile.java
 * -----------------
 * Handles everything that happens AFTER login:
 *   - viewing/updating profile info
 *   - changing password
 *   - logging out
 * This is the class the "Profile Form" GUI talks to.
 */
public class UserProfile {

    /** Updates full name, institution and bio for the currently logged-in user. */
    public String updateProfile(String fullName, String institution, String bio) {
        User current = LoginController.getCurrentUser();
        if (current == null) {
            return "No user is logged in.";
        }
        if (fullName == null || fullName.trim().isEmpty()) {
            return "Full name cannot be empty.";
        }

        current.setFullName(fullName.trim());
        current.setInstitution(institution == null ? "" : institution.trim());
        current.setBio(bio == null ? "" : bio.trim());

        FileDatabase.updateUser(current);
        LoginController.setCurrentUser(current);
        return null; // success
    }

    /** Changes the password after verifying the old one. */
    public String changePassword(String oldPassword, String newPassword, String confirmNewPassword) {
        User current = LoginController.getCurrentUser();
        if (current == null) {
            return "No user is logged in.";
        }
        if (!PasswordUtil.verify(oldPassword, current.getPasswordHash())) {
            return "Old password is incorrect.";
        }
        if (newPassword == null || newPassword.length() < 6) {
            return "New password must be at least 6 characters.";
        }
        if (!newPassword.equals(confirmNewPassword)) {
            return "New password and confirmation do not match.";
        }

        current.setPasswordHash(PasswordUtil.hash(newPassword));
        FileDatabase.updateUser(current);
        LoginController.setCurrentUser(current);
        return null; // success
    }

    /** Logs the current user out (clears the session). */
    public void logout() {
        LoginController.logout();
    }

    public User getLoggedInUser() {
        return LoginController.getCurrentUser();
    }
}
