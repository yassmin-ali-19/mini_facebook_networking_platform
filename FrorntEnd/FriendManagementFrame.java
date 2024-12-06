package friend.manegment;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;

public class FriendManagementFrame {
    private User user;
    private Friend friendManager;
    private File file;

    public FriendManagementFrame(User user, File file) {
        this.user = user;
        this.file = file;
        this.friendManager = new Friend(user, null, file); // Passing null for friend initially
        
        // Create the main frame
        JFrame frame = new JFrame("Friends Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.white);

        // Friends Panel
        JPanel friendsPanel = createSectionPanel("Friends", 10);
        populateFriendsPanel(friendsPanel);

        // Friend Requests Panel
        JPanel requestsPanel = createSectionPanel("Friend Requests", 240);
        populateRequestsPanel(requestsPanel);

        // Friend Suggestions Panel
        JPanel suggestionsPanel = createSectionPanel("Friend Suggestions", 460);
        populateSuggestionsPanel(suggestionsPanel);

        // Add panels to the frame
        frame.add(friendsPanel);
        frame.add(requestsPanel);
        frame.add(suggestionsPanel);

        frame.setVisible(true);
    }

    private JPanel createSectionPanel(String title, int xPosition) {
        JPanel panel = new JPanel();
        panel.setBounds(xPosition, 5, 190, 500);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        return panel;
    }

    private void populateFriendsPanel(JPanel panel) {
        for (String friendId : user.getFriends()) {
            JLabel friendLabel = new JLabel("Friend: " + friendId);
            JButton removeButton = new JButton("Remove");
            JButton blockButton = new JButton("Block");
            removeButton.addActionListener(e -> {
                System.out.println("Remove friend logic for ID: " + friendId);
                // Logic to remove a friend using Friend class
            });
            blockButton.addActionListener(e -> {
                System.out.println("Block user logic for ID: " + friendId);
                // Logic to block a friend using Friend class
            });
            panel.add(friendLabel);
            panel.add(removeButton);
            panel.add(blockButton);
        }
    }

    private void populateRequestsPanel(JPanel panel) {
        for (String requestId : user.getFriendRequests()) {
            JLabel requestLabel = new JLabel("Request from: " + requestId);
            JButton acceptButton = new JButton("Accept");
            JButton rejectButton = new JButton("Decline");
            acceptButton.addActionListener(e -> {
                System.out.println("Accept request logic for ID: " + requestId);
                // Logic to accept a friend request using Friend class
            });
            rejectButton.addActionListener(e -> {
                System.out.println("Reject request logic for ID: " + requestId);
                // Logic to reject a friend request using Friend class
            });
            panel.add(requestLabel);
            panel.add(acceptButton);
            panel.add(rejectButton);
        }
    }

    private void populateSuggestionsPanel(JPanel panel) {
        List<User> suggestions = friendManager.suggestions();
        for (User suggestedUser : suggestions) {
            JLabel suggestionLabel = new JLabel("Suggested: " + suggestedUser.getUsername());
            JButton addButton = new JButton("Add");
            addButton.addActionListener(e -> {
                System.out.println("Send friend request to: " + suggestedUser.getUsername());
                // Logic to send a friend request using Friend class
            });
            panel.add(suggestionLabel);
            panel.add(addButton);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create a user for testing
            User testUser = new User("1", "testuser@example.com", "TestUser", "password", "2000-01-01", "active");
            File testFile = new File("users.json"); // Replace with actual file path
            new FriendManagementFrame(testUser, testFile);
        });
    }
}