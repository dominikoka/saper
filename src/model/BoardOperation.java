package model;

import java.util.Stack;

public class BoardOperation {

  public Board discoverSquare(Board board, Square clickedSquare) {
    int clickedBtn = clickedSquare.getFieldNumber();
    for (int i = 0; i < board.getBoard().size(); i++) {
      for (int j = 0; j < board.getBoard().get(i).size(); j++) {
        Square square = board.getBoard().get(i).get(j);
        if (square.getFieldNumber() == clickedBtn) {
          discover(i, j, board);
        } else {
          //System.out.println("nie kliknales w kwadrat");
        }
      }
    }
    return board;
  }

  public int getBombsNumber(Board board) {
    int counter = 0;
    for (var row : board.getBoard()) {
      for (var square : row) {
        if (square.getValue() == -1) {
          counter++;
        }
      }
    }
    return counter;
  }


  private void discover(int i, int j, Board board) {
    Square square = board.getBoard().get(i).get(j);
    if (square.getValue() != 0) {
      square.setDiscover(true);
    } else {
      square.setDiscover(true);
      dfs(i, j, board);
    }
  }

  private void dfs(int i, int j, Board board) {
    showBoard(board);

    Square square = board.getBoard().get(i).get(j);
    Square currentSquare = square;
    Stack<Square> stack = new Stack<>();
    stack.push(square);
    while (!stack.isEmpty() || currentSquare != null) {
      boolean choosedNextStep = true;
      int counter = 0;
      while (choosedNextStep && counter < currentSquare.getNeighbours().size()) {
        Square neighbour = currentSquare.getNeighbours().get(counter);
        if (neighbour.getValue() == 0 && !neighbour.getDiscover()) {
          currentSquare = neighbour;
          currentSquare.setDiscover(true);
          choosedNextStep = false;
          stack.push(currentSquare);
        }
        if (neighbour.getValue() > 0 || counter == currentSquare.getNeighbours().size()) {
          neighbour.setDiscover(true);
          //currentSquare = stack.pop();
          //choosedNextStep = false;
        }
        counter++;
      }
      if (counter == currentSquare.getNeighbours().size()) {
        if (counter == 0) {
          return;
        }
        if (!stack.isEmpty()) {
          currentSquare = stack.pop();
        } else {
          currentSquare = null;
        }

      }
    }
  }

  private void showBoard(Board board) {
//    for (int i = 0; i < board.getBoard().size(); i++) {
//      for (int j = 0; j < board.getBoard().get(i).size(); j++) {
//        System.out.printf(board.getBoard().get(i).get(j).getValue() + " ");
//      }
//      System.out.println();
//    }
  }

  public Board discoverFlag(Board board, Square clickedSquare) {
    for (var neighbour : clickedSquare.getNeighbours()) {
      int xPosition = neighbour.getY();
      int yPosition = neighbour.getX();
      board.getBoard().get(xPosition).get(yPosition).setDiscover(true);
    }
    return board;
  }

  public Boolean checkDfs(Board board) {
    int counter = 0;
    for(var row : board.getBoard()) {
      for(var square : row) {
        if(square.getDiscover())
        {
          counter+= checkNeighbour(square);
        }
      }
    }
    return counter == 0;
  }

  private int checkNeighbour(Square square) {
    var counter = 0;
    for (var neighbour : square.getNeighbours()) {
      if(neighbour.getValue()==0 && !neighbour.getDiscover())
      {
        counter++;
      }
    }
    return counter;
  }
}
