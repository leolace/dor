package Modelo;

import Auxiliar.Posicao;

public class HorizontalBouncer extends Entity {
  private boolean isMovingRight = true;
  private int movementCounter = 0;

  public HorizontalBouncer(String filename, Posicao posicao) {
    super(filename, posicao);
    this.setMovementDelay(10);
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
