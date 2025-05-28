package Modelo;

import Auxiliar.Consts;
import Controler.GameControl;
import java.util.Random;

public class FireThrower extends Entity {
  private int iContaIntervalos;
  private Random random;
  
  public FireThrower(String filename) {
    super("skoot.png");
    this.isTransposable = true;
    this.isMortal = false; // Causa dano ao herói
    this.isDangerous = true; // Causa dano ao herói
    this.iContaIntervalos = 0;
    this.random = new Random();
  }

  @Override
  protected void movement() {
    this.iContaIntervalos++;
    if (this.iContaIntervalos == Consts.TIMER) {
      this.iContaIntervalos = 0;
      
      // Escolhe uma direção aleatória
      Fogo.Direction randomDirection = getRandomDirection();
      
      // Cria o fogo com direção aleatória
      Fogo f = new Fogo("fire.png", randomDirection);
      
      // Posiciona o fogo baseado na direção
      setPosicaoBasedOnDirection(f, randomDirection);
      
      // Adiciona o fogo ao nível atual
      GameControl.getCurrentLevel().addPersonagem(f);
    }
  }
  
  // Método para escolher uma direção aleatória
  private Fogo.Direction getRandomDirection() {
    Fogo.Direction[] directions = Fogo.Direction.values();
    return directions[random.nextInt(directions.length)];
  }
  
  // Método para posicionar o fogo baseado na direção
  private void setPosicaoBasedOnDirection(Fogo fogo, Fogo.Direction direction) {
    switch (direction) {
      case UP:
        fogo.setPosicao(position.getLinha() - 1, position.getColuna());
        break;
      case RIGHT:
        fogo.setPosicao(position.getLinha(), position.getColuna() + 1);
        break;
      case DOWN:
        fogo.setPosicao(position.getLinha() + 1, position.getColuna());
        break;
      case LEFT:
        fogo.setPosicao(position.getLinha(), position.getColuna() - 1);
        break;
    }
  }
}
