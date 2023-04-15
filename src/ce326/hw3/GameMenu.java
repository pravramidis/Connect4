package ce326.hw3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



public class GameMenu {
    JFrame currFrame = null;
    GamePanel gamePanel = null;
    HistoryPanel historyPanel = null;
    AIPlayer aiPlayer = null;
    JPanel mainPanel = null;

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

        JRadioButtonMenuItem you, ai;
        you = new JRadioButtonMenuItem("You");
        ai = new JRadioButtonMenuItem("AI");

        ai.setSelected(true);

        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();

        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter preferedFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm");
        String DateTime = currentDateTime.format(preferedFormat);


        trivial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.Difficulty = "Trivial";
                gamePanel.gameStart = DateTime;
                cardLayout.show(mainPanel, "game");
                gamePanel.resetGrid();
                aiPlayer.depth = 1;
                if (ai.isSelected()) {
                    aiPlayer.makeMove(gamePanel);
                }
            }
        });

        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.Difficulty = "Medium";
                gamePanel.gameStart = DateTime;

                cardLayout.show(mainPanel, "game");
                gamePanel.resetGrid();
                aiPlayer.depth = 3;
                if (ai.isSelected()) {
                    aiPlayer.makeMove(gamePanel);
                }
            }
        });

        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.Difficulty = "Hard";
                gamePanel.gameStart = DateTime;

                cardLayout.show(mainPanel, "game");
                gamePanel.resetGrid();
                aiPlayer.depth = 5;
                if (ai.isSelected()) {
                    aiPlayer.makeMove(gamePanel);
                }
            }
        });

        menuHistory.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                historyPanel.listFiles(gamePanel);
                cardLayout.next(mainPanel);
            }

            /* Included to satisfy mouselistener requirements */
            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}
        });



        groupPlayer.add(you);
        groupPlayer.add(ai);

        menuPlayer.add(you);
        menuPlayer.add(ai);

        return gameMenu;
    }


    class newGameListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            gamePanel.resetGrid();
        }
    }
}
