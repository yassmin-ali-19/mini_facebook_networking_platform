/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facebookcheck;


import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Login {
    String email,password;
    File file;
    public Login(String email, String password,File file) {
        this.email = email;
        this.password = password;
        this.file=file;
    }
     
     public boolean loggin(){
      
         //loads the users from the gson file
           userDatabase db= new   userDatabase (file);
           List<User> users= db.load();
           
           
     for(User u:users){
   if( u.getEmail().equals(email)){
       try { 
//sees if the password equals after hashing
           if(u.getPassword().equals(passwordHasher.hasher(password))){
               //if found and correct logs in and sets to online
              u.setStatus("online");
               System.out.println("Login successful!");

               db.save(users);
             
           return true;} else {
            System.out.println("Incorrect password");
          return false;  
           }
       } catch (NoSuchAlgorithmException ex) {
             Logger.getLogger(Login.class.getName()).log(Level.SEVERE, "Error in password hashing", ex);
                    return false;
       }
     }

     }
     System.out.println("Email not found");
     return false;
 
     }
    
     
}
