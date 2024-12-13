/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facebookcheck;

import java.util.List;
import org.json.JSONException;

/**
 *
 * @author Yassmin
 */
public interface ContentInterface {

    public void loadContents() throws JSONException;

    public void saveContents() throws JSONException;

    public List<Content> getAllContents();

    public void createPost(String authorId, String content, String imagePath) throws JSONException;

    public void deleteExpiredStories() throws JSONException;

    public void createStory(String authorId, String content, String imagePath) throws JSONException;
}
