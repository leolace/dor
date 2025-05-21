package Modelo;

public class VerticalBouncer extends Personagem {
  boolean bUp;
  private int movementCounter = 0;

  public VerticalBouncer(String filename) {
    super(filename);
    bUp = true;
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
