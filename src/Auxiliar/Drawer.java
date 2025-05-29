package Auxiliar;

import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ImageIcon;

import Controler.GameControl;
import Controler.Tela;
import Modelo.Hero;

public class Drawer implements Serializable {
  private static Tela canvas;

  public static void setCanvas(Tela tela) {
    canvas = tela;
  }

  private static Graphics getCanvasGraphics() {
    return canvas.getGraphicsBuffer();
  }

  // Desenha o fundo (grama) independentemente do estado do jogo
  public static void drawGameTerrain() {
    for (int i = 0; i < Consts.RES_X; i++) {
      for (int j = 0; j < Consts.RES_Y; j++) {
        int mapaLinha = GameControl.getCameraPosition().getLinha() + i;
        int mapaColuna = GameControl.getCameraPosition().getColuna() + j;

        if (mapaLinha < Consts.MUNDO_ALTURA && mapaColuna < Consts.MUNDO_LARGURA) {
          Imagem grassImage = new Imagem("grass.png", getCanvasGraphics());
          grassImage.drawImage(j * Consts.CELL_SIDE, i * Consts.CELL_SIDE,
              Consts.CELL_SIDE, Consts.CELL_SIDE);
        }
      }
    }
  }

  /**
   * Desenha o texto da missão na tela
   * 
   * @param graphics objeto Graphics para desenho
   */
  public static void drawMissionText() {
    String missionText = "Missão: Encontre a chave para avançar de fase";
    Graphics graphics = getCanvasGraphics();

    // Configura a fonte e a cor
    graphics.setFont(graphics.getFont().deriveFont(16f).deriveFont(java.awt.Font.BOLD));

    // Desenha um fundo semi-transparente para melhor legibilidade
    java.awt.FontMetrics metrics = graphics.getFontMetrics();
    int textWidth = metrics.stringWidth(missionText);
    int textHeight = metrics.getHeight();

    int x = 15;
    int y = 55; // Posicionado abaixo da barra de vida

    graphics.setColor(new java.awt.Color(0, 0, 0, 150));
    graphics.fillRect(x - 5, y - textHeight, textWidth + 10, textHeight + 10);

    // Desenha o texto da missão
    graphics.setColor(java.awt.Color.YELLOW);
    graphics.drawString(missionText, x, y);
  }

  public static void drawLevelIndicator() {
    Graphics graphics = getCanvasGraphics();

    graphics.setColor(java.awt.Color.YELLOW);
    graphics.setFont(graphics.getFont().deriveFont(20f).deriveFont(java.awt.Font.BOLD));
    graphics.drawString("Level: " + (GameControl.getCurrentLevelIndex() + 1), 10, 880);
  }

  /**
   * Desenha a barra de vida do herói na tela
   * 
   * @param graphics objeto Graphics para desenho
   */
  public static void drawHealthBar() {
    int healthBarWidth = 200;
    int healthBarHeight = 20;
    int healthBarX = 10;
    int healthBarY = 10;
    Hero hero = GameControl.getHero();
    Graphics graphics = getCanvasGraphics();

    // Desenha o contorno da barra de vida
    graphics.setColor(java.awt.Color.BLACK);
    graphics.drawRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);

    // Calcula a largura atual da barra de vida baseado na porcentagem de vida
    int currentHealthWidth = (int) ((hero.getHealth() / (float) hero.getMaxHealth()) * healthBarWidth);

    // Define a cor da barra baseada na quantidade de vida
    if (hero.getHealth() > 70) {
      graphics.setColor(java.awt.Color.GREEN);
    } else if (hero.getHealth() > 30) {
      graphics.setColor(java.awt.Color.YELLOW);
    } else {
      graphics.setColor(java.awt.Color.RED);
    }

    // Desenha a barra de vida atual
    graphics.fillRect(healthBarX, healthBarY, currentHealthWidth, healthBarHeight);

    // Mostra o valor numérico da vida
    graphics.setColor(java.awt.Color.BLACK);
    graphics.drawString(hero.getHealth() + "/" + hero.getMaxHealth(), healthBarX + healthBarWidth / 2 - 15,
        healthBarY + 15);
  }

  public static void drawImage(ImageIcon iImage, int iColuna, int iLinha) {
    int telaX = (iColuna - GameControl.getCameraPosition().getColuna()) * Consts.CELL_SIDE;
    int telaY = (iLinha - GameControl.getCameraPosition().getLinha()) * Consts.CELL_SIDE;

    if (telaX >= 0 && telaX < Consts.RES_X * Consts.CELL_SIDE &&
        telaY >= 0 && telaY < Consts.RES_Y * Consts.CELL_SIDE) {
      iImage.paintIcon(canvas, getCanvasGraphics(), telaX, telaY);
    }
  }

}
