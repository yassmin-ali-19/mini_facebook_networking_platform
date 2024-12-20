package FrontEnd;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class ProfileManagment extends JFrame {

    private JLabel profileLabel;
    private JLabel coverLabel;
    private JTextField bioField;
    private JComboBox<String> comboBox;

    public ProfileManagment() {
        setTitle("Profile Management");
        initComponents();
    }

    private void initComponents() {
        profileLabel = new JLabel();
        coverLabel = new JLabel();
        bioField = new JTextField(20);
        comboBox = new JComboBox<>();

        profileLabel.setPreferredSize(new Dimension(150, 150));
        coverLabel.setPreferredSize(new Dimension(300, 100));

        comboBox.addItem("Update Profile");
        comboBox.addItem("Update Profile Photo");
        comboBox.addItem("Update Cover Photo");
        comboBox.addItem("Update Password");
        comboBox.addItem("Update Bio");

        JPanel panel = new JPanel();
        panel.add(new JLabel("Profile photo:"));
        panel.add(Box.createVerticalStrut(50));
        panel.add(profileLabel);
        panel.add(Box.createVerticalStrut(50));
        panel.add(new JLabel("Cover photo:"));
        panel.add(coverLabel);
        panel.add(Box.createVerticalStrut(50));
        panel.add(new JLabel("Bio:"));
        panel.add(bioField);
        panel.add(Box.createVerticalStrut(10));
        panel.add(comboBox);
        comboBox.addActionListener(e -> handleAction((String) comboBox.getSelectedItem()));

        add(panel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 500);
        setResizable(false);
        setVisible(true);
    }

    private void handleAction(String action) {
        switch (action) {
            case "Update Profile Photo":
                updatePhoto(profileLabel);
                break;
            case "Update Cover Photo":
                updatePhoto(coverLabel);
                break;
            case "Update Bio":
                updateBio();
                break;
            case "Update Password":
                updatePassword();
                break;
            default:
                break;
        }
    }

    private void updatePhoto(JLabel label) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select Image");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            String filePath = file.getAbsolutePath();

            if (filePath.endsWith(".jpg") || filePath.endsWith(".png")) {
                try {
                    BufferedImage img = ImageIO.read(file);
                    Image scaledImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaledImg));
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(this, "Error loading image: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a JPG or PNG file.");
            }
        }
    }

    private void updateBio() {
        String newBio = JOptionPane.showInputDialog(this, "Enter your new bio:");
        if (newBio != null && !newBio.trim().isEmpty()) {
            bioField.setText(newBio);
            JOptionPane.showMessageDialog(this, "Bio updated successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Bio cannot be empty!");
        }
    }

    private void updatePassword() {
        String currentPassword = JOptionPane.showInputDialog("Enter your current password:");
        if (currentPassword != null && !currentPassword.isEmpty()) {
            String newPassword = JOptionPane.showInputDialog("Enter your new password:");
            if (newPassword != null && !newPassword.isEmpty()) {
                String confirmPassword = JOptionPane.showInputDialog("Confirm your new password:");
                if (newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(null, "Password updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "New password and confirmation do not match.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "New password cannot be empty.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Current password cannot be empty.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ProfileManagment::new);
    }
}
