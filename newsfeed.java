package facebookcheck;

import static facebookcheck.notificationsManager.notifyPostOwner;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;

public class newsfeed extends JFrame {
    private String email;
    private JPanel postPanel;
    private JPanel storyPanel;
    private ContentManager contentManager;
    File file2 = new File("notifications.txt");
    notificationsManager notify=new notificationsManager(file2);
    private int likeCount = 0; // Initialize like counter
    private boolean isLiked = false;
  private File file = new File("jjson.txt");
  userDatabase db=new userDatabase(file);
  private User u;

    public newsfeed(String email) throws JSONException {
        this.email = email;

        // Initialize JFrame properties
        setTitle("News Feed");
        setSize(1400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Initialize ContentManager
        contentManager = new ContentManager(this.email);

        // Initialize panels
        postPanel = new JPanel();
        postPanel.setLayout(new BoxLayout(postPanel, BoxLayout.Y_AXIS));

        storyPanel = new JPanel();
        storyPanel.setLayout(new BoxLayout(storyPanel, BoxLayout.Y_AXIS));

        // Add panels to the frame
        add(new JScrollPane(postPanel), BorderLayout.WEST);
        add(new JScrollPane(storyPanel), BorderLayout.CENTER);

        // Bottom panel for actions
        JPanel bottomButtonsPanel = new JPanel(new FlowLayout());
        JButton addPostButton = new JButton("Add Post");
        JButton addStoryButton = new JButton("Add Story");
        JButton notifications = new JButton("notifications");
        
        bottomButtonsPanel.add(addPostButton);
        bottomButtonsPanel.add(addStoryButton);
        bottomButtonsPanel.add(notifications);
        add(bottomButtonsPanel, BorderLayout.SOUTH);

        // Button actions
        addPostButton.addActionListener(e -> addNewPost());
        addStoryButton.addActionListener(e -> addNewStory());
        notifications.addActionListener(e -> notify.displayNotifications(u));

        // Populate initial data
        populateSampleData();

        setVisible(true);
    }

    private void populateSampleData() throws JSONException {
        loadPostsFromContentManager();
        loadStoriesFromContentManager();
    }

    public void loadPostsFromContentManager() throws JSONException {
        List<Content> allContents = contentManager.getVisibleContents(this.email);
        postPanel.removeAll(); // Clear previous posts

        for (Content content : allContents) {
            if (content.getContentType().equals("post")) {
                JPanel postItemPanel = new JPanel(new BorderLayout());
                postItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Use Displayimage to render text and image
                Displayimage displayComponent = new Displayimage();
                String postDisplay = content.getAuthoremail() + ": " + content.getContent();
                if (content.getImagePath() != null) {
                    postDisplay += "\n" + content.getImagePath();
                }
                displayComponent.getListCellRendererComponent(null, postDisplay, 0, false, false);

                postItemPanel.add(displayComponent, BorderLayout.CENTER);

                // Add buttons below the post
                JPanel buttonPanel = new JPanel(new FlowLayout());
                JButton likeButton = new JButton("Like (0)");
                JButton showCommentsButton = new JButton("Show Comments");
                JButton addCommentButton = new JButton("Add Comment");

               likeButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            // Toggle the like state
            if (isLiked) {
                likeCount--; // Decrease like count
                likeButton.setText("Like (" + likeCount + ")");
                likeButton.setBackground(Color.LIGHT_GRAY);
                content.declikes(); // Call to decrease likes
            } else {
                likeCount++; // Increase like count
                likeButton.setText("Liked (" + likeCount + ")");
                likeButton.setBackground(Color.GREEN);
                content.inclikes(); // Call to increase likes
            }
            isLiked = !isLiked; // Toggle the state
        } catch (JSONException ex) {
            Logger.getLogger(newsfeed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
});
                showCommentsButton.addActionListener(new ActionListener() {
            @Override
    public void actionPerformed(ActionEvent e) {
        CommentsWindow commentsWindow = new CommentsWindow(content);
//        commentsWindow.setVisible(true);
    }
        });
                addCommentButton.addActionListener(e -> {
    String comment = JOptionPane.showInputDialog("Enter your comment:");
    if (comment != null && !comment.trim().isEmpty()) {
        try {
            content.addComment(comment); // Add the comment to the content
            ArrayList<User> array = new ArrayList<>(db.load());
            for (User user : array) {
                if (user.getEmail().equals(this.email)) {
                    u = user; // Find the user
                    break;
                }
            }
            notifyPostOwner(u); // Notify the post owner
            JOptionPane.showMessageDialog(null, "Comment added."); // Confirmation message
        } catch (JSONException ex) {
            Logger.getLogger(newsfeed.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
});

                buttonPanel.add(likeButton);
                buttonPanel.add(showCommentsButton);
                buttonPanel.add(addCommentButton);

                postItemPanel.add(buttonPanel, BorderLayout.SOUTH);
                postPanel.add(postItemPanel);
            }
            
        }

        postPanel.revalidate();
        postPanel.repaint();
    }
    public void likeaction()
    {
        
    }

    private void loadStoriesFromContentManager() throws JSONException {
        List<Content> allContents = contentManager.getVisibleContents(this.email);
        storyPanel.removeAll(); // Clear previous stories

        for (Content content : allContents) {
            if (content.getContentType().equals("story")) {
                JPanel storyItemPanel = new JPanel(new BorderLayout());
                storyItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                // Use Displayimage to render text and image
                Displayimage displayComponent = new Displayimage();
                String storyDisplay = content.getAuthoremail() + ": " + content.getContent();
                if (content.getImagePath() != null) {
                    storyDisplay += "\n" + content.getImagePath();
                }
                displayComponent.getListCellRendererComponent(null, storyDisplay, 0, false, false);

                storyItemPanel.add(displayComponent, BorderLayout.CENTER);

                storyPanel.add(storyItemPanel);
            }
        }

        storyPanel.revalidate();
        storyPanel.repaint();
    }

    private void addNewPost() {
        String newPost = JOptionPane.showInputDialog(this, "Enter your post content:");
        if (newPost != null && !newPost.trim().isEmpty()) {
            String imagePath = JOptionPane.showInputDialog(this, "Enter image path for the post (optional):");
            String postDisplay = "You: " + newPost + (imagePath != null && !imagePath.trim().isEmpty() ? "\n" + imagePath : "");
            Displayimage displayComponent = new Displayimage();
            displayComponent.getListCellRendererComponent(null, postDisplay, 0, false, false);

            JPanel postItemPanel = new JPanel(new BorderLayout());
            postItemPanel.add(displayComponent, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(new JButton("Like"));
            buttonPanel.add(new JButton("Show Comments"));
            buttonPanel.add(new JButton("Add Comment"));

            postItemPanel.add(buttonPanel, BorderLayout.SOUTH);
            postPanel.add(postItemPanel);

            postPanel.revalidate();
            postPanel.repaint();
        }
    }

    private void addNewStory() {
        String newStory = JOptionPane.showInputDialog(this, "Enter your story content:");
        if (newStory != null && !newStory.trim().isEmpty()) {
            String imagePath = JOptionPane.showInputDialog(this, "Enter image path for the story (optional):");
            String storyDisplay = "You: " + newStory + (imagePath != null && !imagePath.trim().isEmpty() ? "\n" + imagePath : "");
            Displayimage displayComponent = new Displayimage();
            displayComponent.getListCellRendererComponent(null, storyDisplay, 0, false, false);

            JPanel storyItemPanel = new JPanel(new BorderLayout());
            storyItemPanel.add(displayComponent, BorderLayout.CENTER);

            storyPanel.add(storyItemPanel);

            storyPanel.revalidate();
            storyPanel.repaint();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
//            try {
//                new newsfeed("user@example.com");
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
        });
    }
}
