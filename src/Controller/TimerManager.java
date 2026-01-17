package Controller;


import lombok.Data;
import model.GameTime;
import view.*;

import javax.swing.*;

@Data
public class TimerManager {
  //  CountryStats countryStats = new CountryStats();
//  Timer countryStatsTimer;
//  Country oldCountry = new Country();
//  MyPanel myPanel;
//  MyFrame myFrame;
  private Timer gameLoop;

  private GameTime gameTime;

  private GameView gameView;

  public TimerManager(GameTime gameTime, GameView gameView) {
    this.gameView = gameView;
    this.gameTime = gameTime;
    init();
  }

  public void init() {
    this.gameTime = new GameTime(0);


    gameLoop = new Timer(1000, e -> {
      this.gameTime.updateOneSec();
      gameView.updateTime(this.gameTime);

    });
  }

  public void startLoop() {
    gameLoop.start();
  }

  public void restartLoop() {
    gameLoop.stop();
    this.gameTime = new GameTime(0);
    gameLoop.start();
  }

  public void pauseLoop() {
    this.gameLoop.stop();
  }
  public int getSec()
  {
    return this.gameTime.getSec();
  }

//  @Override
//  public int hashCode() {
//    return 0;
//  }
}
