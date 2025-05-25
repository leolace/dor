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
  // Não inicializar aqui - fazer isso no momento do salvamento/carregamento
  private static final long serialVersionUID = 1L;
  // Mensagens para salvar/carregar jogo
  private static final String GAME_SAVED_MESSAGE = "Jogo salvo com sucesso!";
  private static final String GAME_LOADED_MESSAGE = "Jogo carregado com sucesso!";
  private static final String GAME_SAVE_ERROR = "Erro ao salvar o jogo!";
  private static final String GAME_LOAD_ERROR = "Erro ao carregar o jogo!";

  // Controle para exibição de mensagens de sistema
  public boolean showSystemMessage = false;
  private String systemMessage = "";
  private long systemMessageTime = 0;
  private static final long SYSTEM_MESSAGE_DURATION = 3000; // 3 segundos de exibição

  // Nome do arquivo para salvar o jogo
  private static final String SAVE_FILE = "savegame.dat";

  public final int heroLinha;
  public final int heroColuna;
  public final int heroHealth;
  public final int levelIndex;

  public SaveGameData() {
    // Obter o herói diretamente do GameControl
    Hero hero = GameControl.getHero();
    if (hero != null) {
      this.heroLinha = hero.getLinha();
      this.heroColuna = hero.getColuna();
      this.heroHealth = hero.getHealth();
    } else {
      // Valores padrão se o herói não estiver disponível
      this.heroLinha = 10;
      this.heroColuna = 10;
      this.heroHealth = 100;
    }
    this.levelIndex = GameControl.getCurrentLevelIndex();
  }

  /**
   * Salva o estado atual do jogo em um arquivo
   */
  public void saveGame() {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
      // Cria um objeto que contém todos os dados necessários para recuperar o estado
      // do jogo
      SaveGameData saveData = new SaveGameData();

      // Salva os dados
      outputStream.writeObject(saveData);

      // Mostra mensagem de sucesso
      showSystemMessage(GAME_SAVED_MESSAGE);
    } catch (IOException e) {
      e.printStackTrace();
      showSystemMessage(GAME_SAVE_ERROR);
    }
  }

  /**
   * Carrega o estado do jogo a partir de um arquivo salvo
   */
  public void loadGame() {
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
      // Carrega os dados salvos
      SaveGameData saveData = (SaveGameData) inputStream.readObject();

      // Obter o herói diretamente do GameControl
      Hero hero = GameControl.getHero();
      if (hero != null) {
        // Restaura os dados do herói
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

  /**
   * Classe interna para armazenar os dados salvos do jogo
   */

  /**
   * Exibe uma mensagem de sistema temporária na tela
   * 
   * @param message A mensagem a ser exibida
   */
  public void showSystemMessage(String message) {
    this.systemMessage = message;
    this.showSystemMessage = true;
    this.systemMessageTime = System.currentTimeMillis();
  }

  /**
   * Desenha a mensagem de sistema na tela, se houver uma para exibir
   * 
   * @param g O contexto gráfico para desenho
   */
  public void drawSystemMessage(Graphics g) {
    if (!showSystemMessage)
      return;

    // Verifica se a mensagem deve ser removida
    if (System.currentTimeMillis() - systemMessageTime > SYSTEM_MESSAGE_DURATION) {
      showSystemMessage = false;
      return;
    }

    // Desenha o fundo da mensagem
    g.setColor(new java.awt.Color(0, 0, 0, 150));

    // Configura a fonte
    g.setFont(g.getFont().deriveFont(18f).deriveFont(java.awt.Font.BOLD));
    java.awt.FontMetrics metrics = g.getFontMetrics();
    int messageWidth = metrics.stringWidth(systemMessage) + 40; // Adiciona margem

    // Calcula a posição
    int x = (Consts.RES_X * Consts.CELL_SIDE - messageWidth) / 2;
    int y = 70; // Um pouco abaixo do topo

    // Desenha o retângulo de fundo
    g.fillRect(x, y - metrics.getHeight(), messageWidth, metrics.getHeight() + 20);

    // Desenha a borda
    g.setColor(java.awt.Color.WHITE);
    g.drawRect(x, y - metrics.getHeight(), messageWidth, metrics.getHeight() + 20);

    // Desenha o texto
    g.setColor(java.awt.Color.GREEN);
    g.drawString(systemMessage, x + 20, y);
  }
}
