package Modelo;

public class HorizontalBouncer extends Entity {
  private boolean isMovingRight = true;
  private int movementCounter = 0;

  public HorizontalBouncer() {
    super("roboPink.png");
    this.setMovementDelay(10);
    this.isTransposable = true; // Não pode atravessar
    this.isMortal = false;
  }

  @Override
  protected void movement() {
    this.movementCounter++;
    if (this.isMovingRight) {
      this.moveRight();
    } else {
      this.moveLeft();
    }

    if (this.movementCounter > 10) {
      this.isMovingRight = !this.isMovingRight;
      this.movementCounter = 0;
    }
  }
}
