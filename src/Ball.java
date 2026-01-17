public class Ball {
  private int x;
  private int y;

  public Ball() {
    this.x = 0;
    this.y = 0;
  }

  public void moveBall() {
    this.x += 2;
    this.y += 2;
  }

  public int getX() {
    return this.x;

  }

  public int getY() {
    return this.y;

  }

}