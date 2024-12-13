package facebookcheck;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
//import user.account.*;


public class Friend {
 private User u,friend;
 File file=new File("jjson.txt");
     userDatabase db=new userDatabase(file);

    public Friend(User u) {
        this.friend =friend;
        this.u = u;
//        this.file = file;
    }

    
    
     public boolean sendFriendRequest(User u,User friend) {
        if (friend.getBlockedUsers().contains(u.getUserId()) || u.getBlockedUsers().contains(friend.getUserId())) 
        {
//            System.out.println("User is blocked. Cannot send request.");
            ArrayList array=new ArrayList(u.getYourRequests());
           array.add(friend);
           u.setyourRequests(array);
 
      saveUsers();
      return true;
        }

//        if (u.getFriends().contains(friend.getUserId())) {
////            System.out.println("Already friends.");
//            return false;
//        }

        if (friend.getFriendRequests().contains(u.getUserId())||u.getFriends().contains(friend.getUserId())) {
//            System.out.println("Friend request already sent.");
            return false;
        }
////         if (u.getFriendRequests().contains(friend.getUserId())) {
////             u.getFriends().add(friend.getUserId());
////            System.out.println("Accepted their request!");
////            return;
//        }
        

//      friend.getFriendRequests().add(u.getUserId());
// 
//      saveUsers();
//        
return false;
    }
    
    
    
    
    public boolean blocking(User u,User friend){
        if (friend.getBlockedUsers().contains(u.getUserId()) || u.getBlockedUsers().contains(friend.getUserId())) 
        {
//            System.out.println("User already blocked");
            return false;
        }
         ArrayList array1=new ArrayList(u.getBlockedUsers());
           array1.add(friend);
           u.setyourRequests(array1);
//        u.getBlockedUsers().add(friend);
 ArrayList array2=new ArrayList( u.getFriendRequests());
           array2.remove(friend);
           u.setfriendRequests(array2);
//        u.getFriendRequests().remove(friend);
ArrayList array3=new ArrayList( u.getFriends());
           array3.remove(friend);
           u.setFriends(array3);
//        u.getFriends().remove(friend);
ArrayList array4=new ArrayList( friend.getFriendRequests());
           array4.remove(u);
           friend.setfriendRequests(array4);
//        friend.getFriendRequests().remove(u);
ArrayList array5=new ArrayList( friend.getFriends());
           array5.remove(u);
           friend.setFriends(array5);
//        friend.getFriends().remove(u);
             saveUsers();   
//             ###################contennnnntttttttttt checkkkkkkkkkk##############///////////////
//        System.out.println("User blocked");
        return true;
           }
    
    public boolean accept(User u,User friend){
    
     if(u.getFriendRequests().contains(friend.getUserId())){
         ArrayList array1=new ArrayList( u.getFriends());
           array1.add(friend);
           u.setFriends(array1);
//          u.getFriends().add(friend);
//            System.out.println("Accepted friend request!");
//          System.out.println("Friend added!");
           ArrayList array2=new ArrayList(u.getFriendRequests());
           array2.remove(friend);
           u.setfriendRequests(array2);
//          u.getFriendRequests().remove(friend.getUserId());
ArrayList array3=new ArrayList(friend.getFriends());
           array3.remove(u);
           friend.setfriendRequests(array3);
//          friend.getFriends().add(u);
          saveUsers();
          return true;
    }  
     
//     System.out.println("No friend request found.");
return false;
    
    }
    
   public boolean remove(User u,User friend){
    
      if(u.getFriendRequests().contains(friend.getUserId())){
          ArrayList array1=new ArrayList(  u.getFriendRequests());
           array1.remove(friend);
           u.setfriendRequests(array1);
//          u.getFriendRequests().remove(friend.getUserId());
          
//            System.out.println("Removed request");
saveUsers();
          return true;}
//    System.out.println("No friend request found.");
return false;
   }
   
   
public List<User> suggestions(User u) {
    userDatabase db = new userDatabase(this.file);
    List<User> users = db.load();
    List<User> sug = new ArrayList<>();

    for (User rec : users) {
        if (rec != null && 
            !rec.getUserId().equals(u.getUserId()) &&
            !u.getFriends().contains(rec.getUserId()) &&
            !u.getBlockedUsers().contains(rec.getUserId()) &&
            !rec.getBlockedUsers().contains(u.getUserId())) {
            sug.add(rec);
        }
    }
    u.setsuggested(sug);
    saveUsers();
    return sug;
}



  // Save the updated user list to the database
  private void saveUsers() {
    userDatabase db = new userDatabase(file);
    List<User> users = db.load();

    boolean userFound = false;
    boolean friendFound = false;

    for (int i = 0; i < users.size(); i++) {
        User currentUser = users.get(i);
        if (currentUser != null) {  // Check to prevent NullPointerException
            if (currentUser.equals(u)) {
                users.set(i, u); // Update the user's record
                userFound = true;
            } else if (currentUser.equals(friend)) {
                users.set(i, friend); // Update the friend's record
                friendFound = true;
            }
        }
        // Break early if both are updated
        if (userFound && friendFound) {
            break;
        }
    }
    // Add them if not found
    if (!userFound) {
        users.add(u);
    }
    if (!friendFound) {
        users.add(friend);
    }
    // Save the updated list
    db.save(users);
}



}