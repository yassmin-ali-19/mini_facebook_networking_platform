package friend.manegment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChatHistory {

    private static final String filePath = "chat_History.txt";

    public static void saveMessage(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(message); // Write the message
            writer.newLine(); // Add a new line after each message
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
     public static String loadChatHistory() {
        StringBuilder history = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.append(line).append("\n"); // Append each line to the history
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return history.toString();
    }
}
