package menu;

import Controller.ScoreController;

import model.ScoreIO;
import model.UIConstants;
import view.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

public class ScoreMenu extends JPanel {
  private ScoreController scoreController;
  private List<String> listToShow;
  private JList<String> scoresList;
  private List<ScoreIO> scores;

  public ScoreMenu(MyFrame frame) {
    scoreController = new ScoreController("save.ser");
    scores = new ArrayList<>();
    listToShow = new ArrayList<>();
    scoresList = new JList<>();

    ImageIcon imageIcon = new ImageIcon("src/img/bckg1.jpg");
    JLabel bckgLabel = new JLabel(imageIcon);
    bckgLabel.setLayout(new BorderLayout());
    JLabel scoresLabel = new JLabel("Scores: ");
    scoresLabel.setFont(new Font("DialogInput", Font.BOLD, 30));
    scoresLabel.setForeground(Color.WHITE);
    scoresLabel.setHorizontalAlignment(SwingConstants.CENTER);
    scoresLabel.setOpaque(false);

    scoresList.setOpaque(false);

    JPanel northPanel = new JPanel(new BorderLayout());
    northPanel.setOpaque(false);
    northPanel.add(scoresLabel, BorderLayout.NORTH);

    JScrollPane scrollPane = new JScrollPane(scoresList);
    scrollPane.getViewport().setOpaque(false);
    scrollPane.setOpaque(false);

    JButton backButton = UIConstants.createButton("back");
    backButton.addActionListener(e -> frame.showMenu());
    northPanel.add(backButton, BorderLayout.SOUTH);
    northPanel.add(scrollPane, BorderLayout.CENTER);
    scoresList.setFixedCellHeight(40);
    scoresList.setFont(new Font("DialogInput", Font.BOLD, 15));

    bckgLabel.add(northPanel, BorderLayout.NORTH);
    add(bckgLabel,BorderLayout.CENTER);

    refreshScores();
  }

  public void refreshScores() {
    scores.clear();
    listToShow.clear();
    scores.addAll(scoreController.getScoresAsc());
    int counter = 1;
    for (ScoreIO score : scores) {
      listToShow.add(counter + ".  " + score.getName() + " - " + score.getScore());
      counter++;
    }
    scoresList.setListData(listToShow.toArray(new String[0]));
  }
}
