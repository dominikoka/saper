package model;

import lombok.Data;

@Data
public class GameTime {

  //private String time;
  private int sec;

  public GameTime(int sec) {
    this.sec = sec;
  }

  public void updateTime(int howMuchSec) {
    this.sec = this.sec + howMuchSec;
  }

  public void updateOneSec() {
    this.sec = this.sec + 1;
  }

  public String showTime() {
    if (this.sec < 60) {
      if (this.sec < 10) {
        return "00:0" + sec;
      }
      return "00:" + sec;
    }
    return getTime(this.sec);
  }

  private String getTime(int sec) {

    int hours = sec / 3600;
    int restSec = sec - hours * 3600;
    int minutes = restSec / 60;
    int second = restSec - minutes * 60;

    String sMinutes = minutes < 10 ? "0" + minutes : "" + minutes;
    String sSeconds = second < 10 ? "0" + second : "" + second;
    return hours + ":" + sMinutes + ":" + sSeconds;
  }


}
