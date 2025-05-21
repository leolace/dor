package Controler;

import Modelo.Chaser;
import Modelo.Entity;
import Modelo.Hero;
import java.util.ArrayList;

public class ControleDeJogo {
  public void desenhaTudo(ArrayList<Entity> personagens) {
    for (int i = 1; i < personagens.size(); i++) {
      personagens.get(i).autoDesenho();
    }
  }

  public void processaTudo(ArrayList<Entity> personagens) {
    Hero hero = (Hero) personagens.get(0);
    for (int i = 1; i < personagens.size(); i++) {
      Entity personagem = personagens.get(i);
      if (hero.isSamePosition(personagem.getLinha(), personagem.getColuna())) {
        if (personagem.isbTransponivel() && personagem.isbMortal())
          personagens.remove(personagem);

      }

      if (personagem instanceof Chaser) {
        ((Chaser) personagem).computeDirection(hero);
      }
    }

  }

  /*
   * Retorna true se a posicao p é válida para Hero com relacao a todos os
   * personagens no array
   */
  public boolean isValidPosition(ArrayList<Entity> personagens, Entity personagem) {
    for (int i = 1; i < personagens.size(); i++) {
      Entity personagemAtual = personagens.get(i);
      if (personagemAtual.isbTransponivel())
        return true;

      if (personagemAtual.isSamePosition(personagem.getLinha(), personagem.getColuna())) {
        return false;
      }

    }
    return true;
  }
}
