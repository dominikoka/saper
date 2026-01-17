package model;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
@Data
public class ScoreIO implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  String name;

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  int score;
  public ScoreIO(String name, int score) {
    this.name = name;
    this.score = score;
  }
  public ScoreIO() {
    score = 0;
  }
  public void addOneScorePoint()
  {
    score += 1;
  }
  public void addScorePoint(int score)
  {
    this.score += score;
  }


}
