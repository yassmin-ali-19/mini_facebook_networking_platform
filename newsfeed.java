/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contentcreation;

import backend.ProfileManagement;
import facebook.Content;
import facebook.ContentManager;
import facebook.Gui;
import javax.swing.*;
import java.awt.*;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

public class newsfeed extends JFrame {

    private JTextArea contentArea;
    private JButton postButton;
    private JButton refreshButton;
    private JButton profileButton;
    private JButton showFriendsButton;
    private JButton showFriendSuggestionsButton;
    private DefaultListModel<String> postListModel;
    private DefaultListModel<String> storyListModel;
    private JList<String> postList;
    private JList<String> storyList;
    private ContentManager contentManager;

    public newsfeed() throws JSONException {
        setTitle("News Feed");
        setSize(1400, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create components
        contentArea = new JTextArea(3, 30);
        postButton = new JButton("Create Post");
        refreshButton = new JButton("Refresh");
        profileButton = new JButton("Profile");
        showFriendsButton = new JButton("Show Friends");
        showFriendSuggestionsButton = new JButton("Show Friend Suggestions");

        postListModel = new DefaultListModel<>();
        storyListModel = new DefaultListModel<>();
        postList = new JList<>(postListModel);
        storyList = new JList<>(storyListModel);

        // Create panels
        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel postPanel = new JPanel(new BorderLayout());
        JPanel storyPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Set up content area for posting
        JPanel postInputPanel = new JPanel();
        postInputPanel.add(new JLabel("What's on your mind?"));
        postInputPanel.add(postButton);

        contentPanel.add(postInputPanel, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(postList), BorderLayout.CENTER);

        // Set up post panel
        postPanel.add(new JLabel("Posts", JLabel.CENTER), BorderLayout.NORTH);
        postPanel.add(new JScrollPane(postList), BorderLayout.CENTER);

        // Set up story panel
        storyPanel.add(new JLabel("Stories", JLabel.CENTER), BorderLayout.NORTH);
        storyPanel.add(new JScrollPane(storyList), BorderLayout.CENTER);

        // Add components to the main frame
        add(contentPanel, BorderLayout.NORTH); // Posting section
        add(postPanel, BorderLayout.WEST); // Left panel for posts
        add(storyPanel, BorderLayout.CENTER); // Center panel for stories
        add(refreshButton, BorderLayout.SOUTH); 

        // Initialize ContentManager
        contentManager = new ContentManager();

        // Action listeners
        postButton.addActionListener(new PostButtonListener());
        refreshButton.addActionListener(new RefreshButtonListener());
        profileButton.addActionListener(new ProfileButtonListener());

        // Populate sample data
        populateSampleData();

        // Set custom renderer for the JLists
        postList.setCellRenderer(new Displayimage());
        storyList.setCellRenderer(new Displayimage());

        // Add the Profile button to the bottom
        bottomPanel.add(profileButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH); 

        // Add the "Show Friends" and "Show Friend Suggestions" buttons to the bottom
        JPanel bottomButtonsPanel = new JPanel(new FlowLayout());
        bottomButtonsPanel.add(showFriendsButton);
        bottomButtonsPanel.add(showFriendSuggestionsButton);
        add(bottomButtonsPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void populateSampleData() throws JSONException {
        String[] posts = loadPostsFromContentManager();
        String[] stories = loadStoriesFromContentManager();

        postListModel.clear();
        for (String post : posts) {
            postListModel.addElement(post);
        }

        storyListModel.clear();
        for (String story : stories) {
            storyListModel.addElement(story);
        }
    }

    private String[] loadPostsFromContentManager() throws JSONException {
        List<Content> allContents = contentManager.getVisibleContents("someUserId");
        String[] postArray = new String[allContents.size()];

        for (int i = 0; i < allContents.size(); i++) {
            Content content = allContents.get(i);
            String postDisplay = content.getAuthorId() + ": " + content.getContent();
            if (content.getImagePath() != null) {
                postDisplay += "\n" + content.getImagePath();
            }
            postArray[i] = postDisplay;
        }

        return postArray;
    }

    private String[] loadStoriesFromContentManager() throws JSONException {
        List<Content> allContents = contentManager.getVisibleContents("someUserId");
        String[] storyArray = new String[allContents.size()];

        for (int i = 0; i < allContents.size(); i++) {
            Content content = allContents.get(i);
            String storyDisplay = content.getAuthorId() + ": " + content.getContent();
            if (content.getImagePath() != null) {
                storyDisplay += "\n" + content.getImagePath();
            }
            storyArray[i] = storyDisplay;
        }

        return storyArray;
    }

    private class PostButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                new Gui().setVisible(true);
            } catch (JSONException ex) {
                Logger.getLogger(newsfeed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class RefreshButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                populateSampleData();
                JOptionPane.showMessageDialog(null, "Content refreshed.");
            } catch (JSONException ex) {
                Logger.getLogger(newsfeed.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private class ProfileButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ProfileManagement(); // Open ProfileManagement window
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new newsfeed(); 
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
