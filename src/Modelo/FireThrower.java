package Modelo;

import Auxiliar.Consts;
import Controler.GameControl;

public class FireThrower extends Entity {
  private int iContaIntervalos;
  private Fogo fire = new Fogo();
  
  public FireThrower() {
    super("skoot.png");
    this.isTransposable = true;
    this.isMortal = false; // Causa dano ao herói
    this.isDangerous = true; // Causa dano ao herói
    this.iContaIntervalos = 0;
  }

  @Override
  public void movement() {
    this.iContaIntervalos++;
    if (this.iContaIntervalos == Consts.TIMER) {
      this.iContaIntervalos = 0;
      
      // Posiciona o fogo baseado na direção
      setPosicaoBasedOnDirection();
      
      // Adiciona o fogo ao nível atual
      GameControl.getCurrentLevel().addPersonagem(fire);
    }
  }

  
  // Método para posicionar o fogo baseado na direção
  private void setPosicaoBasedOnDirection() {
    switch (fire.direction) {
      case UP:
        fire.setPosicao(position.getLinha() - 1, position.getColuna());
        break;
      case RIGHT:
        fire.setPosicao(position.getLinha(), position.getColuna() + 1);
        break;
      case DOWN:
        fire.setPosicao(position.getLinha() + 1, position.getColuna());
        break;
      case LEFT:
        fire.setPosicao(position.getLinha(), position.getColuna() - 1);
        break;
    }
  }
}
