package ce326.hw3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GamePanel extends JPanel {
    public static final int lines = 6;
    public static final int columns = 7;
    private static final int iconSize = 125;

    public static ImageIcon whiteIcon = new ImageIcon("icons" + File.separator+ "white.png");
    public static ImageIcon yellowIcon = new ImageIcon("icons" + File.separator+ "yellow.png");
    public static ImageIcon redIcon = new ImageIcon("icons" + File.separator+ "red.png");
    public static ImageIcon orangeIcon = new ImageIcon("icons" + File.separator+ "orange.png");
    public static ImageIcon pinkIcon = new ImageIcon("icons" + File.separator+ "pink.png");

    JLabel [] labelArray= new JLabel[42];
    char [] gameArray = new char[42]; // the array will store the game state because its easier to compare chars and not labels
    AIPlayer aiPlayer = null;
    JFrame currFrame = null;

    String gameStart = null;
    String Difficulty = "Trivial";
    String startingPlayer = "ai";
    List<Integer> moveList = new ArrayList<Integer>();
    Timer placementTimer = null; // declared here so it can be reset after new game

    /* Creates the icons based on the pre-determined sizes */
    void createIcons() {
        Image whiteImage = whiteIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        whiteIcon = new ImageIcon(whiteImage);

        Image yellowImage = yellowIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        yellowIcon = new ImageIcon(yellowImage);

        Image redImage = redIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        redIcon = new ImageIcon(redImage);

        Image orangeImage = orangeIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        orangeIcon = new ImageIcon(orangeImage);

        Image pinkImage = pinkIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        pinkIcon = new ImageIcon(pinkImage);

    }

    /* Resets the labels and the char array to white */
    public void resetGrid() {
        for (int i = 0; i < lines*columns; i ++) {
            labelArray[i].setIcon(whiteIcon);
        }
        Arrays.fill(gameArray, 'w');
    }

    /* Creates the game panel */
    public GamePanel(JFrame currFrame, AIPlayer newAiPlayer) {
        this.aiPlayer = newAiPlayer;
        newAiPlayer.gameArray = gameArray;
        newAiPlayer.labelArray = labelArray;
        
        this.currFrame = currFrame;

        java.awt.GridLayout gridDimensions = new GridLayout(6,7);
        setLayout(gridDimensions);

        createIcons();

        this.setBackground(Color.BLUE);

        
        Arrays.fill(gameArray, 'w'); // designates every circle as white

        for (int i = 0; i < lines*columns; i++) {
            labelArray[i] = new JLabel();
            labelArray[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            labelArray[i].setBackground(Color.BLUE);
            labelArray[i].setPreferredSize(new Dimension(iconSize,iconSize));
            labelArray[i].setHorizontalAlignment(JLabel.CENTER);
            labelArray[i].setVerticalAlignment(JLabel.CENTER);

            labelArray[i].setIcon(whiteIcon);

          
            this.add(labelArray[i]);
        }

        addMouseListeners();
        
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter preferedFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm:ss");
        String dateTime = currentDateTime.format(preferedFormat);

        gameStart = dateTime;

        this.setFocusable(true);
        this.requestFocusInWindow(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent pressed) {
                int code = pressed.getKeyCode(); 
                if (code >= 48 && code <= 54) { 
                    placeLabel(code-48, "red", false); //The codes are from 48 to 54 so why subtrackt 48 to get the correct position
                }
                if (code >= 96 && code <= 102) { // for the numpad keys
                    placeLabel(code-96, "red", false); //The codes are from 96 to 102 so why subtrackt 96 to get the correct position
                }
            }


            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }
    
    /* Places a label */
    public void placeLabel(int pos, String color, boolean isReplay) {
        ImageIcon icon = null;
        ImageIcon tempIcon = null;

        if (!GamePanel.checkValidPos(gameArray, pos)) {
            return;
        }

        switch (color) {
            case "red": {
                icon = redIcon;
                tempIcon = pinkIcon;
                break;
            }
            case "yellow": {
                icon = yellowIcon;
                tempIcon = orangeIcon;
                break;
            }
        }

        moveList.add(pos%columns);

        pos = findPos(gameArray, pos);

        labelArray[pos].setIcon(tempIcon);
        gameArray[pos] = color.charAt(0);

        int position = pos;
        ImageIcon inputIcon = icon;
        
        String displayString = FindWinner.searchConnections(gameArray);
        if (displayString != null) {
            if (isReplay == false) {
                HistoryPanel.logGame(this, displayString, moveList);            
            }
            FindWinner.createModalBox(displayString, currFrame);
            FindWinner.preventFurtherPlacements(gameArray);
        }

        placementTimer = new Timer(1000, new ActionListener() {
        public void actionPerformed(ActionEvent updateLabel) {
            labelArray[position].setIcon(inputIcon);
        }
        });
        

        placementTimer.setRepeats(false); 
        placementTimer.start();

        if (isReplay) {
            return;
        }

        if (color.equals("red")) {
            if (gameArray[0] != 'l'){
                aiPlayer.makeMove(this);
            }
        }

    }

    /* Checks if a column is full */
    public static boolean checkValidPos(char [] gameArray, int pos) {
        pos = pos%columns;

        if (gameArray[pos] != 'w') {
            return false;
        }
        else {
            return true;
        }
    }

    /* Finds the position for the label */
    public static int findPos(char [] gameArray, int pos) {
        for (int j = (pos%columns); j < lines*columns; j += columns) {      
            if ((j > 34 || gameArray[j+columns] != 'w') && gameArray[j] == 'w') {
                return j;
           }
        }


        return -1; // will nener get here
    }

    /* Adds a mouse listener to every label */
    public void addMouseListeners() {

        for (int i = 0; i < columns * lines; i++) {
            int pos = i;
            labelArray[i].addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent doubleClick) {
                    if (doubleClick.getClickCount() == 2) {
                        placeLabel(pos, "red", false);
                    }
                }
            });
        }
 
    }

    /* Remove the mouslistener of each label
     * mainly used to prevent the player from placing labels during the replays
     */
    public void removeMouseListeners() {

        for (int i = 0; i < columns*lines; i++) {
            MouseListener [] mouseListener = labelArray[i].getMouseListeners();
            if (mouseListener.length != 0) {
                labelArray[i].removeMouseListener(mouseListener[0]);
            }
        }
    }

}
