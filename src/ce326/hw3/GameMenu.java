package ce326.hw3;

import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GameMenu {
    JFrame currFrame = null;

    public GameMenu(JFrame currFrame) {
        this.currFrame = currFrame;
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

        trivial.addActionListener(new newGameListener());
        medium.addActionListener(new newGameListener());
        hard.addActionListener(new newGameListener());

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

        return gameMenu;
    }


    class newGameListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            JPanel newGamePanel = new gamePanel(currFrame);
            currFrame.add(newGamePanel);
        }
    }
}
