package facebookcheck;

import java.time.LocalDateTime;
import java.time.LocalDateTime;

public class Content {
    private String contentId;
    private String authorId;
    private String content;
    private String image;
    private LocalDateTime timestamp;
    private String contentType; // "post" or "story"

    public Content(String contentId, String authorId, String content, String image, LocalDateTime timestamp, String contentType) {
        this.contentId = contentId;
        this.authorId = authorId;
        this.content = content;
        this.image = image;
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
        this.contentType = contentType; // "post" or "story"
    }

    // Getters
    public String getContentId() {
        return contentId;
    }

    public String getAuthorId() {
        return authorId;
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
}