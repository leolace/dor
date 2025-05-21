package Controler;

import java.util.ArrayList;
import java.util.Random;

import Auxiliar.Posicao;
import Modelo.Entity;

public class Fase {
  ArrayList<Entity> personagens = new ArrayList<Entity>();
  private Posicao keyPosition;

  public Fase() {
    this.genKeyPosition();
  }

  public void addPersonagem(Entity personagem) {
    this.personagens.add(personagem);
  }

  public void removePersonagem(Entity personagem) {
    personagens.remove(personagem);
  }

  private void genKeyPosition() {
    Random rand = new Random();
    int linha = rand.nextInt(Auxiliar.Consts.RES_X);
    int coluna = rand.nextInt(Auxiliar.Consts.RES_Y);
    this.keyPosition = new Posicao(linha, coluna);
  }
}
