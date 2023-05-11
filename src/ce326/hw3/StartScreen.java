package ce326.hw3;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

public class StartScreen extends JPanel {
    GamePanel gamePanel = null;
    JPanel mainPanel = null;
   
    public StartScreen(GamePanel gamePanel, JPanel mainPanel, GameMenu gameMenu) {

        this.setLayout(new BorderLayout(ALLBITS, ABORT));

        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel startLabel = new JLabel("Connect4!");
        labelPanel.add(startLabel);
        startLabel.setFont(new Font("Sans-serif", Font.BOLD, 80));
        startLabel.setHorizontalAlignment(JLabel.CENTER);
        startLabel.setHorizontalAlignment(JLabel.CENTER);
        top.add(labelPanel);

        JPanel authorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel authorLabel = new JLabel("by Prodromos Avramidis");
        authorLabel.setFont(new Font("Sans-serif", Font.BOLD, 20));
        authorLabel.setHorizontalAlignment(JLabel.CENTER);
        authorLabel.setHorizontalAlignment(JLabel.CENTER);
        authorPanel.add(authorLabel);
        top.add(authorPanel);

        this.add(top, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton startButton = new JButton("Start Game!", null);
        startButton.setHorizontalAlignment(JLabel.CENTER);
        startButton.setHorizontalAlignment(JLabel.CENTER);
        buttonPanel.add(startButton);
        this.add(buttonPanel, BorderLayout.CENTER);

        /* Below are used in the actionlistener */
        AIPlayer aiPlayer = gamePanel.aiPlayer; 
        CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
        DateTimeFormatter preferedFormat = DateTimeFormatter.ofPattern("yyyy.MM.dd - HH:mm:ss");

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gamePanel.Difficulty = "Trivial";
                aiPlayer.depth = 1;
                gameMenu.startGame(gameMenu.ai, preferedFormat, cardLayout); 
            }
        });
    }
}
