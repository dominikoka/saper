package view;

import Controller.GameController;
import menu.DifficultMenu;
import menu.GameMenu;
import menu.ScoreMenu;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
  public static CardLayout cardLayout;
  private static JPanel contentPane;
  private GameView gameView;
  private GameMenu gameMenu;
  private DifficultMenu difficultMenu;
  private ScoreMenu scoreMenu;
  private GameController gameController;


//  public GameView getGameView() {
//    return gameView;
//  }

  public MyFrame() {
    super("SAPER");
    cardLayout = new CardLayout();
    contentPane = new JPanel(cardLayout);
    gameController = new GameController(this);

    //gameView.setSize(new Dimension(400, 800));
    gameMenu = new GameMenu(this);

    scoreMenu = new ScoreMenu(this);

    difficultMenu = new DifficultMenu(this, gameController);
//    getContentPane().revalidate();
//    getContentPane().repaint();
//    //pack();
    setSize(400, 500);
    contentPane.add(gameMenu, "gameMenu");
    contentPane.add(scoreMenu, "scoreMenu");
    //contentPane.add(gameView, "gameView");
    contentPane.add(difficultMenu, "difficultMenu");
    setPreferredSize(new Dimension(400, 500));
    add(contentPane);
    setContentPane(contentPane);
    //pack();

    //setResizable(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    showMenu();

//    revalidate();
//    repaint();
    setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      new MyFrame();
    });
  }

  public void showMenu() {
    cardLayout.show(contentPane, "gameMenu");
//    System.out.println("gameMenu");
//    contentPane.revalidate();
//    contentPane.repaint();
//    System.out.println("MYFRAME cardLayout hashCode: " + System.identityHashCode(cardLayout));
//    System.out.println("MYFRAME Layout z contentPane hashCode: " + System.identityHashCode(getContentPane().getLayout()));
  }
//  public void showGameView()
//  {
//    cardLayout.show(contentPane, "gameView");
//  }

  public void showScore() {
    scoreMenu.refreshScores();
    cardLayout.show(contentPane, "scoreMenu");
  }

  public void difficultMenu() {
    cardLayout.show(contentPane, "difficultMenu");
  }
}