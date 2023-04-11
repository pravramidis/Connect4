package ce326.hw3;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameMenu {
    JFrame currFrame = null;
    GamePanel currPanel = null;
    AIPlayer aiPlayer = null;

    public GameMenu(JFrame currFrame, GamePanel currPanel, AIPlayer aiPlayer) {
        this.currFrame = currFrame; 
        this.currPanel = currPanel;
        this.aiPlayer = aiPlayer;
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

        trivial.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currPanel.resetGrid();
                aiPlayer.depth = 1;
                if (ai.isSelected()) {
                    aiPlayer.makeMove(currPanel);
                }
            }
        });

        medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currPanel.resetGrid();
                aiPlayer.depth = 3;
                if (ai.isSelected()) {
                    aiPlayer.makeMove(currPanel);
                }
            }
        });

        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                currPanel.resetGrid();
                aiPlayer.depth = 5;
                if (ai.isSelected()) {
                    aiPlayer.makeMove(currPanel);
                }
            }
        });



        groupPlayer.add(you);
        groupPlayer.add(ai);

        menuPlayer.add(you);
        menuPlayer.add(ai);

        return gameMenu;
    }


    class newGameListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            currPanel.resetGrid();
        }
    }
}
