package ce326.hw3;

import javax.swing.*;

public class GameMenu {

    public static JMenuBar createGameMenu() {
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

        groupPlayer.add(you);
        groupPlayer.add(ai);

        menuPlayer.add(you);
        menuPlayer.add(ai);

        return gameMenu;
    }
}
