/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user.account;

import java.io.File;
import java.util.List;

/**
 *
 * @author Ahmed Kamel
 */

public class Logout {
   
    String email;
   File file;
    public Logout(String email, File file) {
        this.email = email;
        this.file=file;
    }
     
     public void logout(){
     
          //loads the users from the gson file
           userDatabase db= new   userDatabase (file);
           List<User> users= db.loadUsers();
           
           
       for(User u:users){
   if(u.getEmail().equals(email)){
     u.setStatus("offline"); 
       System.out.println("Logging out was successful");
   
       db.saveUsers(users);
      
     return;
     } }
     System.out.println("Email not found");
     }
}
