package model;

import lombok.Data;

import javax.swing.*;
import java.awt.*;

@Data
public class GameOverAnimation extends JPanel {
  Square bombSquare;
  int sizeCell;
  int xSquare;
  int ySquare;
  Boolean stopTimer = false;
  private JPanel panel;
  private int sizeSpread = 10;
  private int diff = 0;
  private int paddingHeight;

  public GameOverAnimation(JPanel panel) {
    this.panel = panel;
    setOpaque(false);
  }

  public void setSquare(Square square) {
    this.bombSquare = square;
    countXSquare();
    countYSquare();
  }

  public int countXSquare() {
    return xSquare = bombSquare.getX() * sizeCell + sizeCell;
  }

  public int countYSquare() {
    return ySquare = bombSquare.getY() * sizeCell + paddingHeight;
  }

  public void BombAnimation() {
    Timer timer = new Timer(300, e -> {
      if (sizeSpread < 100) {
        sizeSpread++;
        diff--;
        repaint();
      }
//      else {
//        ((Timer) e.getSource()).stop();
//      }
    });
    timer.start();
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    g2d.setColor(Color.red);
    g2d.drawOval(xSquare + diff, ySquare + diff, sizeSpread - diff, sizeSpread - diff);
  }


}
