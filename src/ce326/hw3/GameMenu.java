package ce326.hw3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        JMenu menuHelp = new JMenu("Help");
        JMenu menuHistory = new JMenu("History");

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
                historyPanel.stopAllTimers();
                gamePanel.Difficulty = "Trivial";
                aiPlayer.depth = 1;
                startGame(ai, preferedFormat, cardLayout); 
            }
        });

        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                historyPanel.stopAllTimers();
                gamePanel.Difficulty = "Medium";
                aiPlayer.depth = 3;
                startGame(ai, preferedFormat, cardLayout);
            }
        });

        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                historyPanel.stopAllTimers();
                gamePanel.Difficulty = "Hard";
                aiPlayer.depth = 5;
                startGame(ai, preferedFormat, cardLayout);
            }
        });

        menuHistory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mainPanel.getComponentCount() == 2) { //To ensure the player is not on the start screen
                    cardLayout.next(mainPanel);
                    historyPanel.listFiles(mainPanel, gamePanel);
                    gamePanel.requestFocus();
                }
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
        if (mainPanel.getComponentCount() != 2) {
            return;
        }

        if (gamePanel.placementTimer != null) { 
            gamePanel.placementTimer.stop(); // stops the timer that changes from secondary color to primary color
        }

        MouseListener [] mouseListener = gamePanel.labelArray[0].getMouseListeners();
        if (mouseListener == null) {
            gamePanel.addMouseListeners();
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        gamePanel.gameStart = currentDateTime.format(preferedFormat);

        cardLayout.show(mainPanel, "game");
        gamePanel.requestFocus();
        gamePanel.moveList.clear();
        if (ai.isSelected()) {
            gamePanel.resetGrid(); // added here and below twice to ensure board i cleared rith before placement
            gamePanel.startingPlayer = "ai";
            gamePanel.placeLabel(3, "yellow", false);
        }
        else {
            gamePanel.resetGrid();
            gamePanel.startingPlayer = "you";
        }
    }
}
