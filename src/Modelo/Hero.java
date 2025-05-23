package Modelo;

import Controler.GameControl;

public class Hero extends Entity {
  public Hero(String filename) {
    super(filename);
  }

  public void voltaAUltimaPosicao() {
    this.position.volta();
  }

  public boolean setPosicao(int linha, int coluna) {
    this.position.setPosicao(linha, coluna);
    return true;
  }

  /*
   * TO-DO: este metodo pode ser interessante a todos os personagens que se movem
   */
  private boolean validaPosicao() {
    if (!GameControl.getCurrentLevel().isLevelValidPosition(this)) {
      this.voltaAUltimaPosicao();
      return false;
    }
    return true;
  }

  public boolean moveUp() {
    if (super.moveUp())
      return validaPosicao();
    return false;
  }

  public boolean moveDown() {
    if (super.moveDown())
      return validaPosicao();
    return false;
  }

  public boolean moveRight() {
    if (super.moveRight())
      return validaPosicao();
    return false;
  }

  public boolean moveLeft() {
    if (super.moveLeft())
      return validaPosicao();
    return false;
  }

  @Override
  protected void movement() {
  }

}
