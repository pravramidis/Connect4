package ce326.hw3;

import javax.swing.*;

import java.util.Random;

public class AIPlayer {
    JLabel [] labelArray = null;
    char [] gameArray = null;
    JFrame currFrame = null;
    int depth = 0;

    static final int rows = 6;
    static final int columns = 7;

    public AIPlayer (JLabel [] labelArray, char [] gameArray, JFrame currFrame, int depth) {
        this.labelArray = labelArray;
        this.gameArray = gameArray;
        this.currFrame = currFrame;
        this.depth = depth;
    }
   
    void makeMove(GamePanel gamePanel) {
        Random rand = new Random();

        int randomNumber = rand.nextInt(7);

        
        gamePanel.placeLabel(randomNumber, "yellow"); 

        evaluatePosition(gameArray);
    }

    int evaluatePosition(char [] gameArray) {
        int evaluation = 0;
        for (int i = 0;  i < rows; i++ ) {
            for (int j = 0; j < columns-3; j++) {
                // System.out.print(i*columns+j);
                String four = new String(gameArray,i*columns + j,4);
                evaluation += evaluateFour(four);
            } 
        }

        System.out.println("evaluation: " + evaluation);
        return evaluation;
    }

    int evaluateFour(String four) {
        int evaluation = 0;
        int countRed = 0;
        int countYellow = 0;
        
        //System.out.println(four);

        for (int i = 0; i < 4; i++) {
            char curr = four.charAt(i);
            if (curr == 'y') {
                countYellow++;
            }
            else if (curr == 'r') {
                countRed++;
            }
        }

        if ((countRed > 0 && countYellow > 0) || (countRed == 0 && countYellow == 0)) {
            return 0;
        }
        else if (countRed == 0) {
            switch (countYellow) {
                case 1: {
                    return 1;
                }
                case 2: {
                    return 4;
                }
                case 3: {
                    return 16;
                }
                case 4: {
                    return 1000;
                }
            }
        }
        else if (countYellow == 0) {
            switch (countRed) {
                case 1: {
                    return 1;
                }
                case 2: {
                    return 4;
                }
                case 3: {
                    return 16;
                }
                case 4: {
                    return 1000;
                }
            }
        }
        
        return evaluation;
    }
}
