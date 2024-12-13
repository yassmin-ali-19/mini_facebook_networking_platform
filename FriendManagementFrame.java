package facebookcheck;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FriendManagementFrame extends JFrame {
    private User user,friend;
    private String email;
    private Friend friendManager;
    private File file=new File("jjson.txt");
    userDatabase db=new userDatabase(file);
    ArrayList<User> array = new ArrayList<>(db.load());

    public FriendManagementFrame(String email) {
        this.email=email;
//        array=(ArrayList) db.load();
      
        // Passing null for friend initially
         for (User i : array) {
    if (i != null && i.getEmail().equals(this.email)) {
        this.user = i;
        break; 
    }
}
          this.friendManager = new Friend(this.user);
        
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
        for (User friendname : user.getFriends()) {
            JLabel friendLabel = new JLabel(friendname.getUsername()+"'"+friendname.getStatus()+"'");
            JButton removeButton = new JButton("Remove");
            JButton blockButton = new JButton("Block");
            removeButton.addActionListener(e -> {
                friendManager.remove(user, friendname);
            });
            blockButton.addActionListener(e -> {
                friendManager.blocking(user, friendname);
            });
            panel.add(friendLabel);
            panel.add(removeButton);
            panel.add(blockButton);
        }
    }

   private void populateRequestsPanel(JPanel panel) {
    for (User requestname : user.getFriendRequests()) {
        if (requestname != null) { // Check for null
            JLabel requestLabel = new JLabel(requestname.getUsername() + "'" + requestname.getStatus() + "'");
            JButton acceptButton = new JButton("Accept");
            JButton rejectButton = new JButton("Block");
            acceptButton.addActionListener(e -> {
                friendManager.accept(user, requestname);
            });
            rejectButton.addActionListener(e -> {
                friendManager.blocking(user, requestname);
            });
            panel.add(requestLabel);
            panel.add(acceptButton);
            panel.add(rejectButton);
        }
    }
}

    private void populateSuggestionsPanel(JPanel panel) {
        List<User> suggestions = friendManager.suggestions(user);
        for (User suggestedUser : suggestions) {
            JLabel suggestionLabel = new JLabel(suggestedUser.getUsername()+"'"+suggestedUser.getStatus()+"'");
            JButton addButton = new JButton("Add");
            addButton.addActionListener(e -> {
           friendManager.sendFriendRequest(user,suggestedUser);
           ArrayList arrau=new ArrayList (suggestedUser.getFriendRequests());
           arrau.add(user);
           suggestedUser.setfriendRequests(arrau);
           
           
            });
            panel.add(suggestionLabel);
            panel.add(addButton);
        }
    }

    public static void main(String[] args) {
        User u=new User("fdfdfd","fdfddfd","DFdfdf","DFDFDf","DFDFDF","DFDfdf");
         SwingUtilities.invokeLater(() -> new FriendManagementFrame("yassmin@gmail.com"));
    }

//    void setVisible(boolean b) {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }
}