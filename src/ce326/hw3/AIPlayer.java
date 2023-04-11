package ce326.hw3;

import javax.swing.*;

import java.util.Random;

public class AIPlayer {
    JLabel [] labelArray = null;
    char [] gameArray = null;
    JFrame currFrame = null;
    int depth = 0;

    public AIPlayer (JLabel [] labelArray, char [] gameArray, JFrame currFrame, int depth) {
        this.labelArray = labelArray;
        this.gameArray = gameArray;
        this.currFrame = currFrame;
        this.depth = depth;
    }
   
    void makeMove(GamePanel gamePanel) {
        Random rand = new Random();

        int randomNumber = rand.nextInt(7);
        
        gamePanel.placeLabel(depth, "yellow"); 

    }
}
