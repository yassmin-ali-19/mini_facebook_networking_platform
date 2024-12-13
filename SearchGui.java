/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facebook;

/**
 *
 * @author Elnour Tech
 */

import javax.swing.*;
import java.awt.*;
import user.account.User;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import user.account.Friend;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import user.account.userDatabase;
public class SearchGui extends JFrame {
    private JTextField searchField;
    private JButton searchButton;
    private JList<String> searchResultsList;
    private DefaultListModel<String> searchResultsModel;
    private SearchManager searchManager;
    private JButton addFriendButton;
    private JButton removeFriendButton;
    private JButton blockUserButton;
    private JButton viewProfileButton;
    private JButton joinGroupButton;
    private JButton leaveGroupButton;
    private JButton viewGroupButton;

    // Constructor
    public SearchGui(User currentUser, List<User> allUsers, List<Group> allGroups) {
        setTitle("Search Interface");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        searchManager = new SearchManager(currentUser, allUsers, allGroups);

        JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.NORTH);

        // Search results list
        searchResultsModel = new DefaultListModel<>();
        searchResultsList = new JList<>(searchResultsModel);
        searchResultsList.addListSelectionListener(e -> updateButtons());
        add(new JScrollPane(searchResultsList), BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        addFriendButton = new JButton("Add Friend");
        removeFriendButton = new JButton("Remove Friend");
   blockUserButton = new JButton("Block User");
        viewProfileButton = new JButton("View Profile");
        joinGroupButton = new JButton("Join Group");
        leaveGroupButton = new JButton("Leave Group");
        viewGroupButton = new JButton("View Group");

        // Add buttons to panel
        buttonPanel.add(addFriendButton);
        
        buttonPanel.add(removeFriendButton);
        
        buttonPanel.add(blockUserButton);
        
     buttonPanel.add(viewProfileButton);
        buttonPanel.add(joinGroupButton);
        buttonPanel.add(leaveGroupButton);
        buttonPanel.add(viewGroupButton);
        add(buttonPanel, BorderLayout.SOUTH);

        hideGroupButtons();

        searchButton.addActionListener(new SearchButtonListener());
        
        addFriendButton.addActionListener(new ActionButtonListener());
        
        removeFriendButton.addActionListener(new ActionButtonListener());
        
        blockUserButton.addActionListener(new ActionButtonListener());
        
        viewProfileButton.addActionListener(new ActionButtonListener());
        joinGroupButton.addActionListener(new GroupListener());
        leaveGroupButton.addActionListener(new GroupListener());
        viewGroupButton.addActionListener(new GroupListener());

        setVisible(true);
    }

    private void updateButtons() {
        String selectedValue = searchResultsList.getSelectedValue();
        if (selectedValue != null) {
            if (selectedValue.startsWith("User: ")) {
                showUserButtons();
                hideGroupButtons();
            } else if (selectedValue.startsWith("Group: ")) {
                hideUserButtons();
                showGroupButtons();
            }
        } else {
            hideUserButtons();
            hideGroupButtons();
        }
    }

    private void showUserButtons() {
        addFriendButton.setVisible(true);
        removeFriendButton.setVisible(true);
        blockUserButton.setVisible(true);
        viewProfileButton.setVisible(true);
    }

    private void hideUserButtons() {
        addFriendButton.setVisible(false);
        removeFriendButton.setVisible(false);
        blockUserButton.setVisible(false);
        viewProfileButton.setVisible(false);
    }

    private void showGroupButtons() {
        joinGroupButton.setVisible(true);
        leaveGroupButton.setVisible(true);
        viewGroupButton.setVisible(true);
    }

    private void hideGroupButtons() {
        joinGroupButton.setVisible(false);
        leaveGroupButton.setVisible(false);
        viewGroupButton.setVisible(false);
    }

    private class ActionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedValue = searchResultsList.getSelectedValue();
      if (selectedValue != null) {
             String username = selectedValue.split(": ")[1];
             User friendUser = UserByUname(username);
      if (friendUser != null) {
          File userFile = new File("users.dat");

         Friend friendAction = new Friend(searchManager.getCurrentUser());

         if (e.getSource() == addFriendButton) {
              friendAction.sendFriendRequest(searchManager.getCurrentUser(), friendUser);
                       JOptionPane.showMessageDialog(SearchGui.this, "Friend request sent to " + username);
                } else if (e.getSource() == removeFriendButton) {
                     friendAction.remove(searchManager.getCurrentUser(), friendUser);
                     JOptionPane.showMessageDialog(SearchGui.this, "Removed friend request for " + username);
             } else if (e.getSource() == blockUserButton) {
                 friendAction.blocking(searchManager.getCurrentUser(), friendUser);
                 JOptionPane.showMessageDialog(SearchGui.this, username + " has been blocked.");
             } else if (e.getSource() == viewProfileButton) {
                     /////////////////////////////////////////////////   new ProfileManagement();
                    }
                } else {
                    JOptionPane.showMessageDialog(SearchGui.this, "User not found.");
                }
            } else {
                JOptionPane.showMessageDialog(SearchGui.this, "Please select a user first.");
            }
        }

        private User UserByUname(String username) {
            for (User user : searchManager.getUsers()) {
                if (user.getUsername().equals(username)) {
                    return user;
                }
            }
            return null;
        }
    }

    private class GroupListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedValue = searchResultsList.getSelectedValue();
                    if (selectedValue != null && selectedValue.startsWith("Group: ")) {
                String groupName = selectedValue.split(": ")[1];

           if (e.getSource() == joinGroupButton) {
                   JOptionPane.showMessageDialog(SearchGui.this, "You have joined the group: " + groupName);
           } else if (e.getSource() == leaveGroupButton) {
                    JOptionPane.showMessageDialog(SearchGui.this, "You have left the group: " + groupName);
           } else if (e.getSource() == viewGroupButton) {
                    JOptionPane.showMessageDialog(SearchGui.this, "Viewing group: " + groupName);
                }
            } else {
                JOptionPane.showMessageDialog(SearchGui.this, "Please select a group first.");
            }
        }
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String s = searchField.getText().trim();
            if (!s.isEmpty()) {
                searchResultsModel.clear();

                List<User> userResults = searchManager.searchUsers(s);
                List<Group> groupResults = searchManager.searchGroups(s);

     for (User user : userResults) {
                    searchResultsModel.addElement("User: " + user.getUsername());
                }

      for (Group group : groupResults) {
                    searchResultsModel.addElement("Group: " + group.getGroupName());
                }

       if (searchResultsModel.isEmpty()) {
                    searchResultsModel.addElement("No results found.");
                }
            }
        }
    }

    public static void main(String[] args) {
       

    userDatabase db = new userDatabase(new File("users.dat"));
    List<User> allUsers = db.load(); 

    if (allUsers.isEmpty()) {
        System.out.println("No users found in the database.");
        return; 
    }

    User currentUser = allUsers.get(0); 

    List<Group> allGroups = new ArrayList<>();
    allGroups.add(new Group("Study Group"));
    allGroups.add(new Group("Sports Fans"));
    allGroups.add(new Group("Travel Enthusiasts"));

    SwingUtilities.invokeLater(() -> new SearchGui(currentUser, allUsers, allGroups));
}

}
