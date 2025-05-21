package Controler;

import java.util.ArrayList;

import Modelo.Personagem;

public class Fase {
  ArrayList<Personagem> personagens = new ArrayList<Personagem>();

  public Fase() {

  }

  public void addPersonagem(Personagem personagem) {
    this.personagens.add(personagem);
  }

  public void removePersonagem(Personagem personagem) {
    personagens.remove(personagem);
  }
}
