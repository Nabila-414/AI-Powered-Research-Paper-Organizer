package util;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileDatabase.java
 * -----------------
 * Acts as a very simple "database" so this module can run standalone
 * (without MySQL/SQLite setup) — perfect for a student project demo.
 * All users are saved as a serialized List<User> inside "users.dat".
 *
 * When your teammates build the real DatabaseManager (Member 5), you
 * can swap the inside of these methods with real SQL calls and the
 * rest of the Auth module (controllers/GUI) will NOT need to change,
 * because they only depend on these method names.
 */
public class FileDatabase {

    private static final String FILE_PATH = "users.dat";

    // Load every user from the file. Returns empty list if file doesn't exist yet.
    @SuppressWarnings("unchecked")
    public static List<User> loadUsers() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Could not load users.dat: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Overwrite the file with the full given list.
    public static void saveUsers(List<User> users) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.err.println("Could not save users.dat: " + e.getMessage());
        }
    }

    public static void addUser(User user) {
        List<User> users = loadUsers();
        users.add(user);
        saveUsers(users);
    }

    public static User findByUsername(String username) {
        for (User u : loadUsers()) {
            if (u.getUsername().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }

    public static User findByEmail(String email) {
        for (User u : loadUsers()) {
            if (u.getEmail().equalsIgnoreCase(email)) {
                return u;
            }
        }
        return null;
    }

    // Replace an existing user's record (used after profile edit / password change)
    public static void updateUser(User updatedUser) {
        List<User> users = loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(updatedUser.getUserId())) {
                users.set(i, updatedUser);
                break;
            }
        }
        saveUsers(users);
    }

    // Generates the next user id like U-1001, U-1002, ...
    public static String generateNextUserId() {
        List<User> users = loadUsers();
        int next = 1000 + users.size() + 1;
        return "U-" + next;
    }
}
