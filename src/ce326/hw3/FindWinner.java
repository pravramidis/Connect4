package ce326.hw3;

import javax.swing.JLabel;
import javax.swing.*;

public class FindWinner {
    public static final int rows = 6;
    public static final int columns = 7;
    
    public static String searchConnections (JLabel [] labelArray, ImageIcon yellow, ImageIcon red) {
       
        /* Checks for winner by row*/
        for (int i = 0;  i < rows; i++ ) {
            for (int j = 0,countRed = 0, countYellow = 0; j < columns; j++) {
                if (yellow.equals(labelArray[i*columns+j].getIcon())) {
                    countYellow++;
                    countRed = 0;
                }
                else if (red.equals(labelArray[i*columns + j].getIcon())) {
                    countRed++;
                    countYellow = 0;
                }
                else {
                    countYellow = 0;
                    countRed = 0;
                }
                if (countYellow == 4) {
                    System.out.println("ai won");
                    return "AI won";
                }
                else if (countRed == 4) {
                    System.out.println("player won");
                    return "Player won";
                }
            } 
        }

        /* Checks for winner by column */
        for (int i = 0;  i < columns; i++ ) {
            for (int j = 0,countRed = 0, countYellow = 0; j < rows; j++) {
                if (yellow.equals(labelArray[j*columns+i].getIcon())) {
                    countYellow++;
                    countRed = 0;
                }
                else if (red.equals(labelArray[j*columns + i].getIcon())) {
                    countRed++;
                    countYellow = 0;
                }
                else {
                    countYellow = 0;
                    countRed = 0;
                }
                if (countYellow == 4) {
                    System.out.println("AI won");
                    return "AI won";
                }
                else if (countRed == 4) {
                    System.out.println("player won");
                    return "Player won";
                }
            } 
        }

        /*Checks for winner diagonally */
        for (int i = 0; i < rows + columns - 1; i++) {
            for (int j = 0, countRed1 = 0, countYellow1 = 0, countRed2 = 0, countYellow2 = 0; j <= i; j++) {
                int k = i - j;
                if (k >= 0 && k < rows && j < columns) {
                    // Check diagonal from top left to bottom right
                    if (yellow.equals(labelArray[k * columns + j].getIcon())) {
                        countYellow1++;
                        countRed1 = 0;
                    } else if (red.equals(labelArray[k * columns + j].getIcon())) {
                        countRed1++;
                        countYellow1 = 0;
                    } else {
                        countYellow1 = 0;
                        countRed1 = 0;
                    }
                    if (countYellow1 == 4) {
                        System.out.println("AI won");
                        return "AI won";
                    } else if (countRed1 == 4) {
                        System.out.println("Player won");
                        return "Player won";
                    }
        
                    // Check diagonal from top right to bottom left
                    if (yellow.equals(labelArray[k * columns + columns - j - 1].getIcon())) {
                        countYellow2++;
                        countRed2 = 0;
                    } else if (red.equals(labelArray[k * columns + columns - j - 1].getIcon())) {
                        countRed2++;
                        countYellow2 = 0;
                    } else {
                        countYellow2 = 0;
                        countRed2 = 0;
                    }
                    if (countYellow2 == 4) {
                        System.out.println("AI won");
                        return "AI won";
                    } else if (countRed2 == 4) {
                        System.out.println("Player won");
                        return "Player won";
                    }
                }
            }
        }

        return "No winner";
    }


    static void createModalBox(String displayString) {
        JOptionPane.showMessageDialog(null, "This is a modal dialog box.", "Dialog Box", JOptionPane.INFORMATION_MESSAGE);
    }
}
