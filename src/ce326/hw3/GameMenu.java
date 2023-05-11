package ce326.hw3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/* Contains all the items for the menu */
public class GameMenu {
    JFrame currFrame = null;
    GamePanel gamePanel = null;
    HistoryPanel historyPanel = null;
    AIPlayer aiPlayer = null;
    JPanel mainPanel = null;
    JRadioButtonMenuItem ai = new JRadioButtonMenuItem("AI");

    public GameMenu(JFrame currFrame, GamePanel gamePanel, AIPlayer aiPlayer, JPanel mainPanel, HistoryPanel historyPanel) {
        this.currFrame = currFrame; 
        this.gamePanel = gamePanel;
        this.aiPlayer = aiPlayer;
        this.mainPanel = mainPanel;
        this.historyPanel = historyPanel;
    }

    public JMenuBar createGameMenu() {
        JMenuBar gameMenu = new JMenuBar();
        JMenu menuNewGame = new JMenu("New Game");
        JMenu menuPlayer = new JMenu("1st Player");
        JMenu menuHistory = new JMenu("History");
        JMenu menuHelp = new JMenu("Help");

        gameMenu.add(menuNewGame);
        gameMenu.add(menuPlayer);
        gameMenu.add(menuHistory);
        gameMenu.add(menuHelp);

        JMenuItem trivial, medium, hard;

        trivial = new JMenuItem("Trivial");
        medium = new JMenuItem("Medium");
        hard = new JMenuItem("Hard");

        menuNewGame.add(trivial);
        menuNewGame.add(medium);
        menuNewGame.add(hard);

        ButtonGroup groupPlayer = new ButtonGroup();

        JRadioButtonMenuItem you;
        you = new JRadioButtonMenuItem("You");

        ai.setSelected(true);

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        DateTimeFormatter preferedFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm:ss");
        


        trivial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.Difficulty = "Trivial";
                aiPlayer.depth = 1;
                startGame(ai, preferedFormat, cardLayout); 
            }
        });

        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.Difficulty = "Medium";
                aiPlayer.depth = 3;
                startGame(ai, preferedFormat, cardLayout);
            }
        });

        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.Difficulty = "Hard";
                aiPlayer.depth = 5;
                startGame(ai, preferedFormat, cardLayout);
            }
        });

        menuHistory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.next(mainPanel);
                historyPanel.listFiles(mainPanel, gamePanel);
                gamePanel.requestFocus();
            }
        });



        groupPlayer.add(you);
        groupPlayer.add(ai);

        menuPlayer.add(you);
        menuPlayer.add(ai);

        return gameMenu;
    }

    /* Starts a new game */
    public void startGame(JRadioButtonMenuItem ai, DateTimeFormatter preferedFormat, CardLayout cardLayout) {
        historyPanel.stopAllTimers();
        LocalDateTime currentDateTime = LocalDateTime.now();
        gamePanel.gameStart = currentDateTime.format(preferedFormat);

        cardLayout.show(mainPanel, "game");
        gamePanel.requestFocus();
        gamePanel.resetGrid();
        gamePanel.moveList.clear();
        if (ai.isSelected()) {
            gamePanel.startingPlayer = "ai";
            gamePanel.placeLabel(3, "yellow", false);
        }
        else {
            gamePanel.startingPlayer = "you";
        }
    }
}
