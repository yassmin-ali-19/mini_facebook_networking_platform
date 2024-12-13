/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package facebook;

/**
 *
 * @author Elnour Tech
 */

import java.io.File;
import user.account.User;
import java.util.ArrayList;
import java.util.List;

public class SearchManager {
    private User u; 
    private List<User> friends; 
    private List<User> suggestedFriends; 
    private List<User> blockedUsers; 
    private List<User> friendRequests; 
    private List<User> users; 
    private List<Group> groups;

    public SearchManager(User u, List<User> allUsers, List<Group> allGroups) {
        this.u = u;
        this.users = allUsers; 
        this.groups = allGroups; 
        this.friends = u.getFriends();
        this.suggestedFriends = u.getSuggested();
        this.blockedUsers = u.getBlockedUsers();
        this.friendRequests = u.getFriendRequests();
    }

    public User getCurrentUser() {
        return u;
    }

    public List<User> searchUsers(String query) {
        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (user.getUsername().toLowerCase().contains(query.toLowerCase())) {
                results.add(user);
            }
        }
        return results;
    }

    public List<User> searchFriends(String query) {
        List<User> results = new ArrayList<>();
        for (User friend : friends) {
            if (friend.getUsername().toLowerCase().contains(query.toLowerCase())) {
                results.add(friend);
            }
        }
        return results;
    }

    public List<User> searchFriendRequests(String query) {
        List<User> results = new ArrayList<>();
        for (User request : friendRequests) {
            if (request.getUsername().toLowerCase().contains(query.toLowerCase())) {
                results.add(request);
            }
        }
        return results;
    }

    public List<User> searchBlockedUsers(String query) {
        List<User> results = new ArrayList<>();
        for (User blockedUser : blockedUsers) {
            if (blockedUser.getUsername().toLowerCase().contains(query.toLowerCase())) {
                results.add(blockedUser);
            }
        }
        return results;
    }

    public List<User> searchSuggestedFriends(String query) {
        List<User> results = new ArrayList<>();
        for (User suggested : suggestedFriends) {
            if (suggested.getUsername().toLowerCase().contains(query.toLowerCase())) {
                results.add(suggested);
            }
        }
        return results;
    }

    public List<Group> searchGroups(String query) {
        List<Group> results = new ArrayList<>();
        for (Group group : groups) {
            if (group.getGroupName().toLowerCase().contains(query.toLowerCase())) {
                results.add(group);
            }
        }
        return results;
    }

    public List<User> getUsers() {
        return users;
    }
}