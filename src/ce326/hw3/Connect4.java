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
        JFrame Connect4 = new JFrame();
        Connect4.setSize(WIDTH, HEIGHT);
        Connect4.setTitle("Connect4");
        Connect4.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Connect4.setVisible(true);

        GameMenu gameMenu = new GameMenu(Connect4);
        JMenuBar gameMenuBar = gameMenu.createGameMenu();

        Connect4.setJMenuBar(gameMenuBar);   

        gamePanel panel = new gamePanel(Connect4);
        Connect4.add(panel);
        Connect4.pack();
    }


}