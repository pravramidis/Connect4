package ce326.hw3;

import javax.swing.*;

import java.util.Random;

public class AIPlayer {
   
    static void makeMove(JLabel [] labelArray, char [] gameArray,JFrame currFrame) {
        Random rand = new Random();

        int randomNumber = rand.nextInt(7);
        
        gamePanel.placeLabel(randomNumber, labelArray, gameArray,"yellow", currFrame); 

    }
}
