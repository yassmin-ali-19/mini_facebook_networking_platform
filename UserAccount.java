package user.account;

import java.io.File;
import java.util.List;
import user.account.friendManagement.Friend;

/**
 *
 * @author Ahmed Kamel
 */
public class UserAccount {

    public static void main(String[] args) {
        // Path to the users.json file
        File file = new File("users.json");
        userDatabase db = new userDatabase(file);

        // Check if the file exists, if not create it and add default users
        List<User> users = db.loadUsers();
        if (users.isEmpty()) {
            System.out.println("Users file is empty or doesn't exist. Creating default users.");
            
            // Create default users
            User user1 = new User(String.valueOf(System.currentTimeMillis()), "alice@example.com", "Alice", "password123", "1990-01-01", "offline");
            User user2 = new User(String.valueOf(System.currentTimeMillis()), "bob@example.com", "Bob", "password456", "1992-02-02", "offline");
            
            users.add(user1);
            users.add(user2);

            // Save the default users back to the file
            db.saveUsers(users);
            System.out.println("Default users added.");
        }

        // Example: user1 is the requester and user2 is the recipient
        User user1 = users.get(0); // Requester (e.g., 'Alice')
        User user2 = users.get(1); // Recipient (e.g., 'Bob')

        // Test Friend functionality: Create a Friend object to manage requests and relationships
        Friend friendManager = new Friend(user1, user2, file);

        // Send a friend request from user1 to user2
        System.out.println("Sending Friend Request...");
        friendManager.sendFriendRequest();

        // Test blocking a user: user1 blocks user2
        System.out.println("Blocking a User...");
        friendManager.blocking();

        // Test accepting a friend request (should fail if not a request)
        System.out.println("Trying to Accept Friend Request...");
        friendManager.accept();

        // Test removing a friend request
        System.out.println("Removing Friend Request...");
        friendManager.remove();

        // Test friend suggestions for user1 (will suggest non-friends, non-blocked users)
        System.out.println("Getting Friend Suggestions...");
        List<User> suggestions = friendManager.suggestions(); // Get suggestions
        System.out.println("Suggested Friends for " + user1.getUsername() + ":");
        for (User suggestion : suggestions) {
            System.out.println(suggestion.getUsername());
        }
    }
}
