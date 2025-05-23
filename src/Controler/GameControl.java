package Controler;

import Modelo.Chaser;
import Modelo.Entity;
import Modelo.Hero;
import java.util.ArrayList;

import Auxiliar.Consts;
import Auxiliar.Posicao;

public class GameControl {
  private static Posicao cameraPosition;
  private Hero hero;
  private static int currentLevelIndex = 0;
  public static ArrayList<Level> levels = new ArrayList<Level>();

  public GameControl(Hero hero) {
    cameraPosition = new Posicao(hero.getLinha(), hero.getColuna());
    this.hero = hero;

    this.assembleLevels();
  }

  private void assembleLevels() {
    // Adiciona 5 níveis
    GameControl.levels.add(new Level(hero, this));
    GameControl.levels.add(new Level(hero, this));
    GameControl.levels.add(new Level(hero, this));
    GameControl.levels.add(new Level(hero, this));
    GameControl.levels.add(new Level(hero, this));

    // Adiciona árvores a todos os níveis
    for (Level level : GameControl.levels) {
      level.addTrees();
    }
  }

  public static Level getLevel(int index) {
    if (index < 0 || index >= levels.size()) {
      System.out.println("Index fora do limite");
      return null;
    }

    return levels.get(index);
  }

  public void nextLevel() {
    if (GameControl.currentLevelIndex >= GameControl.levels.size() - 1) {
      System.out.println("Fim do jogo");
      return;
    }
    GameControl.currentLevelIndex++;
    this.updateCameraToHero();
  }

  public static Posicao getCameraPosition() {
    return GameControl.cameraPosition;
  }

  public static Level getCurrentLevel() {
    return levels.get(currentLevelIndex);
  }

  public void updateCamera(Posicao posicao) {
    int linha = posicao.getLinha();
    int coluna = posicao.getColuna();

    int cameraLinha = Math.max(0, Math.min(linha - Consts.RES_X / 2, Consts.MUNDO_ALTURA - Consts.RES_X));
    int cameraColuna = Math.max(0, Math.min(coluna - Consts.RES_Y / 2, Consts.MUNDO_LARGURA - Consts.RES_Y));

    GameControl.cameraPosition = new Posicao(cameraLinha, cameraColuna);
  }

  public void updateCameraToHero() {
    this.updateCamera(new Posicao(this.hero.getLinha(), this.hero.getColuna()));
  }

  public void desenhaTudo(ArrayList<Entity> personagens) {
    for (int i = 0; i < personagens.size(); i++) {
      personagens.get(i).autoDesenho();
    }
  }

  public void processaTudo(ArrayList<Entity> personagens) {
    for (int i = 0; i < personagens.size(); i++) {
      Entity personagem = personagens.get(i);
      if (hero.isSamePosition(personagem.getLinha(), personagem.getColuna())) {
        if (personagem.isTransposable() && personagem.isMortal())
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
    for (int i = 0; i < personagens.size(); i++) {
      Entity personagemAtual = personagens.get(i);
      if (personagemAtual.isSamePosition(personagem.getLinha(), personagem.getColuna()))
        return personagemAtual.isTransposable();
    }
    return true;
  }
}
