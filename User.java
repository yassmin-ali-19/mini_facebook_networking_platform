/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user.account;


import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class User {
   private String userId, 
           email ,
           username,
           password, dateOfBirth;
   private String status;

  
    public User(String userId, String email, String username, String password, String dateOfBirth, String status) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.setPassword(password); // Automatically hash the password
        this.dateOfBirth = dateOfBirth;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    
    
// Hash password then set it
    public void setPassword(String password) {
     try {
            this.password = passwordHasher.hasher(password); // Hash the password
        } catch (NoSuchAlgorithmException e) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Password hashing failed", e);
        }
    }

    public  String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth( String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
