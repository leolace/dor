package Modelo;

import Auxiliar.Posicao;

public class Key extends Entity {
  public Key(String filename, Posicao posicao) {
    super(filename, posicao);
    this.bMortal = false;
    this.setMovementDelay(0);
  }

  @Override
  protected void movement() {
    return;
  }
}
