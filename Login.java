package com.mycompany.useraccount;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Login {
    String email,password;
     List<User> users;
    userDatabase db;
    public Login(String email, String password, List<User> users,userDatabase db) {
        this.email = email;
        this.password = password;
        this.users = users;
        this.db=db;
    }
     
     public boolean loggin(){
      
     for(User u:users){
   if( u.getEmail().equals(email)){
       try { 
//sees if the password equals after hashing
           if(u.getPassword().equals(passwordHasher.hasher(password))){
               //if found and correct logs in and sets to online
              u.setStatus("online");
               System.out.println("Login successful!");

               db.saveUser(users);
               
           return true;} else {
            System.out.println("Incorrect password");
          return false;  
           }
       } catch (NoSuchAlgorithmException ex) {
           Logger.getLogger(Login.class.getName()).log(Level.SEVERE, "Error in password hashing", ex);
       }
     }
System.out.println("Email not found");
     } return false;
 
     }
     
}
