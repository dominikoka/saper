package Controller;

import lombok.Data;
import model.Board;
import model.Square;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.List;

@Data
public class GameMouseListener {
  Board board;
  List<List<Square>> boardSquareList;
  int clickcounter = 0;
  int moveWidth = 0;
  int moveHeight = 0;
  public GameMouseListener(Board board) {
    this.board = board;
    boardSquareList = board.getBoard();
  }

  public Square clicked(List<List<Point>> positionsCells, List<List<Square>> board, MouseEvent e, int sizeCell,
                        int totalPadding) {
    for (int i = 0; i < positionsCells.size(); i++) {
      for (int j = 0; j < positionsCells.get(i).size(); j++) {
        Point point = positionsCells.get(i).get(j);
       // int clickX = e.getX();
        int clickX = e.getX();
        int clickY = (int) (e.getY())-totalPadding;

        int cellX = (int) point.getX() - moveWidth;
        int cellY = (int) point.getY() + moveHeight;
//        if(i == 0 && j == 0)
//        {
//          System.out.println("positionsCells: " + positionsCells.get(i).get(j).getX());
//          System.out.println("positionsCells: " + positionsCells.get(i).get(j).getY());
//          System.out.println("click X: " + clickX + " Y: " + clickY);
//          System.out.println("totalPadding: " + totalPadding);
//        }

        boolean f1 = (clickX >= cellX) && (clickX < cellX + sizeCell);
        boolean f2 = (clickY >= cellY) && (clickY < cellY + sizeCell);
        if (f1 && f2) {
          int clicked = board.get(i).get(j).getFieldNumber();
          return board.get(i).get(j);
        }
      }
    }
    return new Square(-22);
  }

  public void addClickCounter() {
    clickcounter++;
  }
}
