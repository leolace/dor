package Modelo;

import Controler.GameControl;

public class Fogo extends Entity {

  public Fogo(String filename) {
    super(filename);
    this.isMortal = true;
    this.setMovementDelay(3);
  }

  @Override
  protected void movement() {
    if (!this.moveRight())
      GameControl.getCurrentLevel().removePersonagem(this);
  }

}
