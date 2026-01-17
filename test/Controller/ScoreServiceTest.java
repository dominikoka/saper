package Controller;

import model.ScoreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ScoreServiceTest {
  @TempDir
  public Path tempDir;

  private File scoreFile;

  @BeforeEach
  void setUp() throws Exception {
    scoreFile = tempDir.resolve("score.csv").toFile();
  }
  @Test
  @DisplayName("save/read single ScoreIO")
      public void saveScore(@TempDir Path tempDir) throws IOException
  {
    String name = "ufo";
    int score = 50;
    try {
      ScoreService.saveScore(score,name,scoreFile.getName());
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
    var scores = ScoreService.readScoreObj(scoreFile.getName());

    Assertions.assertEquals("ufo",scores.getFirst().getName());
    Assertions.assertEquals(score,scores.getFirst().getScore());
  }

  @Test
  public void saveScore() {
    ScoreController sc = new ScoreController(scoreFile.getName());
    sc.saveScore(3, 1216, 16, 21);
  }
  @Test
  public void getScore() {
    ScoreController sc = new ScoreController(scoreFile.getName());
    var scoresASC = sc.getScoresAsc();
  }
}
