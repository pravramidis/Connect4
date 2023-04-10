package ce326.hw3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class gamePanel extends JPanel {
    public static final int lines = 6;
    public static final int columns = 7;

    public static ImageIcon whiteIcon = new ImageIcon("assets/white.png");
    public static ImageIcon yellowIcon = new ImageIcon("assets/yellow.png");
    public static ImageIcon redIcon = new ImageIcon("assets/red.png");
    public static ImageIcon orangeIcon = new ImageIcon("assets/orange.png");
    public static ImageIcon pinkIcon = new ImageIcon("assets/pink.png");

    void createIcons() {
        Image whiteImage = whiteIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        whiteIcon = new ImageIcon(whiteImage);

        Image yellowImage = yellowIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        yellowIcon = new ImageIcon(yellowImage);

        Image redImage = redIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        redIcon = new ImageIcon(redImage);

        Image orangeImage = orangeIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        orangeIcon = new ImageIcon(orangeImage);

        Image pinkImage = pinkIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        pinkIcon = new ImageIcon(pinkImage);

    }

    public gamePanel() {
        java.awt.GridLayout gridDimensions = new GridLayout(6,7);
        setLayout(gridDimensions);

        createIcons();

        this.setBackground(Color.BLUE);

        JLabel [] labelArray= new JLabel[42];

        for (int i = 0; i < lines*columns; i++) {
            labelArray[i] = new JLabel(whiteIcon);
            labelArray[i].setHorizontalAlignment(JLabel.CENTER); 
            labelArray[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            int pos = i;
            labelArray[i].addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent doubleClick) {
                    if (doubleClick.getClickCount() == 2) {
                        placeLabel(pos, labelArray, "yellow");
                    }
                }
            });
            
            add(labelArray[i]);
        }

        this.setFocusable(true);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent pressed) {
                int code = pressed.getKeyCode(); 

                if (code >= 48 && code <= 54) { 
                    placeLabel(code-48, labelArray, "yellow"); //The codes are from 48 to 54 so why subtrackt 48 to get the correct position
                }
                if (code >= 96 && code <= 102) { // for the numpad keys
                    placeLabel(code-96, labelArray, "red"); //The codes are from 96 to 102 so why subtrackt 96 to get the correct position
                }
            }


            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }
    

    static void placeLabel(int pos, JLabel [] labelArray, String color) {
        ImageIcon icon = null;
        ImageIcon tempIcon = null;

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

        for (int j = (pos%columns); j < lines*columns; j += columns) {      
            if (!(whiteIcon.equals(labelArray[j].getIcon()))) { // So that the top of the columns can't change colors
                break;
            }
            if (j > 34 || !(whiteIcon.equals(labelArray[j+columns].getIcon()))) {

                labelArray[j].setIcon(tempIcon);
                int position = j;
                ImageIcon inputIcon = icon;
                Timer timer = new Timer(1000, new ActionListener() {
                    public void actionPerformed(ActionEvent updateLabel) {
                        labelArray[position].setIcon(inputIcon);
                        FindWinner.searchConnections(labelArray, yellowIcon, redIcon);
                    }
                });
                timer.setRepeats(false); 
                timer.start(); 

                break;
            }
        }


    }

}
