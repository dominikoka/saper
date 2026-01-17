package menu;

import model.UIConstants;
import view.MyFrame;
import javax.swing.*;
import java.awt.*;

public class GameMenu extends JPanel {
  public GameMenu(MyFrame frame) {
    System.out.println("game menu");


    //ImageIcon imageIcon = new ImageIcon("src/img/bckg1.jpg");
    ImageIcon imageIcon = new ImageIcon("src/img/v2.png");
    JLabel bckgLabel = new JLabel(imageIcon);

    JLabel name = new JLabel("SAPER");
    name.setFont(new Font("DialogInput", Font.BOLD, 40));
    name.setForeground(Color.WHITE);

    bckgLabel.setLayout(new GridBagLayout());
    setLayout(new GridBagLayout());

    //setPreferredSize(new Dimension(400, 500));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.gridx = 0;

    JButton newGame = UIConstants.createButton("new game");
    newGame.addActionListener(e -> frame.difficultMenu());
    JButton score = UIConstants.createButton("score");
    score.addActionListener(e -> frame.showScore());
    JButton quit = UIConstants.createButton("quit");
    quit.addActionListener(e -> System.exit(0));

    gbc.gridy = 0;
    bckgLabel.add(name, gbc);
    gbc.gridy = 1;
    bckgLabel.add(newGame, gbc);
    gbc.gridy = 2;
    bckgLabel.add(score, gbc);
    gbc.gridy = 3;
    bckgLabel.add(quit, gbc);

    add(bckgLabel);

  }
}
