package Controler;

import Modelo.Chaser;
import Modelo.Entity;
import Modelo.Hero;
import Modelo.Key;
import Modelo.HealthPotion;
import java.io.Serializable;
import java.util.ArrayList;

import Auxiliar.Consts;
import Auxiliar.Posicao;

public class GameControl implements Serializable {
  private static Posicao cameraPosition;
  private static Hero hero;
  private static int currentLevelIndex = 0;
  private static ArrayList<Level> levels = new ArrayList<Level>();
  public static boolean isGameWon = false;

  // Versão da serialização para compatibilidade
  private static final long serialVersionUID = 1L;

  public GameControl(Hero heroo) {
    hero = heroo;
    cameraPosition = new Posicao(hero.getLinha(), hero.getColuna());

    this.assembleLevels();
  }

  private void assembleLevels() {
    // Adiciona 5 níveis
    GameControl.levels.add(new Level(hero, this));
    GameControl.levels.add(new Level(hero, this));
    GameControl.levels.add(new Level(hero, this));
    GameControl.levels.add(new Level(hero, this));
    GameControl.levels.add(new Level(hero, this));
  }

  public static Level getLevel(int index) {
    if (index < 0 || index >= levels.size()) {
      System.out.println("Index fora do limite");
      return null;
    }

    return levels.get(index);
  }

  public static boolean isLastLevel() {
    return currentLevelIndex == levels.size() - 1;
  }

  public static ArrayList<Level> getLevels() {
    return levels;
  }

  public static int getCurrentLevelIndex() {
    return currentLevelIndex;
  }

  public void nextLevel() {
    if (GameControl.currentLevelIndex >= GameControl.levels.size() - 1) {
      return;
    }
    GameControl.currentLevelIndex++;
    updateCameraToHero();
  }

  public static Posicao getCameraPosition() {
    return GameControl.cameraPosition;
  }

  public static Level getCurrentLevel() {
    return levels.get(currentLevelIndex);
  }

  public static void setCurrentLevelIndex(int index) {
    GameControl.currentLevelIndex = index;
  }

  public static void updateCamera(Posicao posicao) {
    int linha = posicao.getLinha();
    int coluna = posicao.getColuna();

    int cameraLinha = Math.max(0, Math.min(linha - Consts.RES_X / 2, Consts.MUNDO_ALTURA - Consts.RES_X));
    int cameraColuna = Math.max(0, Math.min(coluna - Consts.RES_Y / 2, Consts.MUNDO_LARGURA - Consts.RES_Y));

    GameControl.cameraPosition = new Posicao(cameraLinha, cameraColuna);
  }

  public static void updateCameraToHero() {
    updateCamera(new Posicao(hero.getLinha(), hero.getColuna()));
  }

  public void desenhaTudo(ArrayList<Entity> personagens) {
    for (int i = 0; i < personagens.size(); i++) {
      personagens.get(i).autoDesenho();
    }
  }

  public void processaTudo(ArrayList<Entity> personagens) {
    for (int i = 0; i < personagens.size(); i++) {
      Entity entity = personagens.get(i);

      // Verifica colisão com o herói
      if (hero.isSamePosition(entity.getLinha(), entity.getColuna())) {
        // Se o herói colidir com a chave, avança para o próximo nível
        if (entity instanceof Key) {
          if (GameControl.isLastLevel()) {
            // aparecer tela de ganhou
            GameControl.isGameWon = true;
          }

          this.nextLevel();
        }

        // Verifica se é uma poção de cura
        if (entity instanceof HealthPotion) {
          HealthPotion potion = (HealthPotion) entity;
          hero.heal(potion.getHealAmount());
          personagens.remove(entity);
        } // Se for um inimigo ou obstáculo mortal, causa dano
        if (entity.isDangerous()) {
          hero.takeDamage(50);

          // Se não estiver vivo e não houver métodos visuais para notificar,
          // simplesmente reinicia o nível
          if (!hero.isAlive()) {
            // Tratar a morte será responsabilidade da Tela
          }
        }

        // Se for um item coletável e mortal, remove-o
        if (entity.isTransposable() && entity.isMortal()) {
          personagens.remove(entity);
        }
      }

      if (entity instanceof Chaser) {
        ((Chaser) entity).computeDirection(hero);
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
    // Ressuscita o herói explicitamente
    hero.resurrect();

    // Reposiciona o herói em uma posição inicial segura
    hero.setPosicao(10, 10);

    // Atualiza a câmera para a nova posição do herói
    updateCameraToHero();

    GameControl.getCurrentLevel().restartHealthPotions();
    GameControl.getCurrentLevel().restartLevel();
    GameControl.getCurrentLevel().restartKey();
  }

  /**
   * Retorna o objeto herói
   * 
   * @return O herói do jogo
   */
  public static Hero getHero() {
    return hero;
  }
}
