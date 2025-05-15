package Modelo;

import Auxiliar.Desenho;

public class Hero extends Personagem {
  public Hero(String sNomeImagePNG) {
    super(sNomeImagePNG);
  }

  public void voltaAUltimaPosicao() {
    this.pPosicao.volta();
  }

  public boolean setPosicao(int linha, int coluna) {
    if (this.pPosicao.setPosicao(linha, coluna)) {
      if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
        this.voltaAUltimaPosicao();
      }
      return true;
    }
    return false;
  }

  /*
   * TO-DO: este metodo pode ser interessante a todos os personagens que se movem
   */
  private boolean validaPosicao() {
    if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
      this.voltaAUltimaPosicao();
      return false;
    }
    return true;
  }

  public boolean moveUp() {
    System.out.println("heroi moveu para cima");
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
  protected void moviment() {
  }

}
