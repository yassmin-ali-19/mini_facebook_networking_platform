
package user.account.friendManagement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import user.account.*;


public class Friend {
 private User u,friend;
    private File file;

    public Friend(User u, User friend, File file) {
        this.friend =friend;
        this.u = u;
        this.file = file;
    }

    // Send a friend request
    public void sendFriendRequest() {
        if (friend.getBlockedUsers().contains(u) || u.getBlockedUsers().contains(friend)) 
        {
            System.out.println("User is blocked. Cannot send request.");
            return;
        }

        if (u.getFriends().contains(friend)) {
            System.out.println("Already friends.");
            return;
        }

        if (friend.getFriendRequests().contains(u)) {
            System.out.println("Friend request already sent.");
            return;
        }
         if (u.getFriendRequests().contains(friend)) {
             u.getFriends().add(friend);
            System.out.println("Accepted their request!");
            return;
        }
        

      friend.getFriendRequests().add(u);
 
      saveUsers();
        System.out.println("Friend request sent.");
    }
    
    
    
    
    public void blocking(){
        if (friend.getBlockedUsers().contains(u) || u.getBlockedUsers().contains(friend)) 
        {
            System.out.println("User already blocked");
            return;
        }
        u.getBlockedUsers().add(friend);
        u.getFriendRequests().remove(friend);
        u.getFriends().remove(friend);
        friend.getFriendRequests().remove(u);
        friend.getFriends().remove(u);
             saveUsers();   
        System.out.println("User blocked");
        
           }
    
    public void accept(){
    
     if(u.getFriendRequests().contains(friend)){
          u.getFriends().add(friend);
            System.out.println("Accepted friend request!");
          System.out.println("Friend added!");
          
          u.getFriendRequests().remove(friend);
          friend.getFriends().add(u);
          saveUsers();
          return;
    }  
     
     System.out.println("No friend request found.");
    
    }
    
   public void remove(){
    
      if(u.getFriendRequests().contains(friend)){

          u.getFriendRequests().remove(friend);
          
            System.out.println("Removed request");
saveUsers();
          return;}
    System.out.println("No friend request found.");
   }
   
   public List<User> suggestions() {
    userDatabase db = new userDatabase(file);
    List<User> users = db.loadUsers();
    List<User> sug = new ArrayList<>();

    for (User rec : users) {
        if (!rec.equals(u) // Exclude self
                && !u.getFriends().contains(rec) // Not already a friend
                && !u.getBlockedUsers().contains(rec) // Not blocked by the user
                && !rec.getBlockedUsers().contains(u)) { // Not blocked by them
            sug.add(rec);
        }
    }
    return sug;
}


 // Save the updated user list to the database
    private void saveUsers() {
        userDatabase db = new userDatabase(file);
        List<User> users = db.loadUsers();
        db.saveUsers(users);
    }

}