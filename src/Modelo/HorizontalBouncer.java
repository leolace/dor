package Modelo;

public class HorizontalBouncer extends Entity {
  private boolean isMovingRight = true;
  private int movementCounter = 0;

  public HorizontalBouncer(String filename) {
    super(filename);
    this.setMovementDelay(10);
    this.isTransposable = false; // Não pode atravessar
    this.isMortal = true; // Causa dano ao herói
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
