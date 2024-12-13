package facebookcheck;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Profile extends JFrame {
    private String email;
    private JLabel profileLabel;
    private JLabel coverLabel;
    private JButton friendspage;
    private JTextField bioField;
    private JComboBox<String> comboBox;
    private ProfileManagement profile;
    private profileDatabase data;
    private ArrayList<ProfileManagement> profiles;
    private File datafile = new File("profiles.json"); // Save in JSON file

    public Profile(String email) {
        this.email=email;
        this.data = new profileDatabase(datafile);
        this.profiles = (ArrayList<ProfileManagement>) data.load();

        if (this.profiles == null) {
            this.profiles = new ArrayList<>();
        }

        this.profile = findProfileByEmail(email);
        if (this.profile == null) {
            this.profile = new ProfileManagement(email, null, null, "", "");
            profiles.add(this.profile);
        }

        initComponents();
    }

    private ProfileManagement findProfileByEmail(String email) {
        for (ProfileManagement p : profiles) {
            if (p.getEmail().equals(email)) {
                return p;
            }
        }
        return null;
    }

    private void initComponents() {
        profileLabel = new JLabel();
        coverLabel = new JLabel();

        // Load profile photo
        if (profile.getProfilePhotoPath() != null) {
            setImageToLabel(profileLabel, profile.getProfilePhotoPath(), 150, 150);
        }

        // Load cover photo
        if (profile.getCoverPhotoPath() != null) {
            setImageToLabel(coverLabel, profile.getCoverPhotoPath(), 800, 200);
        }

        bioField = new JTextField(20);
        bioField.setText(profile.getBio());

        comboBox = new JComboBox<>(new String[]{"Update Profile Photo", "Update Cover Photo", "Update Bio", "Update Password","friends"});
        comboBox.addActionListener(e -> handleAction((String) comboBox.getSelectedItem()));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(coverLabel);
        panel.add(profileLabel);
        panel.add(new JLabel("Bio:"));
        panel.add(bioField);
        panel.add(comboBox);

        add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 700);
        setVisible(true);
    }

    private void setImageToLabel(JLabel label, String filePath, int width, int height) {
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(scaledImg));
        } catch (IOException e) {
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    private void handleAction(String action) {
        switch (action) {
            case "Update Profile Photo":
                updateProfilePhoto();
                break;
            case "Update Cover Photo":
                updateCoverPhoto();
                break;
            case "Update Bio":
                updateBio();
                break;
            case "Update Password":
                updatePassword();
                break;
            case "friends":
                FriendManagementFrame window=new FriendManagementFrame(this.email);
                window.setVisible(true);
                break;
        }
    }

    private void updateProfilePhoto() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            profile.setProfilePhotoPath(selectedFile.getAbsolutePath());
            data.save(profiles);
            setImageToLabel(profileLabel, selectedFile.getAbsolutePath(), 150, 150);
        }
    }

    private void updateCoverPhoto() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            profile.setCoverPhotoPath(selectedFile.getAbsolutePath());
            data.save(profiles);
            setImageToLabel(coverLabel, selectedFile.getAbsolutePath(), 800, 200);
        }
    }

    private void updateBio() {
        String newBio = JOptionPane.showInputDialog("Enter new bio:");
        if (newBio != null && !newBio.isEmpty()) {
            profile.setBio(newBio);
            bioField.setText(newBio);
            data.save(profiles);
        }
    }

    private void updatePassword() {
        String newPassword = JOptionPane.showInputDialog("Enter new password:");
        if (newPassword != null && !newPassword.isEmpty()) {
            profile.setPassword(newPassword);
            data.save(profiles);
        }
    }
}
