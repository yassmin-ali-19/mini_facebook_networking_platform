

package user.account;

import java.io.File;
import java.util.List;

import java.util.regex.Pattern;


public class Sighnin {
     private String email;
    private String username;
    private String password;
    private String dateOfBirth;
  
    File file;
     
    public Sighnin(String email, String username, String password, String dateOfBirth, File file) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
          this.file=file;
        
    }
    
    public boolean signIn(){
    
         //loads the users from the gson file
           userDatabase db= new   userDatabase (file);
           List<User> users= db.loadUsers();
           
    if(!emailValid(email)){
        System.out.println("Failed to sign in");
    return false;
    }
    
   if(emailexist(email,file)){
   
   System.out.println("Email already exists!");
    return false;
   }
   
      if(usernameexists(username,file)){
   
   System.out.println("username already exists!");
    return false;
   }
   
   
         User use= new User(String.valueOf(System.currentTimeMillis()),email,username,password,dateOfBirth,"offline");
         users.add(use);
         db.saveUsers(users);
         
         System.out.println("Sign in successful");
         
    return true;
    }
   public static boolean emailValid(String email){
    
        String emailV = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailV, email);
    }

   
public  static boolean  emailexist(String email ,File file){

     userDatabase db= new userDatabase (file);
           List<User> users= db.loadUsers();
           
for(User u: users){
if(u.getEmail().equals(email)){
    
return true;
             }}
return false;
             }

public  static boolean  usernameexists(String username ,File file){

     userDatabase db= new userDatabase (file);
           List<User> users= db.loadUsers();
           
for(User u: users){
if(u.getUsername().equals(username)){
    
return true;
             }}
return false;
             }
   
}