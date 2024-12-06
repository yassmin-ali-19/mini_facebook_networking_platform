/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package friend.manegment;
import java.util.ArrayList;
import java.util.List;
import org.json.*;
/**
 *
 * @author Yassmin
 */
public class Usercontentmanager extends ContentManager {
    private User user;

    public Usercontentmanager(User user) throws JSONException {
        super();
        this.user = user;
    }

    public List<Content> getVisibleContents() {
        List<Content> visibleContents = new ArrayList<>();
        for (Content content : super.getAllContents()) {
            if (!user.getBlockedUsers().contains(content.getAuthorId()) && 
                !content.getAuthorId().equals(user.getUserId())) {
                visibleContents.add(content);
            }
        }
        return visibleContents;
    }}
