package model;

import lombok.Data;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

@Data
public class SquareGraphic {
  private ImageIcon zero40x40 = new ImageIcon("src/img/square/zero.png");
  private ImageIcon one40x40 = new ImageIcon("src/img/square/one.png");
  private ImageIcon two40x40 = new ImageIcon("src/img/square/two.png");
  private ImageIcon three40x40 = new ImageIcon("src/img/square/three.png");
  private ImageIcon four40x40 = new ImageIcon("src/img/square/four.png");
  private ImageIcon five40x40 = new ImageIcon("src/img/square/five.png");
  private ImageIcon six40x40 = new ImageIcon("src/img/square/six.png");
  private ImageIcon seven40x40 = new ImageIcon("src/img/square/seven.png");
  private ImageIcon eight40x40 = new ImageIcon("src/img/square/eight.png");
  private ImageIcon notDiscovered40x40 = new ImageIcon("src/img/square/not_discovered.png");
  private ImageIcon flag40x40 = new ImageIcon("src/img/square/flag.png");
  private ImageIcon bomb40x40 = new ImageIcon("src/img/square/bomb.png");
  private List<ImageIcon> discoveredSquares40x40 = Arrays.asList(zero40x40, one40x40, two40x40, three40x40, four40x40, five40x40, seven40x40, eight40x40);
}
