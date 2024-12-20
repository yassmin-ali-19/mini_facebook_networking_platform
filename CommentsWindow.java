package facebookcheck;

import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class CommentsWindow {
    private JFrame frame;
    private ArrayList<String> comments;

    public CommentsWindow(Content c) {
        // Initialize the comments list
        comments = new ArrayList<>(c.getComments());
//        comments.add("kjkjkjkjk");
//        comments.add("lkllklklkl");
//        comments.add("kkjkjkjkj");
//        comments.add("klklkllkk");
//        comments.add("klklkllkk");
//        comments.add("klklkllkk");
//        comments.add("klklkllkk");

                frame = new JFrame("Comments");
        frame.setSize(400, 400);
       frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); 

        
        frame.setLayout(new GridLayout(comments.size(), 1)); 

        
        for (String comment : comments) {
            JLabel commentLabel = new JLabel(comment);
            frame.add(commentLabel);
        }

        
        frame.setVisible(true);
    }

    public static void main(String[] args) {
       
    }
}
