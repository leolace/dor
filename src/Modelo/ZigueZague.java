package Modelo;

import java.util.Random;

public class ZigueZague extends Entity {
  public ZigueZague() {
    super("caveira.png");
    this.isTransposable = true;
    this.isMortal = false;
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
