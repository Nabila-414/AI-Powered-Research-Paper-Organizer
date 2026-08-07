package model;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * User.java
 * ---------
 * This is the MODEL class for a user of the AI Research Paper Organizer.
 * It only stores data (fields) + getters/setters. It has NO logic for
 * login/register — that logic lives in the Controller classes.
 *
 * implements Serializable -> so we can save a list of User objects
 * directly into a file (users.dat) using ObjectOutputStream.
 */
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;         // unique id, e.g. U-1001
    private String fullName;
    private String email;
    private String username;
    private String passwordHash;   // NEVER store plain password
    private String institution;    // university / organization
    private String bio;            // short "about me"
    private String joinDate;       // date account was created

    public User() {
    }

    public User(String userId, String fullName, String email,
                String username, String passwordHash) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
        this.institution = "";
        this.bio = "";
        this.joinDate = LocalDate.now().toString();
    }

    // ---------- Getters & Setters ----------
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getJoinDate() { return joinDate; }
    public void setJoinDate(String joinDate) { this.joinDate = joinDate; }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
