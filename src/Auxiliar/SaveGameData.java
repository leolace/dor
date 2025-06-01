package Auxiliar;

import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import Controler.GameControl;
import Modelo.Hero;

public class SaveGameData implements Serializable {
  private static final long serialVersionUID = 1L;
  private static final String GAME_SAVED_MESSAGE = "Jogo salvo com sucesso!";
  private static final String GAME_LOADED_MESSAGE = "Jogo carregado com sucesso!";
  private static final String GAME_SAVE_ERROR = "Erro ao salvar o jogo!";
  private static final String GAME_LOAD_ERROR = "Erro ao carregar o jogo!";

  public boolean showSystemMessage = false;
  private String systemMessage = "";
  private long systemMessageTime = 0;
  private static final long SYSTEM_MESSAGE_DURATION = 3000; // 3 segundos de exibição

  private static final String SAVE_FILE = "savegame.dat";

  public final int heroLinha;
  public final int heroColuna;
  public final int heroHealth;
  public final int levelIndex;

  public SaveGameData(Hero heroo) {
    Hero hero = heroo;
    this.heroLinha = hero.getLinha();
    this.heroColuna = hero.getColuna();
    this.heroHealth = hero.getHealth();

    this.levelIndex = GameControl.getCurrentLevelIndex();
  }

  public void saveGame(Hero heroo) {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
      SaveGameData saveData = new SaveGameData(heroo);

      // Salva os dados
      outputStream.writeObject(saveData);

      // Mostra mensagem de sucesso
      showSystemMessage(GAME_SAVED_MESSAGE);
    } catch (IOException e) {
      e.printStackTrace();
      showSystemMessage(GAME_SAVE_ERROR);
    }
  }

  public void loadGame() {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
      SaveGameData saveData = (SaveGameData) inputStream.readObject();

      Hero hero = GameControl.getHero();
      if (hero != null) {
        hero.setPosicao(saveData.heroLinha, saveData.heroColuna);
        hero.setHealth(saveData.heroHealth);
      }

      // Restaura o nível atual
      GameControl.setCurrentLevelIndex(saveData.levelIndex);

      // Atualiza a câmera
      GameControl.updateCameraToHero();

      // Mostra mensagem de sucesso
      showSystemMessage(GAME_LOADED_MESSAGE);
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
      showSystemMessage(GAME_LOAD_ERROR);
    }
  }

  public void showSystemMessage(String message) {
    this.systemMessage = message;
    this.showSystemMessage = true;
    this.systemMessageTime = System.currentTimeMillis();
  }

  public void drawSystemMessage(Graphics g) {
    if (!showSystemMessage)
      return;

    if (System.currentTimeMillis() - systemMessageTime > SYSTEM_MESSAGE_DURATION) {
      showSystemMessage = false;
      return;
    }

    g.setColor(new java.awt.Color(0, 0, 0, 150));

    g.setFont(g.getFont().deriveFont(18f).deriveFont(java.awt.Font.BOLD));
    java.awt.FontMetrics metrics = g.getFontMetrics();
    int messageWidth = metrics.stringWidth(systemMessage) + 40; // Adiciona margem

    int x = (Consts.RES_X * Consts.CELL_SIDE - messageWidth) / 2;
    int y = 70; // Um pouco abaixo do topo

    g.fillRect(x, y - metrics.getHeight(), messageWidth, metrics.getHeight() + 20);

    g.setColor(java.awt.Color.WHITE);
    g.drawRect(x, y - metrics.getHeight(), messageWidth, metrics.getHeight() + 20);

    g.setColor(java.awt.Color.GREEN);
    g.drawString(systemMessage, x + 20, y);
  }
}
