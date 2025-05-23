package Modelo;

import Auxiliar.Consts;
import Controler.GameControl;

public class Caveira extends Entity {
  private int iContaIntervalos;

  public Caveira(String filename) {
    super(filename);
    this.isTransposable = false;
    isMortal = false;
    this.iContaIntervalos = 0;
  }

  @Override
  protected void movement() {
    this.iContaIntervalos++;
    if (this.iContaIntervalos == Consts.TIMER) {
      this.iContaIntervalos = 0;
      Fogo f = new Fogo("fire.png");
      f.setPosicao(position.getLinha(), position.getColuna() + 1);
      GameControl.getCurrentLevel().addPersonagem(f);
    }
  }
}
