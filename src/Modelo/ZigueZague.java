package Modelo;

import java.util.Random;

import Auxiliar.Posicao;

public class ZigueZague extends Entity {
  public ZigueZague(String filename, Posicao posicao) {
    super(filename, posicao);
  }

  @Override
  public void movement() {
    Random rand = new Random();
    int iDirecao = rand.nextInt(4);

    if (iDirecao == 0)
      this.moveDown();
    else if (iDirecao == 1)
      this.moveLeft();
    else if (iDirecao == 2)
      this.moveRight();
    else if (iDirecao == 3)
      this.moveUp();
  }
}
