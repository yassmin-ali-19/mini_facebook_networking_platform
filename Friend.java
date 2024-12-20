
package facebookcheck;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
public class Friend {
    private User user, friend;
    private File file;
    private notificationsManager notificationsManager;

    public Friend(User user, User friend, File file, File notificationFile) {
        this.user = user;
        this.friend = friend;
        this.file = file;
        this.notificationsManager = new notificationsManager(notificationFile);
    }

    public Friend(User user, User friend) {
        this.user = user;
        this.friend = friend;
    }

    public boolean sendFriendRequest(User u, User friend) {
        // Check if the user and friend are not blocked
        if (!friend.getBlockedUsers().contains(u) && !u.getBlockedUsers().contains(friend) && !u.getFriends().contains(friend)) {
            ArrayList<User> yourRequests = new ArrayList<>(u.getYourRequests());
            yourRequests.add(friend);
            u.setyourRequests(yourRequests);

            ArrayList<User> friendRequests = new ArrayList<>(friend.getFriendRequests());
            friendRequests.add(u);
            friend.setfriendRequests(friendRequests);

            saveUsers();
            return true;
        }
        return false;
    }

    public boolean blocking(User u, User friend) {
        if (friend.getBlockedUsers().contains(u) || u.getBlockedUsers().contains(friend)) {
            return false;
        }

        // Add the user to the blocked users list
        ArrayList<User> blockedUsers = new ArrayList<>(u.getBlockedUsers());
        blockedUsers.add(friend);
        u.setblockedUsers(blockedUsers);

        // Remove from friends and friend requests
        ArrayList<User> friendRequests = new ArrayList<>(u.getFriendRequests());
        friendRequests.remove(friend);
        u.setfriendRequests(friendRequests);

        ArrayList<User> friends = new ArrayList<>(u.getFriends());
        friends.remove(friend);
        u.setFriends(friends);

        // Update friend's list as well
        ArrayList<User> friendBlockedUsers = new ArrayList<>(friend.getBlockedUsers());
        friendBlockedUsers.add(u);
        friend.setblockedUsers(friendBlockedUsers);

        ArrayList<User> friendFriendRequests = new ArrayList<>(friend.getFriendRequests());
        friendFriendRequests.remove(u);
        friend.setfriendRequests(friendFriendRequests);

        ArrayList<User> friendFriends = new ArrayList<>(friend.getFriends());
        friendFriends.remove(u);
        friend.setFriends(friendFriends);

        saveUsers();
        return true;
    }

    public boolean accept(User u, User friend) {
        if (u.getFriendRequests().contains(friend)) {
            // Accept the friend request
            ArrayList<User> friends = new ArrayList<>(u.getFriends());
            friends.add(friend);
            u.setFriends(friends);

            // Remove from friend requests
            ArrayList<User> friendRequests = new ArrayList<>(u.getFriendRequests());
            friendRequests.remove(friend);
            u.setfriendRequests(friendRequests);

            // Update friend's friends list
            ArrayList<User> friendFriends = new ArrayList<>(friend.getFriends());
            friendFriends.add(u);
            friend.setFriends(friendFriends);

            saveUsers();
            return true;
        }
        return false;
    }

    public boolean remove(User u, User friend) {
        if (u.getFriends().contains(friend)) {
            ArrayList<User> friends = new ArrayList<>(u.getFriends());
            friends.remove(friend);
            u.setFriends(friends);

            ArrayList<User> friendFriends = new ArrayList<>(friend.getFriends());
            friendFriends.remove(u);
            friend.setFriends(friendFriends);

            saveUsers();
            return true;
        }
        return false;
    }

    public List<User> suggestions(User u) {
        List<User> allUsers = new userDatabase(file).load();
        List<User> suggestions = new ArrayList<>();

        for (User potentialFriend : allUsers) {
            if (potentialFriend != null &&
                !potentialFriend.getUserId().equals(u.getUserId()) &&
                !u.getFriends().contains(potentialFriend) &&
                !u.getBlockedUsers().contains(potentialFriend) &&
                !potentialFriend.getBlockedUsers().contains(u)) {
                suggestions.add(potentialFriend);
            }
        }

        u.setsuggested(suggestions);
        saveUsers();
        return suggestions;
    }

    private void saveUsers() {
        userDatabase db = new userDatabase(file);
        db.save(getUpdatedUserList());
    }

    private List<User> getUpdatedUserList() {
        List<User> users = new userDatabase(file).load();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) {
                users.set(i, user);
            } else if (users.get(i).equals(friend)) {
                users.set(i, friend);
            }
        }
        return users;
    }
}
