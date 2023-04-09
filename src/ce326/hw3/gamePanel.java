package ce326.hw3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class gamePanel extends JPanel {
    public static final int lines = 6;
    public static final int columns = 7;

    ImageIcon whiteIcon = new ImageIcon("assets/white.png");

    public gamePanel() {
        java.awt.GridLayout gridDimensions = new GridLayout(6,7);
        setLayout(gridDimensions);

        this.setBackground(Color.BLUE);

        Image whiteImage = whiteIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        whiteIcon = new ImageIcon(whiteImage);

        JLabel [] labelArray= new JLabel[42];

        for (int i = 0; i < lines*columns; i++) {
            labelArray[i] = new JLabel(whiteIcon);
            labelArray[i].setHorizontalAlignment(JLabel.CENTER); 
            labelArray[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
            int pos = i;
            ImageIcon updateTest = whiteIcon;
            labelArray[i].addMouseListener(new MouseAdapter() {

                @Override
                public void mouseClicked(MouseEvent doubleClick) {
                    if (doubleClick.getClickCount() == 2) {
                        placeLabel(pos, labelArray, updateTest, "yellow");
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
                    placeLabel(code-48, labelArray, whiteIcon, "yellow"); //The codes are from 48 to 54 so why subtrackt 48 to get the correct position
                }
                if (code >= 96 && code <= 102) { // for the numpad keys
                    placeLabel(code-96, labelArray, whiteIcon, "yellow"); //The codes are from 48 to 54 so why subtrackt 48 to get the correct position
                }
            }


            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

    }
    

    static void placeLabel(int pos, JLabel [] labelArray, ImageIcon updateTest, String color) {
        ImageIcon yellowIcon = new ImageIcon("assets/"+ color +".png");
        Image yellowImage = yellowIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        yellowIcon = new ImageIcon(yellowImage);  

        for (int j = (pos%columns); j < lines*columns; j += columns) {
            if (j > 34) {
                labelArray[j].setIcon(yellowIcon);
                break;
            }
            if (!(updateTest.equals(labelArray[j+columns].getIcon()))) {
                labelArray[j].setIcon(yellowIcon);
                break;
            }
        }

    }

}
