package Controler;

import Modelo.Chaser;
import Modelo.Entity;
import Modelo.Hero;
import Modelo.HealthPotion;
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

      // Verifica colisão com o herói
      if (hero.isSamePosition(personagem.getLinha(), personagem.getColuna())) {
        // Verifica se é uma poção de cura
        if (personagem instanceof HealthPotion) {
          HealthPotion potion = (HealthPotion) personagem;
          hero.heal(potion.getHealAmount());
          personagens.remove(personagem);
          System.out.println("Herói curou " + potion.getHealAmount() + " pontos de vida!");
        }
        // Se for um inimigo ou obstáculo mortal, causa dano
        if (personagem.isDangerous()) {
          // Aplica 10 de dano ao herói quando ele colide com um inimigo
          hero.takeDamage(10);
          System.out.println("Herói sofreu 10 pontos de dano! Vida atual: " + hero.getHealth());

          // Se o herói morreu, reinicia o nível
          if (!hero.isAlive()) {
            restartLevel();
          }
        }
        // Se for um item coletável e mortal, remove-o
        if (personagem.isTransposable() && personagem.isMortal()) {
          personagens.remove(personagem);
        }
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

  /**
   * Reinicia o nível atual
   * Reposiciona o herói e restaura sua vida
   */
  public void restartLevel() {
    // Restaura a vida do herói
    this.hero.heal(this.hero.getMaxHealth());

    // Reposiciona o herói em uma posição inicial segura
    this.hero.setPosicao(10, 10);

    // Atualiza a câmera para a nova posição do herói
    this.updateCameraToHero();

    System.out.println("Nível reiniciado!");
  }
}
