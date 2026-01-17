package Controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.MyFrame;

public class GameControllerTest {
  int row;
  int col;
  int bomb;
  @BeforeEach
  public void setUp() {
    row = 16;
    col = 32;
    bomb = 99;
  }
  @Test
  public void testGameControllerWin() {
    MyFrame frame = new MyFrame();
    GameController gameController = new GameController(frame);
    gameController.startGame(row, col, bomb);
    var ifCanWin = gameController.canWinGame(true);
    Assertions.assertEquals(ifCanWin, true);
  }
  @Test
  public void testGameControllerLose() {
    MyFrame frame = new MyFrame();
    GameController gameController = new GameController(frame);
    gameController.startGame(row, col, bomb);
    var ifCanWin = gameController.canWinGame(false);
    Assertions.assertEquals(ifCanWin, false);
  }
  @Test
  public void leastStepsToWin() {
    MyFrame frame = new MyFrame();
    GameController gameController = new GameController(frame);
    gameController.startGame(row, col, bomb);
    var leastStepsToWin = gameController.leastStepsToWin();
    System.out.println(leastStepsToWin);
    Boolean q1= leastStepsToWin>1;
    Assertions.assertEquals(true, q1);
  }
}
