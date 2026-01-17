package menu;

import Controller.GameController;
import model.*;
import view.*;

import javax.swing.*;
import java.awt.*;

public class DifficultMenu extends JPanel {

  public DifficultMenu(MyFrame frame, GameController gameController) {

    //ImageIcon imageIcon = new ImageIcon("src/img/bckg1.jpg");
    ImageIcon imageIcon = new ImageIcon("src/img/v2.png");
    JLabel bckgLabel = new JLabel(imageIcon);
    bckgLabel.setLayout(new GridBagLayout());
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.gridx = 0;
    gbc.anchor = GridBagConstraints.CENTER;


    JLabel widthLabel = new JLabel("Rows number:");
    widthLabel.setFont(new Font("DialogInput", Font.BOLD, 16));
    widthLabel.setForeground(Color.WHITE);
    JTextField widthField = new JTextField(5);
    widthField.setFont(new Font("DialogInput", Font.BOLD, 12));

    JLabel heightLabel = new JLabel("Columns number:");
    heightLabel.setForeground(Color.WHITE);
    heightLabel.setFont(new Font("DialogInput", Font.BOLD, 16));


    JTextField heightField = new JTextField(5);
    heightField.setFont(new Font("DialogInput", Font.BOLD, 12));

    JLabel bombsLabel = new JLabel("BOMBS QUANTITY:");
    bombsLabel.setFont(new Font("DialogInput", Font.BOLD, 16));
    bombsLabel.setForeground(Color.WHITE);
    JTextField bombsField = new JTextField(5);
    bombsField.setFont(new Font("DialogInput", Font.BOLD, 12));


    JButton backButton = UIConstants.createButton("back");
    backButton.addActionListener(e -> frame.showMenu());
    JButton goButton = UIConstants.createButton("go");
    goButton.addActionListener(e -> {
      try {
        int xField = Integer.parseInt(widthField.getText());
        int yField = Integer.parseInt(heightField.getText());
        int bombs = Integer.parseInt(bombsField.getText());

        gameController.startGame(xField, yField, bombs);
//        if (width > 0 && height > 0 && bombs > 0 && bombs < width * height) {
//          GameController gameController = new GameController(frame);
//          gameController.startGame(width, height, bombs);
//        } else {
//          JOptionPane.showMessageDialog(this, "wronjg.");
//        }
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "write corect nbs");
      }
    });


    gbc.gridy = 0;
    bckgLabel.add(widthLabel, gbc);
    gbc.gridy = 1;
    bckgLabel.add(widthField, gbc);

    gbc.gridy = 2;
    bckgLabel.add(heightLabel, gbc);
    gbc.gridy = 3;
    bckgLabel.add(heightField, gbc);

    gbc.gridy = 4;
    bckgLabel.add(bombsLabel, gbc);
    gbc.gridy = 5;
    bckgLabel.add(bombsField, gbc);

    gbc.gridy = 6;
    bckgLabel.add(goButton, gbc);

    gbc.gridy = 7;
    bckgLabel.add(backButton, gbc);

    add(bckgLabel);
   // setPreferredSize(new Dimension(300, 500));
  }
}
