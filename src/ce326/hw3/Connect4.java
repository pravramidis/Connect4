package ce326.hw3;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

import java.awt.*;

/* The main class of the program */
public class Connect4 {
    public static final int WIDTH = 875;
    public static final int HEIGHT = 787;

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            startConnecting();
        } 
        });
    }

    /* Creates all the necessary components that are added to the frame */
    public static void startConnecting() {
        /* Change all the defaults to the desired font */
        UIManager.put("Label.font", new FontUIResource(new Font("Sans-serif", Font.PLAIN, 20)));
        UIManager.put("MenuItem.font", new FontUIResource(new Font("Sans-serif", Font.PLAIN, 20)));
        UIManager.put("Menu.font", new FontUIResource(new Font("Sans-serif", Font.PLAIN, 20)));
        UIManager.put("RadioButtonMenuItem.font", new FontUIResource(new Font("Sans-serif", Font.PLAIN, 20)));
        UIManager.put("Button.font", new FontUIResource(new Font("Sans-serif", Font.PLAIN, 40)));

        HistoryPanel.createHistoryDirectory();

        JFrame Connect4 = new JFrame();
        Image whiteIcon = Toolkit.getDefaultToolkit().getImage("icons/taskBarIcon.png");
        Connect4.setIconImage(whiteIcon);
        Connect4.setSize(WIDTH, HEIGHT);
        Connect4.setTitle("Connect4");
        Connect4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Connect4.setVisible(true);

        AIPlayer aiPlayer = new AIPlayer(null, null, Connect4, 1);


        /* Using a the CardLayout allows easily switching between the panels */
        JPanel mainPanel = new JPanel();
        CardLayout layout = new CardLayout();
        mainPanel.setLayout(layout);

        GamePanel gamePanel = new GamePanel(Connect4, aiPlayer);
        HistoryPanel historyPanel = new HistoryPanel(mainPanel, gamePanel);

        GameMenu gameMenu = new GameMenu(Connect4, gamePanel, aiPlayer, mainPanel, historyPanel);
        JMenuBar gameMenuBar = gameMenu.createGameMenu();

        StartScreen startScreen = new StartScreen(gamePanel, mainPanel, gameMenu);

        mainPanel.add(gamePanel, "game");
        mainPanel.add(historyPanel, "history");
        mainPanel.add(startScreen, "start");

        Connect4.add(mainPanel);

        layout.show(mainPanel, "start"); 
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();


        Connect4.setJMenuBar(gameMenuBar); 
        
        Connect4.setResizable(false);

    }


}