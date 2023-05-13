package ce326.hw3;

import javax.swing.*;

import java.util.Arrays;

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
  
    /* Calls the minimax algorithm for every child of the root node */
    void makeMove(GamePanel gamePanel) {

        int position = 0, max = -Integer.MAX_VALUE, value;
        for (int i = 0; i < columns; i++) {

            if (!GamePanel.checkValidPos(gameArray, i)) {
                continue;
            }

            char [] moveArray = Arrays.copyOf(gameArray, gameArray.length);
            int pos = GamePanel.findPos(moveArray, i);
            moveArray[pos] = 'y';

            value = miniMax(moveArray, depth-1, false, -Integer.MAX_VALUE, Integer.MAX_VALUE);
            if (value > max) {
                position = i;
                max = value;
            }
        }
        
        gamePanel.placeLabel(position, "yellow", false); 
    }

    /* Evaluates a position */
    int evaluatePosition(char [] gameArray) {
        int evaluation = 0;
        int countRed = 0;
        int countYellow = 0;


        /* Evaluates by row*/
        for (int i = 0;  i < rows; i++ ) {
            for (int j = 0; j < columns-3; j++) {
                countRed = 0;
                countYellow = 0; 

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
            } 
        }


        
        /* Evaluates by column */
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
        
        /*Evaluates diagonally */
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

        for (int i = rows -4; i >= 0; i--) {
            for (int j = columns -4  ; j < columns; j++) {
                countRed = 0;
                countYellow = 0;
                for (int k = 0; k < 4; k++) {
                    if (gameArray[(i+k)*columns + j -k] == 'y') {
                        countYellow++;
                    }
                    else if (gameArray[(i+k)*columns + j - k] == 'r') {
                        countRed++;
                    }
                }
                evaluation += evaluateFour(countRed, countYellow);
            }
        }
        return evaluation;
    }

    int evaluateFour(int countRed, int countYellow) {
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
                    return 100000;
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
                    return -100000;
                }
            }
        }

        return evaluation;
    }

    /* Implements the minimax algorithm with alpha beta pruning*/
    int miniMax(char [] gameArray, int depth, boolean maxPlayer, int alpha, int beta) {


        if (depth == 0) {
            return evaluatePosition(gameArray);
        }
        String winner = FindWinner.searchConnections(gameArray);

        /* we subtrackt the depth to ensure that the quickest that the move chosen 
        prevents the player from winning on the next move and that the ai will win the next move */
        if(winner != null && winner.equals("You won!")) {
            return -100000 - depth;
        }
        if(winner != null && winner.equals("You lost!")) {
            return 100000 - depth;
        }
        if (winner != null && winner.equals(("Draw!"))) {
            return 0;
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
                continue;
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
