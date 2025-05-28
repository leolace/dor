package Modelo;

import Controler.GameControl;

public class Chaser extends Entity {
  private boolean iDirectionV;
  private boolean iDirectionH;

  public Chaser(String filename) {
    super("chaser.png");
    iDirectionV = true;
    iDirectionH = true;

    this.isTransposable = true;
    this.isMortal = false;

    setMovementDelay(10);
  }

  public void computeDirection() {
    Hero hero = GameControl.getHero();

    if (hero.getColuna() < this.getColuna()) {
      iDirectionH = true;
    } else if (hero.getColuna() > this.getColuna()) {
      iDirectionH = false;
    }
    if (hero.getLinha() < this.getLinha()) {
      iDirectionV = true;
    } else if (hero.getLinha() > this.getLinha()) {
      iDirectionV = false;
    }
  }

  @Override
  protected void movement() {

    // Verifica se está na mesma linha ou coluna que o herói
    Hero hero = GameControl.getHero();

    int deltaX = Math.abs(hero.getColuna() - this.getColuna());
    int deltaY = Math.abs(hero.getLinha() - this.getLinha());

    // Se a distância horizontal for maior, move horizontalmente
    if (deltaX > deltaY) {
      if (iDirectionH) {
        this.moveLeft();
      } else {
        this.moveRight();
      }
    }
    // Caso contrário, move verticalmente
    else {
      if (iDirectionV) {
        this.moveUp();
      } else {
        this.moveDown();
      }
    }
  }

}
