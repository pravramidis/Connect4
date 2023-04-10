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

        JMenuBar menuBar = new JMenuBar();
        JMenu menuNewGame = new JMenu("New Game");
        JMenu menuPlayer = new JMenu("1st Player");
        JMenu menuHistory = new JMenu("History");
        JMenu menuHelp = new JMenu("Help");

        menuBar.add(menuNewGame);
        menuBar.add(menuPlayer);
        menuBar.add(menuHistory);
        menuBar.add(menuHelp);

        JMenuItem trivial, medium, hard;

        trivial = new JMenuItem("Trivial");
        medium = new JMenuItem("Medium");
        hard = new JMenuItem("Hard");

        menuNewGame.add(trivial);
        menuNewGame.add(medium);
        menuNewGame.add(hard);

        ButtonGroup groupPlayer = new ButtonGroup();

        JRadioButtonMenuItem you, ai;
        you = new JRadioButtonMenuItem("You");
        ai = new JRadioButtonMenuItem("AI");

        groupPlayer.add(you);
        groupPlayer.add(ai);

        menuPlayer.add(you);
        menuPlayer.add(ai);

        Connect4.setJMenuBar(menuBar);

        gamePanel panel = new gamePanel();
        Connect4.add(panel);
        Connect4.pack();
    }


}