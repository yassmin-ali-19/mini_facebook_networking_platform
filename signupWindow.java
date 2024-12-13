package facebookcheck;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import static facebookcheck.Sighnup.emailValid;
import static facebookcheck.Sighnup.emailexist;
import facebookcheck.NewJFrame;

public class signupWindow extends JFrame {

    private JTextField email, username;
    private JPasswordField password; // Changed to JPasswordField
    private JButton signin, returnButton; // Declare return button
    private JDateChooser jDateChooser1;

    // Constructor to set up the GUI
    public signupWindow() {
        setTitle("Sign up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 153, 153));
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for components

        // Create labels and input fields
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.WHITE);
        email = new JTextField(20);
//        email.setBounds(100, 10, 60, 10);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.WHITE);
        username = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Color.WHITE);
        password = new JPasswordField(20); // Changed to JPasswordField

        JLabel dobLabel = new JLabel("Date of Birth");
        dobLabel.setForeground(Color.WHITE);
        jDateChooser1 = new JDateChooser();

        signin = new JButton("Sign In");
        signin.setForeground(new Color(0, 153, 153));
        signin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signinActionPerformed(e);
            }
        });

        // Create Return Button
        returnButton = new JButton("Return");
        returnButton.setForeground(new Color(0, 153, 153));
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                returnActionPerformed(e);  // Go back to NewJFrame
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
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(username, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(password, gbc); // Add JPasswordField

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(dobLabel, gbc);

        gbc.gridx = 1;
        panel.add(jDateChooser1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(signin, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(returnButton, gbc);  // Add the Return Button to the layout

        // Add panel to the frame
        add(panel, BorderLayout.CENTER);

        // Set size and location of the frame
        setSize(400, 300);
        setLocationRelativeTo(null);
    }

    // Action performed when the SignIn button is clicked
    private void signinActionPerformed(ActionEvent evt) {
        String emailText = email.getText().trim();
        String usernameText = username.getText().trim();
        String passwordText = new String(password.getPassword()).trim(); // Use getPassword() for JPasswordField

        // Validate the inputs
        if (emailText.isEmpty() || usernameText.isEmpty() || passwordText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Check if the Date of Birth is selected
        if (jDateChooser1.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Date of birth is required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validate email format
        if (!emailValid(emailText)) {
            JOptionPane.showMessageDialog(this, "Invalid Email!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Format the date from the DateChooser
        String dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").format(jDateChooser1.getDate());

        // File where user data is stored
        File file = new File("jjson.txt");

        // Ensure the email is unique
        if (emailexist(emailText, file)) {
            JOptionPane.showMessageDialog(this, "Email already Exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Use the Sighnin class to sign in
        Sighnup sighnin = new Sighnup(emailText, usernameText, passwordText, dateOfBirth, file);
        if (sighnin.signIn()) {
            JOptionPane.showMessageDialog(this, "Sign in successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
        } else {
            JOptionPane.showMessageDialog(this, "Sign in failed! Please check your details.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Action performed when the Return button is clicked
    private void returnActionPerformed(ActionEvent evt) {
        // Open the NewJFrame window
        NewJFrame newJFrame = new NewJFrame();
        newJFrame.setVisible(true);
        dispose();  // Close the current window
    }

    // Main method to run the application
    public static void main(String args[]) {
        // Create and display the form
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new signupWindow().setVisible(true);
            }
        });
    }
}
