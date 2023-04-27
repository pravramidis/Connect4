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

        int position = 0, max = -Integer.MAX_VALUE, value;
        for (int i = 0; i < columns; i++) {

            if (!GamePanel.checkValidPos(gameArray, i)) {
                continue;
            }

            char [] moveArray = Arrays.copyOf(gameArray, gameArray.length);
            int pos = GamePanel.findPos(moveArray, i);
            moveArray[pos] = 'y';

            value = miniMax(moveArray, depth, false, -Integer.MAX_VALUE, Integer.MAX_VALUE);
            if (value > max) {
                position = i;
                max = value;
            }
        }
        
        gamePanel.placeLabel(position, "yellow"); 

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

        for (int i = 0; i < rows-3; i++) {
            for (int j = 0; j < columns - 3; j++) {
                countRed = 0;
                countYellow = 0;
                for (int k = 0; k < 4; k++) {
                    if (gameArray[(i+k)*columns + j +k] == 'y') {
                        countYellow++;
                    }
                    else if (gameArray[(i+k)*columns + j + k] == 'r') {
                        countRed++;
                    }
                }
                evaluation += evaluateFour(countRed, countYellow);
            }
        }


        for (int i = rows -3; i < rows; i++) {
            for (int j = columns -4 ; j < columns; j++) {
                countRed = 0;
                countYellow = 0;
                for (int k = 0; k < 4; k++) {
                    if (gameArray[(i-k)*columns + j +k] == 'y') {
                        countYellow++;
                    }
                    else if (gameArray[(i-k)*columns + j + k] == 'r') {
                        countRed++;
                    }
                }
                evaluation += evaluateFour(countRed, countYellow);
            }
        }

        // System.out.println("evaluation: " + evaluation);
        return evaluation;
    }

    int evaluateFour(int countRed, int countYellow) {
    // int evaluateFour(String four) {
        int evaluation = 0;

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

    int miniMax(char [] gameArray, int depth, boolean maxPlayer, int alpha, int beta) {
        String winner = FindWinner.searchConnections(gameArray);

        if(winner == "You won!") {
            return -1000;
        }
        if(winner == "You lost!") {
            return 1000;
        }

        if (depth == 0) {
            return evaluatePosition(gameArray);
        }

        int value;
        if (maxPlayer) {
            int max = -Integer.MAX_VALUE;
            for (int i = 0; i < columns; i++) {

                if (!GamePanel.checkValidPos(gameArray, i)) {
                    continue;
                }

                char [] moveArray = Arrays.copyOf(gameArray, gameArray.length);
                int pos = GamePanel.findPos(moveArray, i);
                moveArray[pos] = 'y';

                value = miniMax(moveArray, depth-1, false, alpha, beta);

                max = Math.max(max, value);
                alpha = Math.max(alpha,max);

                if (beta <= alpha) {
                    break;
                }
            }

            return max;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < columns; i++) {

            if (!GamePanel.checkValidPos(gameArray, i)) {
                break;
            }

            char [] moveArray = Arrays.copyOf(gameArray, gameArray.length);
            int pos = GamePanel.findPos(moveArray, i);
            moveArray[pos] = 'r';

            value = miniMax(moveArray, depth-1, true, alpha, beta);
            min = Math.min(min, value);
            beta = Math.min(beta, min);

            if (beta <= alpha) {    
                break;
            }
        }

        return min;

    }
}
