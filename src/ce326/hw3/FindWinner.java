package ce326.hw3;

import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;

public class FindWinner {
    public static final int rows = 6;
    public static final int columns = 7;
    
    public static String searchConnections (char [] gameArray) {
        String displayString = null;
    
        displayString = checkForDraw(gameArray);
        if (displayString != null) {
            return displayString;
        }
        displayString = checkHorizontal(gameArray);
        if (displayString != null) {
            return displayString;
        }
        displayString = checkVertical(gameArray);
        if (displayString != null) {
            return displayString;
        }
        displayString = checkDiagonals(gameArray);
        if (displayString != null) {
            return displayString;
        }

        return null;
    }

    /*Checks for draws */
    private static String checkForDraw(char [] gameArray) {
        int countWhite = 0;
        for (int i = 0; i < rows*columns; i++) {
            if (gameArray[i] == 'w') {
                countWhite++;
            }
        }
        if (countWhite == 0) {
            return "Draw!";
        }
        
        return null;
    }

    /* Checks for winner by row*/
    private static String checkHorizontal(char [] gameArray) {
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

        return null;
    }

    /* Checks for winner by column */
    private static String checkVertical(char [] gameArray) {
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

        return null;
    }

    /*Checks for winner diagonally */
    private static String checkDiagonals(char [] gameArray) {
        for (int i = 0; i < rows-3; i++) {
            for (int j = 0; j < columns - 3; j++) {
                int countRed = 0;
                int countYellow = 0;
                for (int k = 0; k < 4; k++) {
                    if (gameArray[(i+k)*columns + j +k] == 'y') {
                        countYellow++;
                        countRed = 0;
                    }
                    else if (gameArray[(i+k)*columns + j + k] == 'r') {
                        countRed++;
                        countYellow = 0;
                    }
                    else {
                        countRed = 0;
                        countYellow = 0;
                    }
                }
                if (countYellow == 4) {
                    return "You lost!";
                }
                else if (countRed == 4) {
                    return "You won!";
                }
            }
        }


        for (int i = rows -4; i > 0; i--) {
            for (int j = columns -4  ; j < columns; j++) {
                int countRed = 0;
                int countYellow = 0;
                for (int k = 0; k < 4; k++) {
                    if (gameArray[(i+k)*columns + j -k] == 'y') {
                        countYellow++;
                        countRed = 0;
                    }
                    else if (gameArray[(i+k)*columns + j - k] == 'r') {
                        countRed++;
                        countYellow = 0;
                    }
                    else {
                        countRed = 0;
                        countYellow = 0;
                    }
                }
                if (countYellow == 4) {
                    return "You lost!";
                }
                else if (countRed == 4) {
                    return "You won!";
                }
            }
        }

        return null;
    }

    static void createModalBox(String displayString, JFrame connect4) {

        JDialog winnerBox = new JDialog(connect4, "Winner");
        winnerBox.setSize(400,200);
        JPanel boxPanel = new JPanel(new BorderLayout());
        winnerBox.add(boxPanel);

        JLabel label = new JLabel(displayString);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        Font winnerFont = new Font("Sans-serif", Font.BOLD, 30);
        label.setFont(winnerFont);
        boxPanel.add(label, BorderLayout.CENTER);

        JButton closeButton = new JButton("OK");
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                winnerBox.dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        boxPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        buttonPanel.add(closeButton);

        winnerBox.setLocationRelativeTo(connect4);
        winnerBox.setVisible(true);
    }

    static void preventFurtherPlacemetns(char [] gameArray) {
        Arrays.fill(gameArray, 'l');
    }
}
