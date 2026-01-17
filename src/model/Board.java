package model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
public class Board {
  List<List<Square>> board;
  Boolean isBoardInitialized;
  private final Random random;

  public Board(int row, int col, Random random) {
    this.random = random;
    initBoard(row, col);
    isBoardInitialized = false;
  }

  private void initBoard(int row, int col) {
    List<List<Square>> result = new ArrayList<>();
    List<Square> createdRow;
    for (int i = 0; i < col; i++) {
      createdRow = new ArrayList<>();
      for (int j = 0; j < row; j++) {
        int nb = Integer.parseInt((i + 1) + "" + i + (j + 1));
        Square square = new Square(nb);
        square.setY(i);
        square.setX(j);
        createdRow.add(square);
      }
      result.add(createdRow);
    }
    this.board = result;
    initializeNeighbours();
  }


  private void initializeNeighbours() {
    int rows = board.size();
    for (int i = 0; i < board.size(); i++) {
      int cols = board.get(i).size();
      for (int j = 0; j < board.get(i).size(); j++) {
        Square square;
        Square current = board.get(i).get(j);
        //lewa strona
        if (j - 1 >= 0) {
          //dla gornego
          if (i - 1 >= 0) {
            square = board.get(i - 1).get(j - 1);
            current.addSquareNeighbour(square);
          }
          if (i + 1 < rows) {
            square = board.get(i + 1).get(j - 1);
            current.addSquareNeighbour(square);
          }
          square = board.get(i).get(j - 1);
          current.addSquareNeighbour(square);
        }
        //prawa strona
        if (j + 1 < cols) {
          if (i - 1 >= 0) {
            square = board.get(i - 1).get(j + 1);
            current.addSquareNeighbour(square);
          }
          if (i + 1 < rows) {
            square = board.get(i + 1).get(j + 1);
            current.addSquareNeighbour(square);
          }
          square = board.get(i).get(j + 1);
          current.addSquareNeighbour(square);
        }
        if (i - 1 >= 0) {
          square = board.get(i - 1).get(j);
          current.addSquareNeighbour(square);
        }
        if (i + 1 < rows) {
          square = board.get(i + 1).get(j);
          current.addSquareNeighbour(square);
        }
      }
    }
  }


  public void createBoard(Square clickedSquare, int bombs) {
    addBombs(bombs, clickedSquare);
    addValues();
    isBoardInitialized = true;
  }

  private void addValues() {
    for (int i = 0; i < board.size(); i++) {
      for (int j = 0; j < board.get(i).size(); j++) {
        if (board.get(i).get(j).getValue() == -2) {
          addValue(board.get(i).get(j));
        }
      }
    }
  }
  public void discoverBoard()
  {
    for(int i = 0; i < board.size(); i++)
    {
      int row = board.get(i).size();
      for (int j = 0; j < row; j++)
      {
        board.get(i).get(j).setDiscover(true);
      }
    }
  }
  public Boolean getStanSquare(int fieldNumber, Board board) {
    for (var row : board.getBoard()) {
      for (var e : row) {
        if (e.getFieldNumber() == fieldNumber) {
          return true;
        }
      }
    }
    return false;
  }

  private void addValue(Square square) {
    int counter = 0;
    for (Square neighbour : square.neighbours) {
      if (neighbour.value == -1) {
        counter++;
      }
    }
    square.value = counter;
  }

  private void addBombs(int bombs, Square clickedSquare) {
    var randomPlaceBomb = takeRandomPlace(bombs, clickedSquare);
    int counter = 0;
    for (int i = 0; i < board.size(); i++) {
      for (int j = 0; j < board.get(i).size(); j++) {
        if (randomPlaceBomb.contains(counter)) {
          System.out.println(i + " " + j + "- bomb position");
          board.get(i).get(j).setValue(-1);
        }
        counter++;
      }
    }

  }


  private List<Integer> takeRandomPlace(int bombs, Square clickedSquare) {
    if (board.isEmpty() || board.get(0).isEmpty()) {
      throw new IllegalStateException("Board is not initialized properly");
    }
    int boardSize = board.size() * board.get(0).size();
    if (bombs > boardSize) throw new IllegalArgumentException("Too many bombs");
    List<Integer> randomPlace = new ArrayList<>();
    int clickedBtnIn = clickBtnInOrderAsc(clickedSquare);
    while (randomPlace.size() < bombs) {
      int randomPlaces = random.nextInt(boardSize);
      if (clickedBtnIn != randomPlaces && !randomPlace.contains(randomPlaces)) {
        randomPlace.add(randomPlaces);
      }
    }
    return randomPlace;
  }

  private int clickBtnInOrderAsc(Square clickedSquare) {
    int y = clickedSquare.getY();
    int x = clickedSquare.getX();
    return y * board.get(0).size() + x;
  }
}
