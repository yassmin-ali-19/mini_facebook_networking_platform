

package friend.manegment;

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
    public boolean sendFriendRequest() {
        if (friend.getBlockedUsers().contains(u.getUserId()) || u.getBlockedUsers().contains(friend.getUserId())) 
        {
//            System.out.println("User is blocked. Cannot send request.");
            friend.getFriendRequests().add(u.getUserId());
 
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
    
    
    
    
    public boolean blocking(){
        if (friend.getBlockedUsers().contains(u.getUserId()) || u.getBlockedUsers().contains(friend.getUserId())) 
        {
//            System.out.println("User already blocked");
            return false;
        }
        u.getBlockedUsers().add(friend.getUserId());
        u.getFriendRequests().remove(friend.getUserId());
        u.getFriends().remove(friend.getUserId());
        friend.getFriendRequests().remove(u.getUserId());
        friend.getFriends().remove(u.getUserId());
             saveUsers();   
//        System.out.println("User blocked");
        return true;
           }
    
    public boolean accept(){
    
     if(u.getFriendRequests().contains(friend.getUserId())){
          u.getFriends().add(friend.getUserId());
//            System.out.println("Accepted friend request!");
//          System.out.println("Friend added!");
          
          u.getFriendRequests().remove(friend.getUserId());
          friend.getFriends().add(u.getUserId());
          saveUsers();
          return true;
    }  
     
//     System.out.println("No friend request found.");
return false;
    
    }
    
   public boolean remove(){
    
      if(u.getFriendRequests().contains(friend.getUserId())){

          u.getFriendRequests().remove(friend.getUserId());
          
//            System.out.println("Removed request");
saveUsers();
          return true;}
//    System.out.println("No friend request found.");
return false;
   }
   
   
public static List<User> suggestions(File file,User u){


  userDatabase  db = new userDatabase(file);
       List<User> users = db.loadUsers();
         
        List<User> sug =new ArrayList<>();
       //a loop to see relation 
      for(User rec:users){
       if(!u.getFriends().contains(rec.getUserId())|| !u.getBlockedUsers().contains(rec.getUserId()) 
               || !rec.getBlockedUsers().contains(u.getUserId())  ) 
       {      sug.add(rec);
       }   }   
 return sug;   }


 // Save the updated user list to the database
    private void saveUsers() {
        userDatabase db = new userDatabase(file);
        List<User> users = db.loadUsers();
        db.saveUser(users);
    }


}