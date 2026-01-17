package Controller;

import lombok.Data;
import model.Board;
import model.BoardOperation;
import model.GameTime;
import model.Square;
import view.GameView;
import view.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;

@Data
public class GameController {
  Boolean win;
  TimerManager timerManager;
  GameTime gameTime;
  GameView gameView;
  MyFrame frame;
  Board board;
  BoardOperation discoverBoard;
  int bombs;
  int row;
  int col;
  Boolean runGame;
  int clickcounter = 0;
  ScoreController scoreController = new ScoreController("save.ser");
  Square bombHits;

  public GameController(MyFrame frame) {
    this.frame = frame;
  }

  public void startGame(int row, int col, int bombs) {
    runGame = true;
    this.row = row;
    this.col = col;
    this.bombs = bombs;
    board = new Board(row, col, new Random());
    discoverBoard = new BoardOperation();
    try {
      gameView = new GameView(board, this);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    gameTime = new GameTime(0);
    if (timerManager != null) {
      timerManager.pauseLoop();
    }
    clickcounter = 0;

    timerManager = new TimerManager(gameTime, gameView);

    gameView.setFocusable(true);
    gameView.addMouseListener(gameView);
    gameView.addKeyListener(gameView);
    SwingUtilities.invokeLater(() -> {
      gameView.requestFocusInWindow();
    });
    frame.getContentPane().add(gameView, "gameView");
    frame.cardLayout.show(frame.getContentPane(), "gameView");
    frame.setSize(600, 700);
  //  frame.setResizable(true);
//    gameView.repaint();
//    gameView.revalidate();
//    gameView.setOpaque(true);
//    frame.setResizable(true);
  }

  public void mouseEvent(Square clickedSquare, int clickcounter) {
    this.clickcounter = clickcounter;
    addValueInBoard(clickedSquare, clickcounter);
    checkGameStan(clickedSquare);
  }

  public Boolean canWinGame(boolean canWin) {
    var counter = 1;
    startGame(row, col, bombs);
    for (var oneLine : board.getBoard()) {
      for (var square : oneLine) {
        Boolean q2 = canWin ? square.getValue() != -1 : square.getValue() == -1;
        if (counter == 1) {
          mouseEvent(square, counter);
          counter++;
        }
        if (!square.getDiscover() && q2) {
          mouseEvent(square, counter);
          counter++;
        }
      }
    }
    return win;
  }

  public int leastStepsToWin() {
    int fieldValue = 0;
    int counter = 2;
    mouseEvent(board.getBoard().getFirst().getFirst(),1);
    win = false;
    while (!win) {
      for (var row : board.getBoard()) {
        for (var square : row) {
          if(!square.getDiscover() && square.getValue() ==fieldValue&& square.getValue()!=-1) {
            mouseEvent(square, counter);
            counter++;
          }
        }
      }
      fieldValue++;
    }
    return counter;
  }

  public void addValueInBoard(Square clickedSquare, int clickcounter) {
    if (clickcounter == 1) {
      board.createBoard(clickedSquare, bombs);
      timerManager.restartLoop();
    }
    if (clickedSquare.getNeighbourFlag() == clickedSquare.getValue() && clickedSquare.getDiscover()) {
      clickedSquare.discoverNeighbours();
      for (var neighbour : clickedSquare.getNeighbours()) {
        if (neighbour.getValue() == -1) {
          checkGameStan(neighbour);
        }
      }
    }
    board = discoverBoard.discoverSquare(board, clickedSquare);

  }

  private void checkGameStan(Square clickedSquareSquare) {
    int clickedSquare = clickedSquareSquare.getFieldNumber();
    var usedSquare = clickedSquare(clickedSquare);
    if (usedSquare.getValue() == -1) {
      gameView.setGameOverAnimation(true);
      win = false;
      bombHits = usedSquare;
      timerManager.pauseLoop();
      runGame = false;
      gameView.displayGameOver();
    }
    if (checkWin()) {
      gameView.setGameWinAnimation(true);
      win = true;
      timerManager.pauseLoop();
      runGame = false;
      gameView.displayGameWin();
      board.discoverBoard();
      saveScore();
    }
  }

  private boolean checkWin() {
    int boardSize = board.getBoard().size() * board.getBoard().getFirst().size();
    int counter = 0;
    for (int i = 0; i < board.getBoard().size(); i++) {
      for (int j = 0; j < board.getBoard().get(i).size(); j++) {
        if (board.getBoard().get(i).get(j).getDiscover() && board.getBoard().get(i).get(j).getValue() != -1) {
          counter++;
        }
      }
    }
    return counter == boardSize - bombs;
  }

  private Square clickedSquare(int clickedSquare) {
    for (int i = 0; i < board.getBoard().size(); i++) {
      for (int j = 0; j < board.getBoard().get(i).size(); j++) {
        int numberSq = board.getBoard().get(i).get(j).getFieldNumber();
        if (numberSq == clickedSquare) {
          return board.getBoard().get(i).get(j);
        }
      }
    }
    return null;
  }

  public void restartGame() {
    startGame(row, col, bombs);

    //timerManager.restartLoop();
    SwingUtilities.invokeLater(() -> {
      gameView.invalidate();
      gameView.repaint();
      gameView.revalidate();
    });
  }

  public void newGame() {
    frame.showMenu();
    frame.setSize(new Dimension(400, 500));
  }


  public Square getBombSquare() {
    return this.bombHits;
  }

  public void saveScore() {
    Timer saveScoreTimer = new Timer(200, e ->
    {
      scoreController.saveScore(clickcounter, timerManager.getSec(), row, col);
    });
    saveScoreTimer.setRepeats(false);
    saveScoreTimer.start();


  }
}
