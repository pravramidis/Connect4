package ce326.hw3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class gamePanel extends JPanel {
    public static final int lines = 6;
    public static final int columns = 7;

    public gamePanel() {
        java.awt.GridLayout gridDimensions = new GridLayout(6,7);
        setLayout(gridDimensions);

        this.setBackground(Color.BLUE);

        ImageIcon whiteIcon = new ImageIcon("assets/white.png");
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
                        ImageIcon yellowIcon = new ImageIcon("assets/yellow.png");
                        Image yellowImage = yellowIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
                        yellowIcon = new ImageIcon(yellowImage);  

                        for (int j = (pos%columns); j < lines*columns; j += columns) {
                            System.out.println(j);
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
            });
            
            add(labelArray[i]);
        }
    }
}
