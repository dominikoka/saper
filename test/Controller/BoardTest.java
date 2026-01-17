package Controller;

import model.Board;
import model.BoardOperation;
import model.Square;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class BoardTest {

  @Test
  public void testDiscoverClickedSquare() {
    //clicked square
    //given
    int squareNumber = 213;
    BoardOperation discoverBoard = new BoardOperation();
    //when
    Board board = new Board(3, 3, new Random());
    Square clickedSquare = new Square(213);
    board.createBoard(clickedSquare, 1);
    var discoverFieldBoard = discoverBoard.discoverSquare(board, clickedSquare);
    var stanDiscoverSquare = discoverFieldBoard.getStanSquare(squareNumber, discoverFieldBoard);
    //then
    Assertions.assertEquals(stanDiscoverSquare, true);
  }

  @Test
  public void testFillAllBoardBombs() {
    //given
    int clickedSquareNumber = 213;
    int row = 3;
    int column = 3;
    int bomb = row * column - 1;
    Board board = new Board(3, 3, new Random());
    board.createBoard(board.getBoard().getFirst().getFirst(), bomb);
    BoardOperation boardOperation = new BoardOperation();
    //when
    int numberBombs = boardOperation.getBombsNumber(board);
    //them
    Assertions.assertEquals(numberBombs, bomb);
  }

  @Test
  public void checkDFS() {
    //given
    int row = 3;
    int column = 3;
    int bomb = 1;
    //when
    Board board = new Board(row, column, new Random());
    Square clickedSquare = board.getBoard().getFirst().getFirst();
    board.createBoard(clickedSquare, bomb);
    BoardOperation boardOperation = new BoardOperation();
    var statusDfs = boardOperation.checkDfs(board);
    //them
    Assertions.assertEquals(statusDfs, true);
  }

}
