package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Posicao;

public class Caveira extends Entity {
  private int iContaIntervalos;

  public Caveira(String filename, Posicao posicao) {
    super(filename, posicao);
    this.bTransponivel = false;
    bMortal = false;
    this.iContaIntervalos = 0;
  }

  @Override
  protected void movement() {
    this.iContaIntervalos++;
    if (this.iContaIntervalos == Consts.TIMER) {
      this.iContaIntervalos = 0;
      Fogo f = new Fogo("fire.png", new Posicao(pPosicao.getLinha(), pPosicao.getColuna() + 1));
      Desenho.getLevel().addPersonagem(f);
    }
  }
}
