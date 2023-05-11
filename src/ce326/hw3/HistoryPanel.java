package ce326.hw3;

import javax.swing.*;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import org.json.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class HistoryPanel extends JPanel {
    private static ArrayList<Timer> timerList = new ArrayList<>();

    public HistoryPanel(JPanel mainPanel, GamePanel gamePanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        listFiles(mainPanel, gamePanel);
    }

    public void listFiles(JPanel mainPanel, GamePanel gamePanel) {
        File gameFolder = null;
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.Y_AXIS));

        this.removeAll();

        gameFolder = createFileDescriptor(System.getProperty("user.home")+ "/Connect4/");

        File [] loggedGames = gameFolder.listFiles(); 

        if (loggedGames == null) {
            return;
        }
        ArrayList<String> gamesList = new ArrayList<String>();

        for (File curr: loggedGames) {
            gamesList.add(curr.getName());
        }

        Collections.sort(gamesList, Collections.reverseOrder());

        for(String curr: gamesList) {
            String filePath = System.getProperty("user.home")+ "/Connect4/" + curr;
            File file = createFileDescriptor(filePath);
            String jsonString = readGameFile(file);
            String labelString = createHistoryLabel(jsonString);
            JLabel label = new JLabel(labelString);
            label.setForeground(Color.BLACK);

            tempPanel.add(label);
            label.addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent doubleClick) {
                    if (doubleClick.getClickCount() == 2) {
                        stopAllTimers();
                        replayGame(mainPanel, gamePanel, filePath);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    label.setForeground(Color.BLUE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    label.setForeground(Color.BLACK);
                }
            });
        }

        JScrollPane scrollPane = new JScrollPane(tempPanel);
        this.add(scrollPane);
        this.revalidate();
        this.repaint();
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

    private static void replayGame(JPanel mainPanel, GamePanel gamePanel, String fileName) {

        File file = createFileDescriptor(fileName);
        String jsonString = readGameFile(file);

        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray("moves");

        String color;

        gamePanel.resetGrid();
        gamePanel.removeMouseListeners();

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        cardLayout.show(mainPanel, "game");



        if (jsonObject.getString("startingPlayer").equals("ai")) {
            gamePanel.placeLabel(jsonArray.getInt(0), "yellow", true);
            color = "red";
        }
        else {
            gamePanel.placeLabel(jsonArray.getInt(0), "red", true);
            color = "yellow";
            color = "red";
        }


        for (int i = 1; i < jsonArray.length(); i++) {
            int pos = i;
            String final_color = color;
            Timer timer = new Timer(3000*pos, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    gamePanel.placeLabel(jsonArray.getInt(pos), final_color, true);
                }
            });
            timerList.add(timer);
            timer.setRepeats(false);
            timer.start();
            if (color.equals("red")) {
                color = "yellow";
            }
            else {
                color = "red";
            }
        }

        gamePanel.addMouseListeners();
    }

    public void stopAllTimers() {
        for (Timer currTimer: timerList) {
            currTimer.stop();
        }
    }
    
}
