package model;

import lombok.Data;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Data
public class Square {
  int fieldNumber;
  int value;
  Boolean discover;
  Boolean flag;
  int y;
  int x;
  SquareGraphic graphic;
  List<Square> neighbours;

  public Square(int fieldNumber) {
    this.fieldNumber = fieldNumber;
    neighbours = new ArrayList<Square>();
    value = -2;
    flag = false;
    graphic = new SquareGraphic();
  }

  public void addSquareNeighbour(Square square) {
    neighbours.add(square);
    discover = false;
  }

  public void changeFlagStan() {
    flag = !flag;
  }

  public ImageIcon getImage() {
    if (flag) {
      return graphic.getFlag40x40();
    } else if (discover) {
      if (value >= 0) {
        return graphic.getDiscoveredSquares40x40().get(value);
      } else {
        return graphic.getBomb40x40();
      }
    } else {
      return graphic.getNotDiscovered40x40();
    }
  }

  public int getNeighbourFlag() {
    int flag = 0;
    for (Square neighbour : neighbours) {
      if (neighbour.flag) {
        flag++;
      }
    }
    return flag;
  }

  public void discoverNeighbours() {
    for (Square neighbour : neighbours) {
      if (neighbour.discover == null) {
        neighbour.discover = true;
      }
      if (!neighbour.discover) {
        neighbour.discover = true;
      }
    }
  }
}
