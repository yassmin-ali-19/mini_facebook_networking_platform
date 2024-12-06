package user.account;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class User {
    private String userId;
    private String email;
    private String username;
    private String password;
    private String dateOfBirth;
    private String status;

    private List<String> friends; // IDs of friends
    private List<String> friendRequests; // IDs of pending friend requests
    private List<String> blockedUsers; // IDs of blocked users
    private List<String> suggested;   // IDs of suggested users


    public User(String userId, String email, String username, String password, String dateOfBirth, String status) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.setPassword(password); // Automatically hash the password
        this.dateOfBirth = dateOfBirth;
        this.status = status;

        this.friends = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.blockedUsers = new ArrayList<>();
    }

    // Getters and setters
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        try {
            this.password = passwordHasher.hasher(password); // Hash the password
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Password hashing failed", e);
        }
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getFriends() {
        return friends;
    }

    public List<String> getFriendRequests() {
        return friendRequests;
    }

    public List<String> getBlockedUsers() {
        return blockedUsers;
    }

    public List<String> getSuggested() {
        return suggested;
    }
    
}
