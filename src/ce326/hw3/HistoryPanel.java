package ce326.hw3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import org.json.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HistoryPanel extends JPanel {

    public HistoryPanel(GamePanel gamePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        listFiles(gamePanel);
    }

    public void listFiles(GamePanel gamePanel) {
        File gameFolder = null;

        this.removeAll();

        gameFolder = createFileDescriptor(System.getProperty("user.home")+ "/Connect4/");

        File [] loggedGames = gameFolder.listFiles(); 

        if (loggedGames == null) {
            return;
        }
        ArrayList<String> gamesList = new ArrayList<String>();

        for (File curr: loggedGames) {
            String jsonString = readGameFile(curr);
            String labelString = createHistoryLabel(jsonString);
            gamesList.add(labelString);
        }

        Collections.sort(gamesList, Collections.reverseOrder());

        for(String curr: gamesList) {
            JLabel label = new JLabel(curr);

            this.add(label);
            label.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent doubleClick) {
                    if (doubleClick.getClickCount() == 2) {
                        replayGame(gamePanel, label.getText());
                    }
                }
            });
        }

        //JScrollPane scrollPane = new JScrollPane(this);
        //scrollPane.setViewportView(labelList);
        this.revalidate();
        this.repaint();
        // this.add(scrollPane, BorderLayout.CENTER);


    }

    private static String createHistoryLabel(String jsonString) {
        JSONObject jsonObject = new JSONObject(jsonString);
        StringBuilder labelString = new StringBuilder();

        labelString.append(jsonObject.get("startTime"));
        labelString.append("  L: ");
        String difficultyString = jsonObject.get("difficulty").toString();
        labelString.append(difficultyString);
        if (difficultyString.equals("Trivial")) {
            labelString.append("    ");
        }
        else if (difficultyString.equals("Hard")) {
            labelString.append("      ");
        }
        else {
            labelString.append(" ");
        }

        labelString.append(" W: ");
        labelString.append(jsonObject.get("winner"));

        return labelString.toString();
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

    private static void replayGame(GamePanel gamePanel, String fileName) {

        System.out.println(fileName);

    }
    
}
