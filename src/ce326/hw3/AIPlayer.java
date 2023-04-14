package ce326.hw3;

import javax.swing.*;

import java.util.Arrays;
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
        // Random rand = new Random();

        // int randomNumber = rand.nextInt(7);

        BestChild child;

        child = miniMax(gameArray, depth, true, -Integer.MAX_VALUE, Integer.MAX_VALUE);
    
        // System.out.println("value: " + child.value + "pos: " + child.position);
        
        gamePanel.placeLabel(child.position, "yellow"); 

        //evaluatePosition(gameArray);
    }

    int evaluatePosition(char [] gameArray) {
        int evaluation = 0;
        int countRed = 0;
        int countYellow = 0;



        for (int i = 0;  i < rows; i++ ) {
            for (int j = 0; j < columns-3; j++) {
                countRed = 0;
                countYellow = 0; 

                // String four = new String(gameArray, i*columns +j,  4);

                for (int k = 0; k < 4; k++) {
                    char curr = gameArray[i*columns + j + k];
                    if (curr == 'y') {
                        countYellow++;
                    }
                    else if (curr == 'r') {
                        countRed++;
                    }
                }
                evaluation += evaluateFour(countRed, countYellow);
                // evaluation += evaluateFour(four);
            } 
        }


        
        for (int i = 0;  i < columns; i++ ) {
            for (int j = 0; j < rows-3; j++) {
                countRed = 0;
                countYellow = 0;

                for (int k = 0; k < 4; k++) {
                    if (gameArray[(j+k)*columns+i] == 'y') {
                        countYellow++;
                    }
                    else if (gameArray[(j+k)*columns + i] == 'r') {
                        countRed++;
                    }
                }

                evaluation += evaluateFour(countRed, countYellow);
         
            } 
        }

        for (int i = 0; i < rows + columns - 1; i++) {
            for (int j = 0; j <= i; j++) {
                int k = i - j;
                if (k >= 0 && k < rows && j < columns-3) {
                    countRed = 0;
                    countYellow = 0;
                    for (int l = 0; l < 4 && k+l < rows && j+l < columns; l++) {
                        // Check diagonal from top left to bottom right
                        if (gameArray[(k+l) * columns + j + l] == 'y') {
                            countYellow++;
                        } else if (gameArray[(k+l) * columns + j + l] == 'r') {
                            countRed++;
                        }
                    }

                    countRed = 0;
                    countYellow = 0;

                    for (int l = 0; l < 4 && k+l < rows && j+l < columns; l++) {
                        // Check diagonal from top right to bottom left
                        if (gameArray[(k+l) * columns + columns -j -l - 1] == 'y') {
                            countYellow++;
                        } else if (gameArray[(k+l) * columns + columns - j -l - 1] == 'r') {
                            countRed++;
                        }
                    }
                }
            }
        }

        // System.out.println("evaluation: " + evaluation);
        return evaluation;
    }

    int evaluateFour(int countRed, int countYellow) {
    // int evaluateFour(String four) {
        int evaluation = 0;
        // int countRed = 0;
        // int countYellow = 0;
        
        // System.out.println(four);

        // for (int i = 0; i < 4; i++) {
        //     char curr = four.charAt(i);
        //     if (curr == 'y') {
        //         countYellow++;
        //     }
        //     else if (curr == 'r') {
        //         countRed++;
        //     }
        // }

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
                    return -1;
                }
                case 2: {
                    return -4;
                }
                case 3: {
                    return -16;
                }
                case 4: {
                    return -1000;
                }
            }
        }

        return evaluation;
    }

    class BestChild {
        int value;
        int position;

        public BestChild(int value, int position) {
            this.value = value;
            this.position = position;
        }
    }

    void pringArray(char [] gameArray) {
        System.out.println("current state");
        for (int i = 0;  i < rows; i++ ) {
            for (int j = 0; j < columns; j++) {
                System.out.print(gameArray[i*columns+j]);
            } 
            System.out.println();
        }
    }

    BestChild miniMax(char [] gameArray, int depth, boolean maxPlayer, int alpha, int beta) {

        BestChild child = new BestChild(-1, -1);
        BestChild tempChild = new BestChild(-1, -1);

        if (depth == 0) {
            child.position = -1;
            child.value = evaluatePosition(gameArray);
            // pringArray(gameArray); 
            return child;
        }



        if (maxPlayer) {
            int max = -Integer.MAX_VALUE;
            for (int i = 0; i < columns; i++) {

                if (!GamePanel.checkValidPos(gameArray, i)) {
                    break;
                }

                char [] moveArray = Arrays.copyOf(gameArray, gameArray.length);
                int pos = GamePanel.findPos(moveArray, i);
                moveArray[pos] = 'y';

                tempChild = miniMax(moveArray, depth-1, false, alpha, beta);

                max = Math.max(max, tempChild.value);
                alpha = Math.max(alpha,max);

                if (max == tempChild.value) {
                    child.position = i;
                }

                // System.out.println("beta: " + beta + "alpha: "+  alpha);

                // if (beta <= alpha) {
                //     // System.out.println("in" + max);
                //     break;
                // }
            }

            child.value = max;

            return child;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < columns; i++) {

            if (!GamePanel.checkValidPos(gameArray, i)) {
                break;
            }

            char [] moveArray = Arrays.copyOf(gameArray, gameArray.length);
            int pos = GamePanel.findPos(moveArray, i);
            moveArray[pos] = 'r';

            tempChild = miniMax(moveArray, depth-1, true, alpha, beta);
            min = Math.min(min, tempChild.value);
            beta = Math.min(beta, min);

            if (min == tempChild.value) {
                child.position = i;
            }
            // System.out.println("beta: " + beta + "alpha: "+  alpha);
            // if (beta <= alpha) {    
            //     // System.out.println("in" + min);
            //     break;
            // }
        }

        child.value = min;

        return child;

    }
}
