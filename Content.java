
package facebookcheck;

/**
 *
 * @author Elnour Tech
 */
import java.io.File;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import org.json.JSONException;

public class Content {
    private String contentId;
    private String authoremail;
    private String content;
    private String image;
    private LocalDateTime timestamp;
    private String contentType; // post or story
    private int likesnum;
    private ArrayList comments;
    

    public Content(String contentId, String authoremail, String content, String image, LocalDateTime timestamp, String contentType) throws JSONException {
        this.contentId = contentId;
        this.authoremail = authoremail;
        this.content = content;
        this.image = image;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
        this.contentType = contentType; // post or story
        this.likesnum=0;
        this.comments=new ArrayList<String>();
    }

    
    public String getContentId() {
        return contentId;
    }

    public String getAuthoremail() {
        return authoremail;
    }

    public String getContent() {
        return content;
    }

    public String getImagePath() {
        return image;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getContentType() {
        return contentType;
    }
    public void inclikes() throws JSONException {
    this.likesnum++; // Increment the like count
}

public void declikes() throws JSONException {
    if (this.likesnum > 0) {
        this.likesnum--; // Decrement the like count
    }
}
     public int numberoflikes() throws JSONException
     {
         return this.likesnum;
         
     }
    public void addComment(String comment) throws JSONException
    {
        this.comments.add(comment);
    }
    public ArrayList getComments()
    {
        return this.comments;
    }
}