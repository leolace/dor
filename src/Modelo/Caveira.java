package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;

public class Caveira extends Personagem {
  private int iContaIntervalos;

  public Caveira(String sNomeImagePNG) {
    super(sNomeImagePNG);
    this.bTransponivel = false;
    bMortal = false;
    this.iContaIntervalos = 0;
  }

  @Override
  protected void moviment() {
    this.iContaIntervalos++;
    if (this.iContaIntervalos == Consts.TIMER) {
      this.iContaIntervalos = 0;
      Fogo f = new Fogo("fire.png");
      f.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() + 1);
      Desenho.acessoATelaDoJogo().addPersonagem(f);
    }
  }
}
