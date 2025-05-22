package Modelo;

import Auxiliar.Desenho;
import Auxiliar.Posicao;

public class Fogo extends Entity {

  public Fogo(String filename, Posicao posicao) {
    super(filename, posicao);
    this.bMortal = true;
    this.setMovementDelay(3);
  }

  @Override
  protected void movement() {
    if (!this.moveRight())
      Desenho.getLevel().removePersonagem(this);
  }

}
