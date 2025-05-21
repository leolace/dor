package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;

public class Caveira extends Entity {
  private int iContaIntervalos;

  public Caveira(String filename) {
    super(filename);
    this.bTransponivel = false;
    bMortal = false;
    this.iContaIntervalos = 0;
  }

  @Override
  protected void movement() {
    this.iContaIntervalos++;
    if (this.iContaIntervalos == Consts.TIMER) {
      this.iContaIntervalos = 0;
      Fogo f = new Fogo("fire.png");
      f.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() + 1);
      Desenho.acessoATelaDoJogo().addPersonagem(f);
    }
  }
}
