package Modelo;

import Auxiliar.Posicao;

public class Chaser extends Entity {
  private boolean iDirectionV;
  private boolean iDirectionH;

  public Chaser(String filename) {
    super(filename);
    iDirectionV = true;
    iDirectionH = true;

    this.bTransponivel = true;
  }

  public void computeDirection(Hero hero) {
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
    if (iDirectionH) {
      this.moveLeft();
    } else {
      this.moveRight();
    }
    if (iDirectionV) {
      this.moveUp();
    } else {
      this.moveDown();
    }
  }

}
