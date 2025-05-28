package Auxiliar;

import java.awt.Graphics;

import Controler.GameControl;
import Modelo.Hero;

public class UIComponents {
  private Graphics graphics;
  private static UIComponents instance = null;

  private UIComponents(Graphics g) {
    this.graphics = g;
  }

  public static UIComponents getInstance(Graphics g) {
    if (instance == null) {
      instance = new UIComponents(g);
    } else {
      instance.graphics = g;
    }

    return instance;
  }

  /**
   * Desenha o texto da missão na tela
   * 
   * @param graphics objeto Graphics para desenho
   */
  public void drawMissionText() {
    String missionText = "Missão: Encontre a chave para avançar de fase";

    // Configura a fonte e a cor
    this.graphics.setFont(this.graphics.getFont().deriveFont(16f).deriveFont(java.awt.Font.BOLD));

    // Desenha um fundo semi-transparente para melhor legibilidade
    java.awt.FontMetrics metrics = this.graphics.getFontMetrics();
    int textWidth = metrics.stringWidth(missionText);
    int textHeight = metrics.getHeight();

    int x = 15;
    int y = 55; // Posicionado abaixo da barra de vida

    this.graphics.setColor(new java.awt.Color(0, 0, 0, 150));
    this.graphics.fillRect(x - 5, y - textHeight, textWidth + 10, textHeight + 10);

    // Desenha o texto da missão
    this.graphics.setColor(java.awt.Color.YELLOW);
    this.graphics.drawString(missionText, x, y);
  }

  public void drawLevelIndicator() {
    this.graphics.setColor(java.awt.Color.YELLOW);
    this.graphics.setFont(this.graphics.getFont().deriveFont(20f).deriveFont(java.awt.Font.BOLD));
    this.graphics.drawString("Level: " + (GameControl.getCurrentLevelIndex() + 1), 10, 880);
  }

  /**
   * Desenha a barra de vida do herói na tela
   * 
   * @param graphics objeto Graphics para desenho
   */
  public void drawHealthBar() {
    int healthBarWidth = 200;
    int healthBarHeight = 20;
    int healthBarX = 10;
    int healthBarY = 10;
    Hero hero = GameControl.getHero();

    // Desenha o contorno da barra de vida
    this.graphics.setColor(java.awt.Color.BLACK);
    this.graphics.drawRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);

    // Calcula a largura atual da barra de vida baseado na porcentagem de vida
    int currentHealthWidth = (int) ((hero.getHealth() / (float) hero.getMaxHealth()) * healthBarWidth);

    // Define a cor da barra baseada na quantidade de vida
    if (hero.getHealth() > 70) {
      this.graphics.setColor(java.awt.Color.GREEN);
    } else if (hero.getHealth() > 30) {
      this.graphics.setColor(java.awt.Color.YELLOW);
    } else {
      this.graphics.setColor(java.awt.Color.RED);
    }

    // Desenha a barra de vida atual
    this.graphics.fillRect(healthBarX, healthBarY, currentHealthWidth, healthBarHeight);

    // Mostra o valor numérico da vida
    this.graphics.setColor(java.awt.Color.BLACK);
    this.graphics.drawString(hero.getHealth() + "/" + hero.getMaxHealth(), healthBarX + healthBarWidth / 2 - 15, healthBarY + 15);
  }
}
