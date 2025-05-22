package Modelo;

import Auxiliar.Desenho;
import Auxiliar.Posicao;

public class Hero extends Entity {
  public Hero(String filename, Posicao posicao) {
    super(filename, posicao);
  }

  public void voltaAUltimaPosicao() {
    this.pPosicao.volta();
  }

  public boolean setPosicao(int linha, int coluna) {
    this.pPosicao.setPosicao(linha, coluna);
    return true;
    // if (this.pPosicao.setPosicao(linha, coluna)) {
    // if (!Desenho.getLevel().isLevelValidPosition(this)) {
    // this.voltaAUltimaPosicao();
    // }
    // return true;
    // }
    // return false;
  }

  /*
   * TO-DO: este metodo pode ser interessante a todos os personagens que se movem
   */
  private boolean validaPosicao() {
    if (!Desenho.getLevel().isLevelValidPosition(this)) {
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
