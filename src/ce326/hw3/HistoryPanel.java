package ce326.hw3;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import org.json.*;
import java.util.List;
import java.util.Scanner;

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
        DefaultListModel<String> gamesList = new DefaultListModel<>();

        for (File curr: loggedGames) {
            String labelString = readGameFile(curr);
            gamesList.addElement(labelString);
        }

        JList<String> labelList = new JList<String>(gamesList);
        JScrollPane scrollPane = new JScrollPane(labelList);
        this.add(scrollPane, BorderLayout.CENTER);


    }

    

    private static String readGameFile(File gameFile) {
        StringBuilder jsonString = new StringBuilder();

        try {
            Scanner  fileReader = new Scanner(gameFile);
            while (fileReader.hasNextLine()) {
                jsonString.append(fileReader.nextLine());
            }
            fileReader.close();
        }
        catch (Exception e) {
            System.out.println("Could not read file");
        }

        return jsonString.toString();
    }

    public static void createHistoryDirectory() {
        File connect4 = null;

        connect4 = createFileDescriptor(System.getProperty("user.home")+ "/Connect4");

        if (!connect4.exists()) {
            connect4.mkdir();
        }
    }

    public static void logGame(GamePanel gamePanel, String displayString, List<Integer> moveList) {
        File gameFile = null;
        
        gameFile = createFileDescriptor(System.getProperty("user.home")+ "/Connect4/" + gamePanel.gameStart + ".json");

        try {
            gameFile.createNewFile();
        }
        catch (Exception ex) {
            System.out.println("Failed to create file");
        }

        String jsonString = createJsonString(gamePanel, displayString, moveList);

        try (PrintWriter writer = new PrintWriter(gameFile)) {
            writer.format("%s", jsonString);
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

    private static String createJsonString(GamePanel gamePanel, String displayString, List<Integer> moveList) {
        JSONObject gameObject = new JSONObject();
        JSONArray moveArray = new JSONArray(moveList);

        gameObject.put("startTime", gamePanel.gameStart);
        gameObject.put("difficulty", gamePanel.Difficulty);
        gameObject.put("startingPlayer", gamePanel.startingPlayer);
        gameObject.put("moves", moveArray);

        if (displayString == "You won!") {
            gameObject.put("winner", "P");
        }
        else if (displayString == "You lost!") {
            gameObject.put("winner", "AI");
        }
        else {
            gameObject.put("winner", "D");
        }




        return gameObject.toString();
    }
    
}
