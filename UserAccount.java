package facebookcheck;

import java.io.File;
import java.util.List;
import facebookcheck.Friend;

public class UserAccount {

    public static void main(String[] args) {
        // Path to the users.json file
        File file = new File("users.json");
        userDatabase db = new userDatabase(file);

        // Load existing users or create default ones if file is empty
        List<User> users = db.load();
        if (users.isEmpty()) {
            System.out.println("Users file is empty or doesn't exist. Creating default users.");

            // Create default users
            User user1 = new User("1", "alice@example.com", "Alice", "password123", "1990-01-01", "offline");
            User user2 = new User("2", "bob@example.com", "Bob", "password456", "1992-02-02", "offline");

            users.add(user1);
            users.add(user2);

            // Save the default users back to the file
            db.save(users);
            System.out.println("Default users added.");
        }

        // Example: user1 is the requester and user2 is the recipient
        User user1 = users.get(0); // Alice
        User user2 = users.get(1); // Bob

        // Initialize Friend management for user1 and user2
        Friend friendManager = new Friend(user1);

        // Send a friend request from user1 to user2
        System.out.println("\n=== Sending Friend Request ===");
        friendManager.sendFriendRequest(user1, user2);

        // Block user2 by user1
        System.out.println("\n=== Blocking a User ===");
        friendManager.blocking(user1, user2);

        // Accept the friend request (only works if it's pending)
        System.out.println("\n=== Accepting Friend Request ===");
        friendManager.accept(user1, user2);

        // Remove a friend request (only works if it's pending)
        System.out.println("\n=== Removing Friend Request ===");
        friendManager.remove(user1, user2);

        // Suggest friends for user1
        System.out.println("\n=== Friend Suggestions ===");
        List<User> suggestions = friendManager.suggestions(user1);
        System.out.println("Suggested Friends for " + user1.getUsername() + ":");
        for (User suggestion : suggestions) {
            System.out.println(suggestion.getUsername());
        }
    }
}
