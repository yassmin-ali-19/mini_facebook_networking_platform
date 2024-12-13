package facebookcheck;


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
import javax.swing.text.AbstractDocument.Content;
import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.undo.UndoableEdit;
/**
 *
 * @author Elnour Tech
 */

public class ContentManager {
    private List<Content> contents = new ArrayList<>();
    private static final String CONTENT_DATABASE_FILE = "contents.json";
       private List<String> blockedUsers = new ArrayList<>();
    public ContentManager() throws JSONException {
        loadContents();
        deleteExpiredStories();
    }

private void loadContents() throws JSONException {
    try {
        String json = new String(Files.readAllBytes(Paths.get(CONTENT_DATABASE_FILE)));
        JSONArray arr = new JSONArray(json);
        contents.clear();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject j = arr.getJSONObject(i);
            String contentId = j.getString("contentId");
            String authorId = j.getString("authorId");
            String content = j.getString("content");
            String image = j.has("imagePath") ? j.getString("imagePath") : null;

            LocalDateTime timestamp = j.has("timestamp")
                    ? LocalDateTime.parse(j.getString("timestamp"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    : LocalDateTime.now();

            String contentType = j.has("contentType") ? j.getString("contentType") : "post"; // Default to "post"

            contents.add(new Content(contentId, authorId, content, image, timestamp, contentType) {
                @Override
                public Position createPosition(int offset) throws BadLocationException {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public int length() {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public UndoableEdit insertString(int where, String str) throws BadLocationException {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public UndoableEdit remove(int where, int nitems) throws BadLocationException {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public String getString(int where, int len) throws BadLocationException {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }

                @Override
                public void getChars(int where, int len, Segment txt) throws BadLocationException {
                    throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
                }
            });
        }
    } catch (IOException e) {
        throw new RuntimeException("Could not load contents: " + e.getMessage(), e);
    }
}

    private void saveContents() throws JSONException {
        JSONArray contentsArray = new JSONArray();
        for (Content content : contents) {
            JSONObject j = new JSONObject();
            j.put("contentId", content.getContentId());
            j.put("authorId", content.getAuthorId());
            j.put("content", content.getContent());
            j.put("imagePath", content.getImagePath());
            j.put("timestamp", content.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
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

   public void createPost(String authorId, String content, String imagePath) throws JSONException {
    String contentId = UUID.randomUUID().toString();
    Content post = new Content(contentId, authorId, content, imagePath, LocalDateTime.now(), "post");
    contents.add(post);
    saveContents();
}

public void createStory(String authorId, String content, String imagePath) throws JSONException {
    String contentId = UUID.randomUUID().toString();
    Content newStory = new Content(contentId, authorId, content, imagePath, LocalDateTime.now(), "story");
    contents.add(newStory);
    saveContents();
}


    private void deleteExpiredStories() throws JSONException {
        contents.removeIf(content -> content.getTimestamp().plusHours(24).isBefore(LocalDateTime.now()));
        saveContents();
    }

  
     public void blockUser(String userId) {
        blockedUsers.add(userId);
   
    }

    public void unblockUser(String userId) {
        blockedUsers.remove(userId);
       
    }

    public List<Content> getVisibleContents(String userId) {
        List<Content> visibleContents = new ArrayList<>();
        for (Content c : contents ) {
            if (!blockedUsers.contains(c.getAuthorId()) && !c.getAuthorId().equals(userId)) {
                visibleContents.add(c);
            }
        }
        return visibleContents;
    }
}