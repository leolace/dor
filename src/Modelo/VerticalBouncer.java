package Modelo;

public class VerticalBouncer extends Entity {
  boolean bUp;
  private int movementCounter = 0;
  public VerticalBouncer(String filename) {
    super("robo.png");
    bUp = true;
    this.isTransposable = true;
    this.isMortal = false;
  }

  @Override
  protected void movement() {
    this.movementCounter++;

    if (bUp)
      this.moveUp();
    else
      this.moveDown();

    if (this.movementCounter > 10) {
      this.movementCounter = 0;
      bUp = !bUp;
    }
  }
}
