package Modelo;

import Controler.GameControl;

public class Fogo extends Entity {
  // Enumeração para as direções possíveis do fogo
  public enum Direction {
    UP, RIGHT, DOWN, LEFT
  }
  
  private Direction direction;

  public Fogo(String filename) {
    super(filename);
    this.isMortal = true;
    this.isTransposable = true;
    this.isDangerous = true;
    this.setMovementDelay(3);
    this.direction = Direction.RIGHT; // Direção padrão é para direita
  }
  
  public Fogo(String filename, Direction direction) {
    this(filename);
    this.direction = direction;
  }

  @Override
  protected void movement() {
    boolean movementSuccess = false;
    
    // Move na direção especificada
    switch (this.direction) {
      case UP:
        movementSuccess = this.moveUp();
        break;
      case RIGHT:
        movementSuccess = this.moveRight();
        break;
      case DOWN:
        movementSuccess = this.moveDown();
        break;
      case LEFT:
        movementSuccess = this.moveLeft();
        break;
    }
    
    // Se não conseguir mover, remove o fogo
    if (!movementSuccess) {
      GameControl.getCurrentLevel().removePersonagem(this);
    }
  }

}
