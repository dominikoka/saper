package Controller;

import model.Square;
import model.SquareGraphic;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SquareTest {
  @Test
  public void testAddSquare() {
    int squareNeighbourFieldNumber = 225;
    Square square = new Square(222);
    Square neighbourSquare = new Square(squareNeighbourFieldNumber);
    square.addSquareNeighbour(neighbourSquare);
    Assertions.assertEquals(squareNeighbourFieldNumber, square.getNeighbours().getFirst().getFieldNumber());
  }

  @Test
  public void testChangeFlagStan() {
    Square square = new Square(222);
    square.changeFlagStan();
    Assertions.assertEquals(true, square.getFlag());
  }
  @Test
  public void testGetImage()
  {
    SquareGraphic graphic = new SquareGraphic();
    Square square = new Square(222);
    square.setGraphic(graphic);
    Assertions.assertEquals(graphic, square.getGraphic());
  }
  @Test
  public void testGetNeighbourFlag()
  {
    Square square = new Square(2222);
    Square neighbourSquare = new Square(1111);
    square.addSquareNeighbour(neighbourSquare);
    var flagNeighbour = square.getNeighbourFlag();
    Assertions.assertEquals(0, flagNeighbour);
  }
  @Test
  public void testDiscoverNeighbours()
  {
    Square square = new Square(2222);
    Square neighbourSquare = new Square(1111);
    square.addSquareNeighbour(neighbourSquare);
    square.discoverNeighbours();
    Assertions.assertEquals(square.getNeighbours().getFirst().getDiscover(), true);
  }
}
