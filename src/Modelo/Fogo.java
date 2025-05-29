package Modelo;

import java.util.Random;

import Controler.GameControl;

public class Fogo extends Entity {
  public enum Direction {
    UP, RIGHT, DOWN, LEFT
  }
  private Random random = new Random();
  public Direction direction;

  public Fogo() {
    super("fire.png");
    this.isMortal = true;
    this.isTransposable = true;
    this.isDangerous = true;
    this.setMovementDelay(3);
    this.direction = getRandomDirection(); // Direção padrão é para direita
  }


  // Método para escolher uma direção aleatória
  private Fogo.Direction getRandomDirection() {
    Fogo.Direction[] directions = Fogo.Direction.values();
    return directions[random.nextInt(directions.length)];
  }

  @Override
  public void movement() {
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
