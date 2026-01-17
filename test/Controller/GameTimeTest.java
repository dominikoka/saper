package Controller;

import model.GameTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTimeTest {
  @Test
  public void updateTime10Sec() {
    //given
    int actualTime = 10;
    int updateTime = 10;
    int updatedTime = actualTime + updateTime;
    //when
    GameTime gameTime = new GameTime(actualTime);
    gameTime.updateTime(updateTime);
    //them
    Assertions.assertEquals(gameTime.getSec(), updatedTime);
  }

  @Test
  public void showCoretlyFormatFor8Sec() {
    GameTime gameTime = new GameTime(8);
    Assertions.assertEquals("00:08", gameTime.showTime());
  }

  @Test
  public void showCoretlyFrormatFor8888Sec() {
    GameTime gameTime = new GameTime(8888);
    var time = "2:28:08";
    Assertions.assertEquals(time, gameTime.showTime());
  }
  @Test
  public void showIncreaseToOneMin()
  {
    GameTime gameTime = new GameTime(59);
    gameTime.updateOneSec();

    Assertions.assertEquals("0:01:00",gameTime.showTime());
  }
  @Test
  public void showOneHour()
  {
    GameTime gameTime =  new GameTime(3600);
    Assertions.assertEquals("1:00:00",gameTime.showTime());
  }
}
