package ce326.hw3;

import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.*;

public class FindWinner {
    public static final int rows = 6;
    public static final int columns = 7;
    
    public static String searchConnections (char [] gameArray) {
       
        /* Checks for winner by row*/
        for (int i = 0;  i < rows; i++ ) {
            for (int j = 0,countRed = 0, countYellow = 0; j < columns; j++) {
                if (gameArray[i*columns+j] == 'y') {
                    countYellow++;
                    countRed = 0;
                }
                else if (gameArray[i*columns + j] == 'r') {
                    countRed++;
                    countYellow = 0;
                }
                else {
                    countYellow = 0;
                    countRed = 0;
                }
                if (countYellow == 4) {
                    return "You lost!";
                }
                else if (countRed == 4) {
                    return "You won!";
                }
            } 
        }

        /* Checks for winner by column */
        for (int i = 0;  i < columns; i++ ) {
            for (int j = 0,countRed = 0, countYellow = 0; j < rows; j++) {
                if (gameArray[j*columns+i] == 'y') {
                    countYellow++;
                    countRed = 0;
                }
                else if (gameArray[j*columns + i] == 'r') {
                    countRed++;
                    countYellow = 0;
                }
                else {
                    countYellow = 0;
                    countRed = 0;
                }
                if (countYellow == 4) {
                    return "You lost!";
                }
                else if (countRed == 4) {
                    return "You won!";
                }
            } 
        }

        /*Checks for winner diagonally */
        for (int i = 0; i < rows + columns - 1; i++) {
            for (int j = 0, countRed1 = 0, countYellow1 = 0, countRed2 = 0, countYellow2 = 0; j <= i; j++) {
                int k = i - j;
                if (k >= 0 && k < rows && j < columns) {
                    // Check diagonal from top left to bottom right
                    if (gameArray[k * columns + j] == 'y') {
                        countYellow1++;
                        countRed1 = 0;
                    } else if (gameArray[k * columns + j] == 'r') {
                        countRed1++;
                        countYellow1 = 0;
                    } else {
                        countYellow1 = 0;
                        countRed1 = 0;
                    }
                    if (countYellow1 == 4) {
                        return "You lost!";
                    } else if (countRed1 == 4) {
                        return "You won!";
                    }
        
                    // Check diagonal from top right to bottom left
                    if (gameArray[k * columns + columns - j - 1] == 'y') {
                        countYellow2++;
                        countRed2 = 0;
                    } else if (gameArray[k * columns + columns - j - 1] == 'r') {
                        countRed2++;
                        countYellow2 = 0;
                    } else {
                        countYellow2 = 0;
                        countRed2 = 0;
                    }
                    if (countYellow2 == 4) {
                        return "You lost!";
                    } else if (countRed2 == 4) {
                        return "You won!";
                    }
                }
            }
        }

        return null;
    }


    static void createModalBox(String displayString, JFrame connect4) {

        JDialog winnerBox = new JDialog(connect4, "Winner");
        winnerBox.setSize(400,200);
        JLabel label = new JLabel(displayString);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        Font winnerFont = new Font("Sans-serif", Font.BOLD, 30);
        label.setFont(winnerFont);
        winnerBox.add(label);

        winnerBox.setLocationRelativeTo(connect4);
        winnerBox.setVisible(true);
    }

    static void preventFurtherPlacemetns(char [] gameArray) {
        Arrays.fill(gameArray, 'l');
    }
}
