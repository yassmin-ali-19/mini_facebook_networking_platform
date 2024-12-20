
package facebookcheck;

//import friend.manegment.ChatFront;
//import friend.manegment.FriendChat;
import javax.swing.*;
import java.awt.*;


import java.io.File;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class FriendManagementFrame extends JFrame {
    private User user;
    private String email;
    private Friend friendManager;
    private File file = new File("jjson.txt");
    private userDatabase db = new userDatabase(file);
    private File f = new File("notifications.txt");
    private ArrayList<User> array = new ArrayList<>(db.load());

    public FriendManagementFrame(String email) {
        this.email = email;
        
        // Find the user based on email
        for (User i : array) {
            if (i != null && i.getEmail().equals(this.email)) {
                this.user = i;
                break;
            }
        }

        // Initialize the friendManager with the current user
        this.friendManager = new Friend(this.user, null, file, f);

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
private boolean isValidIPAddress(String ip) {
    String ipPattern = 
        "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
        "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    return ip.matches(ipPattern);
}
private void populateFriendsPanel(JPanel panel) {
    panel.removeAll(); // Clear existing components

    for (User friend : user.getFriends()) {
        JLabel friendLabel = new JLabel(friend.getUsername() + "'s Status: " + friend.getStatus());
        JButton removeButton = new JButton("Remove");
        JButton blockButton = new JButton("Block");
        JButton chatButton = new JButton("Chat");

        removeButton.addActionListener(e -> {
            friendManager.remove(user, friend); // Remove the friend
            populateFriendsPanel(panel); // Refresh the friends panel
        });
        
        blockButton.addActionListener(e -> {
            friendManager.blocking(user, friend); // Block the friend
            populateFriendsPanel(panel); // Refresh the friends panel
        });

       // In the chat button action listener
chatButton.addActionListener(e -> {
    try {
        // Ensure friend.getUserId() is valid
        String friendUserId = friend.getUserId();
        if (isValidIPAddress(friendUserId)) {
            Socket friendSocket = new Socket(friend.getUserId(), 12345);
            ChatFront chatWindow = new ChatFront(); // Assuming ChatFront can accept a Socket
            chatWindow.setVisible(true);
            chatWindow.setLocationRelativeTo(null);
        } else {
            JOptionPane.showMessageDialog(panel, "Invalid user ID for chat.");
        }
    } catch (IOException ex) {
        Logger.getLogger(FriendManagementFrame.class.getName()).log(Level.SEVERE, null, ex);
        JOptionPane.showMessageDialog(panel, "Unable to connect to chat.");
    }
});




        panel.add(friendLabel);
        panel.add(removeButton);
        panel.add(blockButton);
        panel.add(chatButton); // Add the chat button under each friend
    }

    panel.revalidate(); // Refresh the panel to show new components
    panel.repaint(); // Repaint the panel to reflect changes
}

    private void populateRequestsPanel(JPanel panel) {
        for (User requestname : user.getFriendRequests()) {
            if (requestname != null) {
                JLabel requestLabel = new JLabel(requestname.getUsername() + "'s Status: " + requestname.getStatus());
                JButton acceptButton = new JButton("Accept");
                JButton rejectButton = new JButton("Block");
                acceptButton.addActionListener(e -> {
                    friendManager.accept(user, requestname); // Accept the friend request
                });
                rejectButton.addActionListener(e -> {
                    friendManager.blocking(user, requestname); // Block the friend request
                });
                panel.add(requestLabel);
                panel.add(acceptButton);
                panel.add(rejectButton);
            }
        }
    }

private void populateSuggestionsPanel(JPanel panel) {
    // Fetch suggestions using the suggestions method from Friend class
    List<User> suggestions = friendManager.suggestions(user);
    
    panel.removeAll(); // Clear existing components to refresh the panel

    for (User suggestedUser : suggestions) {
        JLabel suggestionLabel = new JLabel(suggestedUser.getUsername() + "'s Status: " + suggestedUser.getStatus());
        JButton addButton = new JButton("Add Friend");
        
        addButton.addActionListener(e -> {
            // Send friend request logic
            if (friendManager.sendFriendRequest(user, suggestedUser)) {
                ArrayList array=new ArrayList<>(user.getFriends());
                array.add(suggestedUser);
                user.setFriends(array); // Add the suggested user to the friends list
                JOptionPane.showMessageDialog(panel, 
                    suggestedUser.getUsername() + " has been added to your friends!");
                populateFriendsPanel((JPanel) panel.getParent().getComponent(0)); // Refresh friends panel
            } else {
                JOptionPane.showMessageDialog(panel, 
                    "Unable to add " + suggestedUser.getUsername() + ". They may be blocked or already a friend.");
            }
        });
        
        panel.add(suggestionLabel);
        panel.add(addButton);
    }
    
    panel.revalidate(); // Refresh the panel to show new components
    panel.repaint(); // Repaint the panel to reflect changes
}
}

