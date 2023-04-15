package ce326.hw3;

import javax.swing.*;
import java.awt.*;

public class Connect4 {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 700;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            startConnecting();
        } 
        });
    }

    public static void startConnecting() {

        HistoryPanel.createHistoryDirectory();

        JFrame Connect4 = new JFrame();
        Connect4.setSize(WIDTH, HEIGHT);
        Connect4.setTitle("Connect4");
        Connect4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Connect4.setVisible(true);

        AIPlayer aiPlayer = new AIPlayer(null, null, Connect4, 1);


        JPanel mainPanel = new JPanel();
        CardLayout layout = new CardLayout();
        mainPanel.setLayout(layout);

        GamePanel gamePanel = new GamePanel(Connect4, aiPlayer);
        HistoryPanel historyPanel = new HistoryPanel(gamePanel);

        mainPanel.add(gamePanel, "game");
        mainPanel.add(historyPanel, "history");

        layout.show(mainPanel, "game"); 

        Connect4.add(mainPanel);

        GameMenu gameMenu = new GameMenu(Connect4, gamePanel, aiPlayer, mainPanel, historyPanel);
        JMenuBar gameMenuBar = gameMenu.createGameMenu();

        Connect4.setJMenuBar(gameMenuBar);   

        Connect4.pack();
    }


}