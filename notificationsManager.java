package user.account.friendManagement;



import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;
import user.account.User;



public class notificationsManager {
    private File file;

    public notificationsManager(File file) {
     this.file = file;        
    }

 
    
    private static List<notification> loadNotifications() {
        File file = new File("notifications.txt");
        
        if (!file.exists()) {
            return new ArrayList<>(); // Return empty list if file doesn't exist
        }

        Gson gson = new Gson();
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, new TypeToken<List<notification>>() {}.getType());
        } catch (IOException e) {
        System.out.println("Problem in loading");
            return new ArrayList<>(); // Return empty list if an error occurs
        }
    }

    
  private static void saveNotifications(List<notification> notifications) {
    Gson gson = new GsonBuilder().setPrettyPrinting().create();
    File file = new File("notifications.txt");
    try (FileWriter writer = new FileWriter(file)) {
        gson.toJson(notifications, writer);
    } catch (IOException e) {
        System.out.println("Problem in saving");
    }
}

   
    
    public static void addNotification(notification y) {
        List<notification> allNotifications = loadNotifications();

        // Check if the notification already exists
        boolean exists = false;
      for (notification notif : allNotifications) {
            if (notif.equals(y)) {
                exists = true; //exists
                System.out.println("Notification exists");
                break;
            }
        }
      
        // Add the new notification if it doesn't exist
        if (!exists) {
            allNotifications.add(y); // Add the new notification
            saveNotifications(allNotifications); // Save the updated list
            System.out.println("New notification added: " +y.getType());
        }
    }

    // Mark a notification as opened.
   
    public void markNotificationAsOpened(notification y) {
        List<notification> allNotifications = loadNotifications();

        // Iterate over the notifications and mark the matching one as opened
        for (notification notif : allNotifications) {
            if (notif.equals(y)) {
                y.close(); // Mark as opened
                System.out.println("Notification opened");
                break;
            }
        }

        saveNotifications(allNotifications); // Save the updated list
    }

     public void displayNotifications(User currentUser) {
    List<notification> allNotifications = loadNotifications();

    // Filter notifications that have not been opened
    List<notification> unopenedNotifications = new ArrayList<>();
    for (notification notif : allNotifications) {
        if (!notif.isOpened()) {
            unopenedNotifications.add(notif);
        }
    }

    // Display unopened notifications
    if (unopenedNotifications.isEmpty()) {
        System.out.println("No new notifications.");
    } else {
        for (notification notif : unopenedNotifications) {
            System.out.println("Notification - Type: " + notif.getType() + ", User ID: " + notif.getU().getUserId() + ", Opened: " + notif.isOpened());

            // Display GUI based on notification type
            switch (notif.getType()) {
                case "gotreq":
                    // Open friend request GUI
                    User sender = notif.getU(); // Assuming this holds the sender's user information
                    File file = new File("jjson.txt");
                    File notify = new File("notifications.txt");
                    new frndreqgui(currentUser, sender, file, notify).setVisible(true);
                    break;

                case "statuschanged":
                    // Open status change GUI
                    new statuschange().setVisible(true);
                    break;

                case "addedpost":
                    // Open added post GUI
                    new addedpost().setVisible(true);
                    break;
                    
                     case "comment":
                    // Open added comment GUI
                
                         
                         
                    break;
                     case "newmessage":
                    // Open added newmessage GUI
                   
                         
                         
                    break;

                default:
                    System.out.println("Unknown notification type: " + notif.getType());
            }

            // Mark notification as opened
            notif.close();
        }

        // Save updated notifications
        saveNotifications(allNotifications); 
    }
}
     public static void notifyOwner(User owner) {
    notificationsManager.addNotification(new notification(owner, "newmessage"));
     }
public static void notifyPostOwner(User postOwner) {
    notificationsManager.addNotification(new notification(postOwner, "comment"));
}

}