/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facebookcheck;

/**
 *
 * @author Elnour Tech
 */
import javax.swing.*;
import java.awt.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.io.File;
import org.json.JSONException;

public class Gui extends JFrame {
    private ContentManager contentManager;
    private String imagePath;
    private JTextArea jTextArea1;
    private JLabel imageLabel;
    private String email;

    public Gui(String email) throws JSONException {
        this.email=email;
        initComponents();
        contentManager = new ContentManager(email);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        // Set up frame properties
        setTitle("Content Creator");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(new Color(240, 240, 240));

        // Create components
        JButton createStoryButton = createButton("Create Story");
        createStoryButton.addActionListener(evt -> createStory());

        JButton createPostButton = createButton("Create Post");
        createPostButton.addActionListener(evt -> createPost());

        JLabel contentLabel = new JLabel("Content");
        contentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        jTextArea1 = new JTextArea(5, 20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane jScrollPane1 = new JScrollPane(jTextArea1);

        JButton uploadPhotoButton = createButton("Upload Photo");
        uploadPhotoButton.addActionListener(evt -> uploadPhoto());

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(400, 400));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        // Layout setup
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(createStoryButton)
                    .addComponent(createPostButton))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(contentLabel)
                    .addComponent(jScrollPane1))
                .addGroup(layout.createSequentialGroup()
                    .addComponent(uploadPhotoButton)
                    .addComponent(imageLabel))
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(createStoryButton)
                    .addComponent(createPostButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(contentLabel)
                    .addComponent(jScrollPane1))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(uploadPhotoButton)
                    .addComponent(imageLabel))
        );

        pack();
    }

  private JButton createButton(String text) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 14));
    button.setBackground(new Color(59, 89, 152)); // Facebook blue
    button.setForeground(Color.WHITE);
    button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1)); // Simple line border
    button.setFocusPainted(false);
    button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

    // Add hover effect
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(new Color(47, 70, 120)); // Darker blue
        }
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(new Color(59, 89, 152));
        }
    });

    return button;
}

    private void createStory() {
        String content = jTextArea1.getText().trim();
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Content cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            contentManager.createStory(this.email, content, imagePath);
            JOptionPane.showMessageDialog(this, "Story created successfully!");
            clear();
        } catch (JSONException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating story: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void createPost() {
        String content = jTextArea1.getText().trim();
        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Content cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            contentManager.createPost(this.email, content, imagePath);
            JOptionPane.showMessageDialog(this, "Post created successfully!");
            clear();
        } catch (JSONException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creating post: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void uploadPhoto() {
  JFileChooser f = new JFileChooser();
    f.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images", "jpg", "jpeg", "png", "gif"));
        int userSelect = f.showOpenDialog(this);
          f.setDialogTitle("Select an Image");
        if (userSelect == JFileChooser.APPROVE_OPTION) {
            File file = f.getSelectedFile();
            imagePath = file.getAbsolutePath();
            displayImage(file);
        }
    }

  private void displayImage(File file) {
        ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    private void clear() {
        jTextArea1.setText("");
        imageLabel.setIcon(null);
        imagePath = null;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                try {
//                  String email="yassmin@gmail.com";
//                    
//                    new Gui(email); 
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }
} 