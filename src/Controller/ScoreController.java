package Controller;

import model.ScoreIO;
import model.ScoreService;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScoreController {
  private String nameFile;
  public ScoreController(String nameFile)
  {
    this.nameFile = nameFile;
  }

  public void saveScore(int clickcounter, int sec, int row, int col) {


    Integer score = (clickcounter * sec * row * col) + 10;
    String name = JOptionPane.showInputDialog("your ress: " + score +
        "\n" +
        "Write Your Name: ");
    try {
      ScoreService.saveScore(score, name, nameFile);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public ArrayList<ScoreIO> getScoresAsc() {
    var scores = ScoreService.readScoreObj(nameFile);
    for (int i = 0; i < scores.size(); i++) {
      for (int j = i; j < scores.size(); j++) {
        ScoreIO score1 = scores.get(i);
        ScoreIO score2 = scores.get(j);
        if(score1.getScore()<score2.getScore()) {
          scores.set(i, score2);
          scores.set(j, score1);
        }
      }
    }
    return scores;
  }

}
