package Controler;

import Modelo.Chaser;
import Modelo.Entity;
import Modelo.Hero;
import java.util.ArrayList;

import Auxiliar.Consts;
import Auxiliar.Posicao;

public class ControleDeJogo {
  private Posicao cameraPosition;
  private Hero hero;

  public ControleDeJogo(Hero hero) {
    cameraPosition = new Posicao(hero.getLinha(), hero.getColuna());
    this.hero = hero;
  }

  public Posicao getCameraPosition() {
    return this.cameraPosition;
  }

  public void updateCamera(Posicao posicao) {
    int linha = posicao.getLinha();
    int coluna = posicao.getColuna();

    int cameraLinha = Math.max(0, Math.min(linha - Consts.RES_X / 2, Consts.MUNDO_ALTURA - Consts.RES_X));
    int cameraColuna = Math.max(0, Math.min(coluna - Consts.RES_Y / 2, Consts.MUNDO_LARGURA - Consts.RES_Y));

    this.cameraPosition = new Posicao(cameraLinha, cameraColuna);
  }

  public void updateCameraToHero(Hero heroo) {
    this.updateCamera(new Posicao(heroo.getLinha(), heroo.getColuna()));
  }

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
