package Modelo;

import Auxiliar.Posicao;

public class Chaser extends Personagem {
  private boolean iDirectionV;
  private boolean iDirectionH;

  public Chaser(String sNomeImagePNG) {
    super(sNomeImagePNG);
    iDirectionV = true;
    iDirectionH = true;

    this.bTransponivel = true;
  }

  public void computeDirection(Posicao heroPos) {
    if (heroPos.getColuna() < this.getPosicao().getColuna()) {
      iDirectionH = true;
    } else if (heroPos.getColuna() > this.getPosicao().getColuna()) {
      iDirectionH = false;
    }
    if (heroPos.getLinha() < this.getPosicao().getLinha()) {
      iDirectionV = true;
    } else if (heroPos.getLinha() > this.getPosicao().getLinha()) {
      iDirectionV = false;
    }
  }

  @Override
  protected void moviment() {
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
