/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facebookcheck;

import java.io.File;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 *
 * @author Elnour Tech
 */

public class ContentManager {
    private String email;
    private List<Content> contents = new ArrayList<>();
    private static final String CONTENT_DATABASE_FILE = "contents.json";
    private userDatabase db=new userDatabase(new File("jjson.txt"));
       private List<String> blockedUsers = new ArrayList<>();
    public ContentManager(String email) throws JSONException {
        this.email=email;
        loadContents();
        deleteExpiredStories();
    }

public void loadContents() throws JSONException {
    File contentFile = new File(CONTENT_DATABASE_FILE);
    if (!contentFile.exists() || contentFile.length() == 0) {
        contents.clear();
        return;
    }

    try {
        String json = new String(Files.readAllBytes(Paths.get(CONTENT_DATABASE_FILE)));
        JSONArray arr = new JSONArray(json);
        contents.clear();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject j = arr.getJSONObject(i);
            String contentId = j.getString("contentId");
            String authoremail = j.getString("authoremail");
            String content = j.getString("content");
            int likesnum = j.has("likesnum") ? j.getInt("likesnum") : 0;
            String image = j.has("imagePath") ? j.getString("imagePath") : null;
            ArrayList<String> comments = new ArrayList<>();
            if (j.has("comments")) {
                JSONArray commentsArray = j.getJSONArray("comments");
                for (int k = 0; k < commentsArray.length(); k++) {
                    comments.add(commentsArray.getString(k));
                }
            }

            LocalDateTime timestamp = LocalDateTime.parse(j.getString("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            String contentType = j.has("contentType") ? j.getString("contentType") : "contentType";

            Content loadedContent = new Content(contentId, authoremail, content, image, timestamp, contentType);
            for (String comment : comments) {
                loadedContent.addComment(comment);
            }
            for (int like = 0; like < likesnum; like++) {
                loadedContent.inclikes();
            }

            contents.add(loadedContent);
        }
    } catch (IOException e) {
        throw new RuntimeException("Could not load contents: " + e.getMessage(), e);
    }
}

    public void saveContents() throws JSONException {
    JSONArray contentsArray = new JSONArray();
    for (Content content : contents) {
        JSONObject j = new JSONObject();
        j.put("contentId", content.getContentId());
        j.put("authoremail", content.getAuthoremail());
        j.put("content", content.getContent());
        j.put("imagePath", content.getImagePath());
        j.put("timestamp", content.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        j.put("contentType", content.getContentType());
      j.put("likesnum", content.numberoflikes()); // Save the number of likes
j.put("comments", content.getComments()); // Save comments here
        contentsArray.put(j);
    }

    try (FileWriter file = new FileWriter(CONTENT_DATABASE_FILE)) {
        file.write(contentsArray.toString(2));
    } catch (IOException e) {
        throw new RuntimeException("Could not save contents: " + e.getMessage(), e);
    }
}
    public List<Content> getAllContents() {
        return new ArrayList<>(contents);
    }

   public void createPost(String authoremail, String content, String imagePath) throws JSONException {
    String contentId = UUID.randomUUID().toString();
    Content post = new Content(contentId, this.email, content, imagePath, LocalDateTime.now(), "post");
    contents.add(post);
    saveContents();
}

public void createStory(String authorId, String content, String imagePath) throws JSONException {
    String contentId = UUID.randomUUID().toString();
    Content newStory = new Content(contentId, this.email, content, imagePath, LocalDateTime.now(), "story");
    contents.add(newStory);
    saveContents();
}


    private void deleteExpiredStories() throws JSONException {
        contents.removeIf(content -> content.getTimestamp().plusHours(24).isBefore(LocalDateTime.now()));
        saveContents();
    }

  
//     public void blockUser(String userId) {
//        blockedUsers.add(userId);
//   
//    }
//
//    public void unblockUser(String userId) {
//        blockedUsers.remove(userId);
//       
//    }

public List<Content> getVisibleContents(String email){
        List<Content> allContents = new ArrayList<>(getAllContents());
        List<Content> visibleContents = new ArrayList<>();
        List<User> visibleusers = new ArrayList<>();
        
        
        for (Content c : allContents ) {
            if(c.getAuthoremail().equals(email))
                visibleContents.add(c);
            for(User u:db.load())
                for(User i:u.getFriends())
                {
                    if(email.equals(i.getEmail())){
                       visibleContents.add(c); 
                       visibleusers.add(i);
                    }
                }
        }
        return visibleContents;
    }
}