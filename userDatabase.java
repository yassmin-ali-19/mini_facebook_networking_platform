package com.mycompany.useraccount;

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
//dynamic file
    public userDatabase(File file) {
        this.file = file;
    }
    
//adds user to json file
public void saveUser(List<User> users){
    
Gson gson = new GsonBuilder().setPrettyPrinting().create();

// Ensure the file exists before writing
if(!file.exists()){
    try {
        file.createNewFile(); // Create the file if it doesn't exist
    }
    catch (IOException ex) {
        Logger.getLogger(userDatabase.class.getName()).log(Level.SEVERE, null, ex);
    }

}


    try ( FileWriter writer=new FileWriter(file))
    {
        gson.toJson(users,writer);
        
    } catch (IOException ex) {
        Logger.getLogger(userDatabase.class.getName()).log(Level.SEVERE, "Unable to write in file", ex);
    }
}

public List<User> loadUsers(){
   if (!file.exists()) {
       System.out.println("File doesn't exist");
       return new ArrayList<>(); // Return an empty list if the file doesn't exist
    }
Gson gson=new Gson();

try(FileReader reader=new FileReader(file)){

   return gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());
    } catch (IOException ex) {
        Logger.getLogger(userDatabase.class.getName()).log(Level.SEVERE, "Unable to read file", ex);
        
        return new ArrayList<>(); // Return an empty list if an error happens
    }

}
}
