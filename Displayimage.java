package facebookcheck;

import javax.swing.*;
import java.awt.*;

public class Displayimage extends JPanel implements ListCellRenderer<String> {
    private JLabel textLabel;
    private JLabel imageLabel;

    public Displayimage() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  // Stack text and image vertically

        // Initialize components
        textLabel = new JLabel();
        textLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(400, 300)); // Set a size for the image

        add(textLabel);
        add(imageLabel);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index, boolean isSelected, boolean cellHasFocus) {
        // Set text
        textLabel.setText(value.split("\n")[0]);  // Display text only (first line)

        // Check if there is an image (based on the post format)
        String imagePath = value.split("\n").length > 1 ? value.split("\n")[1] : null;
        if (imagePath != null) {
            try {
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage();
                Image scaledImage = image.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImage));  // Show the image
            } catch (Exception e) {
                imageLabel.setIcon(null);  // Clear image if invalid
            }
        } else {
            imageLabel.setIcon(null);  // No image to show
        }

        // Set selection style (highlight the item when selected)
        if (isSelected) {
            setBackground(new Color(200, 200, 255));  // Highlight selected item
        } else {
            setBackground(Color.WHITE);
        }

        return this;
    }
}