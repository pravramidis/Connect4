package ce326.hw3;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import org.json.*;

public class HistoryPanel extends JPanel {

    public HistoryPanel(GamePanel gamePanel) {
        setLayout(new BorderLayout());
        listFiles(gamePanel);
    }

    public void listFiles(GamePanel gamePanel) {
        File gameFolder = null;

        gameFolder = createFileDescriptor(System.getProperty("user.home")+ "/Connect4/");

        File [] loggedGames = gameFolder.listFiles(); 

        if (loggedGames == null) {
            return;
        }
        DefaultListModel<String> fileNames = new DefaultListModel<>();

        for (File curr: loggedGames) {
            fileNames.addElement(curr.getName());
        }

        JList<String> labelList = new JList<String>(fileNames);
        JScrollPane scrollPane = new JScrollPane(labelList);
        this.add(scrollPane, BorderLayout.CENTER);


    }

    public static void createHistoryDirectory() {
        File connect4 = null;

        connect4 = createFileDescriptor(System.getProperty("user.home")+ "/Connect4");

        if (!connect4.exists()) {
            connect4.mkdir();
        }
    }

    public static void logGame(GamePanel gamePanel) {
        File gameFile = null;
        
        gameFile = createFileDescriptor(System.getProperty("user.home")+ "/Connect4/" + gamePanel.gameStart + ".json");

        try {
            gameFile.createNewFile();
        }
        catch (Exception ex) {
            System.out.println("Failed to create file");
        }

        try (PrintWriter writer = new PrintWriter(gameFile)) {
            writer.format("%s", gamePanel.gameStart  + gamePanel.Difficulty);
        }
        catch (Exception ex) {
            System.out.println("Failed to write file");
        }


    }
    
    public static File createFileDescriptor(String name) {
        File file = null;

        try {
            file = new File(name);
        }
        catch (Exception e) {
            System.out.println("Failed to create file");
        }
        return file;
    }
    
}
