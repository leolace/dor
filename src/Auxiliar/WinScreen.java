package Auxiliar;

import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

/**
 * Classe responsável por controlar e desenhar a tela de vitória do jogo
 */
public class WinScreen implements Serializable {
  private boolean isActive = false;
  private static final String WIN_MESSAGE = "Você ganhou!";
  private static final String CREATORS_MESSAGE = "Criado por: Leonardo Gonsalez e Rafael Auada";

  /**
   * Construtor padrão da tela de vitória
   */
  public WinScreen() {
    // Inicializa no estado inativo
    this.isActive = false;
  }

  /**
   * Ativa a tela de vitória
   */
  public void activate() {
    this.isActive = true;
  }

  /**
   * Desativa a tela de vitória
   */
  public void deactivate() {
    this.isActive = false;
  }

  /**
   * Verifica se a tela de vitória está ativa
   * 
   * @return true se estiver ativa, false caso contrário
   */
  public boolean isActive() {
    return this.isActive;
  }

  /**
   * Desenha a tela de vitória
   * 
   * @param g Contexto gráfico para desenho
   */
  public void draw(Graphics g) {
    // Fundo escuro semi-transparente apenas para a área do jogo
    g.setColor(new Color(0, 0, 0, 200));
    g.fillRect(0, 0, Consts.RES_X * Consts.CELL_SIDE, Consts.RES_Y * Consts.CELL_SIDE);

    // Desenha a mensagem de vitória
    g.setColor(Color.GREEN);
    g.setFont(g.getFont().deriveFont(40f).deriveFont(Font.BOLD));

    // Centraliza a mensagem principal na área do jogo
    FontMetrics metrics = g.getFontMetrics();
    int x = (Consts.RES_X * Consts.CELL_SIDE - metrics.stringWidth(WIN_MESSAGE)) / 2;
    int y = (Consts.RES_Y * Consts.CELL_SIDE) / 2 - 30; // Posicionada um pouco acima do centro

    g.drawString(WIN_MESSAGE, x, y);
    
    // Desenha os nomes dos criadores
    g.setFont(g.getFont().deriveFont(20f));
    g.setColor(Color.YELLOW);
    
    metrics = g.getFontMetrics();
    x = (Consts.RES_X * Consts.CELL_SIDE - metrics.stringWidth(CREATORS_MESSAGE)) / 2;
    y = (Consts.RES_Y * Consts.CELL_SIDE) / 2 + 50; // Posicionada abaixo da mensagem principal
    
    g.drawString(CREATORS_MESSAGE, x, y);
  }
}
