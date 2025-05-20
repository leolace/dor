package Modelo;

import java.util.Random;

public class ZigueZague extends Personagem {
  public ZigueZague(String sNomeImagePNG) {
    super(sNomeImagePNG);
  }

  @Override
  public void moviment() {
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
