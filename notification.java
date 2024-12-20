
package facebookcheck;

//import user.account.User;



public class notification {
    
   private User u; // User who the notification is related to
   private String type; // Type of notification (friend request, group activity, new post)
   private boolean opened; // Whether the notification is opened or not

    public notification(User u, String type) {
        this.u = u;
        this.type = type;
        this.opened = false;
    }

    public User getU() {
        return u;
    }

    public String getType() {
        return type;
    }

    public boolean isOpened() {
        return opened;
    }

    public void close() {
        this.opened = true;
    }  
   
}
