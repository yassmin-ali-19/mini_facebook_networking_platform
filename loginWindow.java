package user.account;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
import static user.account.Sighnin.emailValid;
import user.account.friendManagement.NewJFrame;

public class loginWindow extends JFrame {

    private JTextField email;
    private JPasswordField password;
    private JButton loginButton;
    private JButton returnButton; // Declare return button

    // Constructor to set up the GUI
    public loginWindow() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the panel with GridBagLayout for better alignment
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(0, 153, 153));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for components

        // Create labels and text fields for email and password
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        email = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        password = new JPasswordField(20);

        // Create login button
        loginButton = new JButton("Login");
        loginButton.setForeground(new Color(0, 153, 153));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginActionPerformed();
            }
        });

        // Create return button
        returnButton = new JButton("Return");
        returnButton.setForeground(new Color(0, 153, 153));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnActionPerformed(); // Go back to NewJFrame
            }
        });

        // Position components on the panel using GridBagConstraints
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        panel.add(email, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(password, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(loginButton, gbc);

        gbc.gridx = 1;
        panel.add(returnButton, gbc); // Add the Return Button to the layout

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);

        // Set size and location of the frame
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    // Action performed when login button is clicked
    private void loginActionPerformed() {
        String emailText = email.getText().trim();
        String passwordText = new String(password.getPassword()).trim();

        if (emailText.isEmpty() || passwordText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!emailValid(emailText)) {
            JOptionPane.showMessageDialog(this, "Invalid Email!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Let the user choose a file (e.g., database)
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File file = chooser.getSelectedFile();

        // Perform login
        Login loginmer = new Login(emailText, passwordText, file);
        if (loginmer.loggin()) {
            JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Login failed! Please check your email or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Action performed when the Return button is clicked
    private void returnActionPerformed() {
        // Open the NewJFrame window
        NewJFrame newJFrame = new NewJFrame();
        newJFrame.setVisible(true);
        dispose();  // Close the current window
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new loginWindow().setVisible(true);
            }
        });
    }
}
