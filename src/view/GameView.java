package view;

import Controller.GameController;
import Controller.GameMouseListener;
import lombok.Getter;
import lombok.Setter;
import model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameView extends JPanel implements MouseListener, KeyListener {


  final BufferedImage burn = ImageIO.read(new File("src/img/burn.png"));
  final BufferedImage balloon1 = ImageIO.read(new File("src/img/baloon.png"));
  final BufferedImage balloon2 = ImageIO.read(new File("src/img/baloon2.png"));
  final BufferedImage balloon3 = ImageIO.read(new File("src/img/baloon3.png"));
  Config config = new Config("config.txt");
  ImageIcon happyIcon = new ImageIcon("src/img/happy_m.png");
  ImageIcon exitIcon = new ImageIcon("src/img/exit.jpg");
  ImageIcon newGameIcon = new ImageIcon("src/img/newGame_M.png");
  ImageIcon newGameIconBig = new ImageIcon("src/img/newGame_B.png");
  ImageIcon gameOverIcon = new ImageIcon("src/img/gameOver.png");
  ImageIcon gameWinIcon = new ImageIcon("src/img/winner_m.png");
  JPanel btnPanel;
  JPanel drawingPanel;
  int sizeCell = config.getSizeCell();
  int paddingSquare = sizeCell;
  int defaultSizeCell = sizeCell;
  int newSizeCell = sizeCell;
  int sizeSpread = sizeCell;
  String time = "00:00";
  GameMouseListener mouseListener;
  GameController gameController;
  JLabel btn1;
  JLabel btn2;
  JLabel btn3;
  int positionXCell;
  int positionYCell;
  List<List<Point>> positionsCells = new ArrayList<>();
  int defaultWidth;
  int scallingPadding = 0;
  int totalPadding = 0;
  int widthWindow;
  int paddingBoardTimer = 20;
  Boolean reloadPanel = false;
  boolean paintBorder = true;
  int paddingBorder = 0;
  boolean shopMenu = false;
  GameOverAnimation gameOverPanel = new GameOverAnimation(this);
  int diff = 0;
  Square bombHits;
  Timer bombTimer;
  int bombGrowUp = 300;
  Boolean firstClick = false;
  private int paddingBoardTopBtnPanel = 60;
  private int widthScreen;
  private int heightScreen;
  private int widthScreenDefault;
  private int heightScreenDefault;
  private Board board;
  private BufferedImage worldImg = config.getImageBckg();
  //  {
//    try {
//      borderImg = ImageIO.read(new File("src/img/border.png"));
//    } catch (IOException e) {
//      throw new RuntimeException(e);
//    }
//  }
//  public BombAnimation()
//  {
//
//  }
  private BufferedImage borderImg = config.getImageBorder();
  private List<List<Square>> boardList;
  @Getter
  @Setter
  private Boolean gameOverAnimation = false;
  @Getter
  @Setter
  private Boolean gameWinAnimation = false;

  public GameView(Board board, GameController gameController) throws IOException {

    // Wczytanie obrazu
//    ImageIcon imageIcon = new ImageIcon("src/img/virus.jpg");
//    backgroundImage = imageIcon.getImage();
    this.gameController = gameController;
    this.board = board;
    this.boardList = board.getBoard();
    initComponents();

    widthScreen = countWidth();
    heightScreen = countHeight() + 60;
    widthScreenDefault = widthScreen - 16;
    heightScreenDefault = heightScreen - 39;
//
    mouseListener = new GameMouseListener(board);
    //setPreferredSize(new Dimension(widthScreen, heightScreen));
    defaultWidth = getPreferredSize().width;
    widthWindow = defaultWidth;
    repaint();
  }

  private int countHeight() {
    int deleteWhiteLineBottom = 3;
    return boardList.size() * sizeCell + (sizeCell * 3) - deleteWhiteLineBottom + paddingBoardTimer + resizeCell();
  }

  private int resizeCell() {
    if (config.getSizeCell() == 80) {
      return -40;
    }
    if (config.getSizeCell() == 60) {
      return -20;
    }
    return 0;
  }

  private int countWidth() {
    int padding = 10;
    return boardList.getFirst().size() * sizeCell + (sizeCell * 2) + padding;
  }

  public void initComponents() {

    if (bombTimer != null) {
      System.out.println("wylacza timer");
      bombTimer.stop();
    }
    setLayout(new BorderLayout());
    btnPanel = getBtnPanel();
    drawingPanel = getDrawingPanel();
    add(btnPanel, BorderLayout.NORTH);
//    revalidate();
//    repaint();
    add(drawingPanel, BorderLayout.CENTER);
  }

  public JPanel getBtnPanel() {
    JPanel boardPanel = new JPanel();
    boardPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
    boardPanel.setBackground(Color.BLACK);
    boardPanel.setOpaque(true);

    this.btn1 = new JLabel(newGameIconBig);

    this.btn2 = new JLabel(happyIcon);
    btn3 = new JLabel(exitIcon);
    this.btn1.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        gameController.newGame();
      }
    });
    this.btn2.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        gameController.restartGame();
        repaint();
        revalidate();
      }

      @Override
      public void mouseEntered(MouseEvent e) {
        GameView.this.btn2.setIcon(newGameIcon);

      }

      @Override
      public void mouseExited(MouseEvent e) {
        GameView.this.btn2.setIcon(happyIcon);
      }
    });
    btn3.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.exit(0);
      }
    });


    boardPanel.add(btn1);
    boardPanel.add(btn2);
    boardPanel.add(btn3);
    return boardPanel;
  }

  public void bombAnimation(int bombGrowUp) {
    bombTimer = new Timer(80, e -> {
      if (sizeSpread < bombGrowUp) {
        sizeSpread++;
        diff--;
      }
      this.repaint();
      if (sizeSpread == bombGrowUp) {
        ((Timer) e.getSource()).stop();

      }
    });
    bombTimer.start();
  }

  private void stopBombTimer(int bombGrowUp) {
    if (sizeSpread == bombGrowUp) {
      bombTimer.stop();
    }
  }
  private void stopWinTimer(int heightScreen) {
    if (heightSpread == heightScreen) {
      winTimer.stop();
    }
  }

  public JPanel getDrawingPanel() {
    JPanel panelDrawing = new JPanel() {
      @Override
      protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setOpaque(true);
        Graphics2D g2d = (Graphics2D) g;
        if (reloadPanel) {
          widthScreen = getSize().width;
          heightScreen = getSize().height + paddingBoardTopBtnPanel;
          resizeWidth(g2d);
          resizeHeight(g2d);
          widthWindow = this.getWidth() + 6;
          if (paintBorder) {
            drawBorder(g2d);
            paddingBorder = sizeCell;
            g2d.drawImage(worldImg, paddingBorder, paddingBorder + paddingBoardTimer,
                sizeCell * boardList.getFirst().size(), sizeCell * boardList.size(), null);
          } else {
            paddingBoardTopBtnPanel = 0;
            paddingBorder = 0;

          }
          drawTime(g2d);
          if (shopMenu) {
            drawShop(g2d);
          } else {
            drawBoard(g2d);
          }
          if (gameOverAnimation) {
            g2d.setColor(Color.red);
            Square bombSquare = gameController.getBombSquare();
            int x = bombSquare.getX() * sizeCell + sizeCell + sizeCell / 2;
            int y = bombSquare.getY() * sizeCell + paddingBorder + paddingBoardTimer + sizeCell / 2;
            System.out.println("bomba trafiona" + bombSquare.getValue());
            bombAnimation(widthScreen);
            //g2d.drawOval(x + diff, y + diff, sizeSpread - diff, sizeSpread - diff);
            drawBomb(g2d, x, y);
            stopBombTimer(widthScreen);
          }
          if (gameWinAnimation) {
            System.out.println("win animation");
            winAnimation(heightScreen-100,g2d, widthScreen);
            drawWin(g2d,heightScreen-100);
            stopWinTimer(heightScreen-100);
          }
        }
        reloadPanel = true;
      }
    };
    return panelDrawing;
  }
  Timer winTimer;
  int heightSpread = 0;
  private void drawWin(Graphics2D g2d,int heightScreen) {
    winTimer = new Timer(80, e -> {
      if (heightSpread < heightScreen) {
        heightSpread++;
      }
      this.repaint();
      if (heightSpread == heightScreen) {
        ((Timer) e.getSource()).stop();
      }
    });
    winTimer.start();
  }
  final BufferedImage balloon5 = ImageIO.read(new File("src/img/baloon5v2.png"));
  private void winAnimation(int heightScreen,Graphics2D g2d, int widthScreen) {
    g2d.setColor(Color.red);
    g2d.drawImage(balloon5,
        widthScreen/2-270, heightScreen-heightSpread,
        widthScreen,
        300, null);
  }

  private void drawBomb(Graphics2D g2d, int x, int y) {
    int half = sizeSpread / 2;
    g2d.fillOval(x, y, 3, 3);
    g2d.drawImage(burn,
        x - half, y - half,
        sizeSpread,
        sizeSpread, null);
  }

  private void drawShop(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);
    g2d.fillRect(paddingBorder, paddingBorder + paddingBoardTimer,
        sizeCell * boardList.getFirst().size(), sizeCell * boardList.size());
    g2d.setColor(Color.WHITE);
    g2d.setFont(new Font("Arial", Font.BOLD, 15));
    g2d.drawString("THEMES:", (widthWindow / 2 - 20) - 20, paddingBorder + paddingBoardTimer + 20);
    g2d.setFont(new Font("Arial", Font.BOLD, 12));
    g2d.drawString("1 - Galaxy THEME", (widthWindow / 2 - 20) - 20, paddingBorder + paddingBoardTimer + 40);
    g2d.drawString("2 - Forest THEME", (widthWindow / 2 - 20) - 20, paddingBorder + paddingBoardTimer + 60);
    g2d.drawString("3 - City THEME", (widthWindow / 2 - 20) - 20, paddingBorder + paddingBoardTimer + 80);
    g2d.drawString("4 - Ufo THEME", (widthWindow / 2 - 20) - 20, paddingBorder + paddingBoardTimer + 100);
    g2d.setFont(new Font("Arial", Font.BOLD, 15));
    g2d.drawString("SIZE CELL:", (widthWindow / 2 - 20) - 20, paddingBorder + paddingBoardTimer + 130);
    g2d.setFont(new Font("Arial", Font.BOLD, 12));
    g2d.drawString("6 - 40", (widthWindow / 2 - 20) - 20, paddingBorder + paddingBoardTimer + 150);
    g2d.drawString("7 - 60", (widthWindow / 2 - 20) - 20, paddingBorder + paddingBoardTimer + 170);
    g2d.drawString("8 - 80", (widthWindow / 2 - 20) - 20, paddingBorder + paddingBoardTimer + 190);
  }

  private void drawBoard(Graphics2D g2d) {
    positionsCells = new ArrayList<>();
    for (int i = 0; i < boardList.size(); i++) {
      List<Point> row = new ArrayList<>();
      for (int j = 0; j < boardList.get(i).size(); j++) {
        positionXCell = j * sizeCell + paddingBorder;
        positionYCell = i * sizeCell + paddingBoardTimer + paddingBorder;
        Point position = new Point(positionXCell, positionYCell);
        row.add(position);
        g2d.drawRect(positionXCell, positionYCell, sizeCell, sizeCell);
        g2d.drawRect(positionXCell + 1, positionYCell + 1, sizeCell - 2, sizeCell - 2);
        if (board.getBoard().get(i).get(j).getDiscover()) {
          g2d.drawImage(boardList.get(i).get(j).getImage().getImage(), positionXCell,
              positionYCell, sizeCell, sizeCell, null);
        }
        if (board.getBoard().get(i).get(j).getFlag()) {
          g2d.drawImage(boardList.get(i).get(j).getImage().getImage(), positionXCell,
              positionYCell, sizeCell, sizeCell, null);
        }
      }
      positionsCells.add(row);
    }
  }

  private void resizeHeight(Graphics2D g2d) {
    double percentDiffHeight =
        (double) (heightScreen - paddingBoardTopBtnPanel - paddingBoardTimer) / (heightScreenDefault - paddingBoardTopBtnPanel - paddingBoardTimer);
    //System.out.println(percentDiffHeight);
    newSizeCell = (int) (defaultSizeCell * percentDiffHeight);
    if (boardList.getFirst().size() * newSizeCell + newSizeCell * 2 < widthScreen) {
      sizeCell = newSizeCell;
      paddingSquare = sizeCell;
    }
  }

  private void resizeWidth(Graphics2D g2d) {
    double percentDiffWidth = (double) widthScreen / widthScreenDefault;
    //System.out.println(percentDiffWidth + "width");
    // System.out.println(percentDiffWidth + "width");
    newSizeCell = (int) (defaultSizeCell * percentDiffWidth);
    int boardSize = boardList.getFirst().size();
    int fullb = boardList.size() * newSizeCell + newSizeCell * 2;
    if (boardList.size() * newSizeCell + newSizeCell * 2 < heightScreen) {
      sizeCell = newSizeCell;
      paddingSquare = sizeCell;
    }
  }

  private void drawBorder(Graphics2D g2d) {
    for (int i = 0; i < boardList.size(); i++) {
      List<Point> row = new ArrayList<>();
      for (int j = 0; j < boardList.get(i).size(); j++) {
        if (i == 0) {
          g2d.drawImage(borderImg,
              j * sizeCell + sizeCell, i * sizeCell + paddingBoardTimer, sizeCell, sizeCell, null);
        } else if (i == boardList.size() - 1) {
          g2d.drawImage(borderImg,
              j * sizeCell + sizeCell, i * sizeCell + paddingBoardTimer + sizeCell * 2, sizeCell,
              sizeCell, null);
        } else if (j == 0) {
          g2d.drawImage(borderImg,
              j * sizeCell + paddingSquare + scallingPadding - sizeCell, i * sizeCell + paddingBoardTimer + sizeCell,
              sizeCell,
              sizeCell, null);
        } else if (j == boardList.get(i).size() - 1) {
          g2d.drawImage(borderImg,
              j * sizeCell + paddingSquare + scallingPadding + sizeCell, i * sizeCell + paddingBoardTimer + sizeCell,
              sizeCell,
              sizeCell, null);
        }
      }
      g2d.drawImage(borderImg,
          0 * sizeCell + paddingSquare + scallingPadding - sizeCell, 0 * sizeCell + paddingBoardTimer + sizeCell,
          sizeCell,
          sizeCell, null);
      g2d.drawImage(borderImg,
          0 * sizeCell + paddingSquare + scallingPadding - sizeCell, 0 * sizeCell + paddingBoardTimer, sizeCell,
          sizeCell, null);
      g2d.drawImage(borderImg,
          (board.getBoard().getFirst().size() + 1) * sizeCell + paddingSquare + scallingPadding - sizeCell,
          0 * sizeCell + paddingBoardTimer, sizeCell,
          sizeCell, null);
      g2d.drawImage(borderImg,
          (board.getBoard().getFirst().size() + 1) * sizeCell + paddingSquare + scallingPadding - sizeCell,
          1 * sizeCell + paddingBoardTimer, sizeCell,
          sizeCell, null);
      g2d.drawImage(borderImg,
          (board.getBoard().getFirst().size() + 1) * sizeCell + paddingSquare + scallingPadding - sizeCell,
          (board.getBoard().size() + 1) * sizeCell + paddingBoardTimer, sizeCell,
          sizeCell, null);
      g2d.drawImage(borderImg,
          (board.getBoard().getFirst().size() + 1) * sizeCell + paddingSquare + scallingPadding - sizeCell,
          board.getBoard().size() * sizeCell + paddingBoardTimer, sizeCell,
          sizeCell, null);
      g2d.drawImage(borderImg,
          0 * sizeCell + paddingSquare + scallingPadding - sizeCell,
          (board.getBoard().size() + 1) * sizeCell + paddingBoardTimer, sizeCell,
          sizeCell, null);
      g2d.drawImage(borderImg,
          0 * sizeCell + paddingSquare + scallingPadding - sizeCell,
          board.getBoard().size() * sizeCell + paddingBoardTimer, sizeCell,
          sizeCell, null);
    }
  }

  private void drawTime(Graphics2D g2d) {
    g2d.setFont(new Font("Arial", Font.PLAIN, 20));
    g2d.drawString(time, widthWindow / 2 - 20,
        17);
  }

  public int getWidth() {
    return widthScreen;
  }

  public int getHeight() {
    return heightScreen;
  }

  @Override
  public void mouseClicked(MouseEvent e) {

    if (gameController.getRunGame()) {
      Square clickedSquare = mouseListener.clicked(positionsCells, boardList, e, sizeCell, paddingBoardTopBtnPanel);
      if (SwingUtilities.isLeftMouseButton(e) && clickedSquare.getFieldNumber() != -22) {
        firstClick = true;
        if (clickedSquare.getFlag()) {
          clickedSquare.setFlag(false);
        } else {
          mouseListener.addClickCounter();
          gameController.mouseEvent(clickedSquare, mouseListener.getClickcounter());
        }
      } else {
        if (firstClick && !clickedSquare.getDiscover()) {
          clickedSquare.changeFlagStan();
        }
      }
    }
    repaint();
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  public void displayGameOver() {
    btn2.setIcon(gameOverIcon);
  }

  public void displayGameWin() {
    btn2.setIcon(gameWinIcon);
    //gameController.saveScore();
  }

  public void updateTime(GameTime gameTime) {
    this.time = gameTime.showTime();
    repaint();
    revalidate();
  }

  @Override
  public void keyTyped(KeyEvent e) {

  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == 80) {
      shopMenu = !shopMenu;
      repaint();
      revalidate();
      //System.out.println(shopMenu);
    }
    if (shopMenu) {
      //System.out.println(e.getKeyCode());
      if (e.getKeyCode() == 49) {
        config.setImages(e.getKeyCode() - 49);
        changeImages();
      }
      if (e.getKeyCode() == 50) {
        config.setImages(e.getKeyCode() - 49);
        changeImages();
      }
      if (e.getKeyCode() == 51) {
        config.setImages(e.getKeyCode() - 49);
        changeImages();
      }
      if (e.getKeyCode() == 52) {
        config.setImages(e.getKeyCode() - 49);
        changeImages();
      }
      if (e.getKeyCode() == 54) {
        config.setSizeCell(40);

        repaint();
      }
      if (e.getKeyCode() == 55) {
        config.setSizeCell(60);
        repaint();
      }
      if (e.getKeyCode() == 56) {
        config.setSizeCell(80);
        repaint();
      }
    }
  }

  private void changeImages() {
    worldImg = config.getImageBckg();
    borderImg = config.getImageBorder();
  }

  @Override
  public void keyReleased(KeyEvent e) {

  }


}
