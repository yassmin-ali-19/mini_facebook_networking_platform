/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package user.account;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
public class userDatabase {
    File file;

    public userDatabase(File file) {
        this.file = file;
    }

    // Save users to the JSON file
    public void saveUsers(List<User> users) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        // Ensure the file exists before writing
        if (!file.exists()) {
            try {
                file.createNewFile(); // Create the file if it doesn't exist
            } catch (IOException ex) {
                Logger.getLogger(userDatabase.class.getName()).log(Level.SEVERE, "Error creating file", ex);
                return;
            }
        }

        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(users, writer);
        } catch (IOException ex) {
            Logger.getLogger(userDatabase.class.getName()).log(Level.SEVERE, "Unable to write to file", ex);
        }
    }

    // Load users from the JSON file
    public List<User> loadUsers() {
        if (!file.exists()) {
            System.out.println("File doesn't exist. Returning an empty list.");
            return new ArrayList<>(); // Return an empty list if the file doesn't exist
        }

        Gson gson = new Gson();

        try (FileReader reader = new FileReader(file)) {
            List<User> users = gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
            if (users == null) {
                System.out.println("No users found in the file. Returning an empty list.");
                return new ArrayList<>(); // Handle empty file case
            }
            return users;
        } catch (IOException ex) {
            Logger.getLogger(userDatabase.class.getName()).log(Level.SEVERE, "Unable to read file", ex);
            return new ArrayList<>(); // Return an empty list if an error occurs
        }
    }
}
