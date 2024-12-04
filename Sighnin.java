
package com.mycompany.useraccount;


import java.util.List;

import java.util.regex.Pattern;


public class Sighnin {
     private String email;
    private String username;
    private String password;
    private String dateOfBirth;
    private List<User> users; // List of existing users
    private userDatabase db; // Database object
     
    public Sighnin(String email, String username, String password, String dateOfBirth, List<User> users, userDatabase db) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.users = users;
        this.db = db;
        
    }
    
    public boolean signIn(){
    
    if(!emailValid(email)){
        System.out.println("Failed to sign in");
    return false;
    }
 
         User use= new User(String.valueOf(System.currentTimeMillis()),email,username,password,dateOfBirth,"offline");
     
         db.saveUser(users);
         
         System.out.println("Sign in successful");
         
    return true;
    }
   public boolean emailValid(String email){
    
        String emailV = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailV, email);
    }
    
}
