

package com.mycompany.useraccount;

import java.util.List;


public class Logout {
   
    String email;
    List<User> users;
    userDatabase db;
    public Logout(String email, List<User> users,  userDatabase db) {
        this.email = email;
        this.users = users;
        this.db=db;
    }
     
     public void logout(){
     
       for(User u:users){
   if(u.getEmail().equals(email)){
     u.setStatus("offline"); 
       System.out.println("Logging out was successful");
   
       db.saveUser(users);
      
     return;
     } }
     System.out.println("Email not found");
     }
}
