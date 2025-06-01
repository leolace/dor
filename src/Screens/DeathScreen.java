package Screens;

import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

import Auxiliar.Consts;

public class DeathScreen implements Serializable {
  private static final String DEATH_MESSAGE = "Você morreu! Pressione a tecla [espaço] para continuar...";

  public static void draw(Graphics g) {
    // Fundo escuro semi-transparente
    g.setColor(new Color(0, 0, 0, 200));
    g.fillRect(0, 0, Consts.RES_X * Consts.CELL_SIDE, Consts.RES_Y * Consts.CELL_SIDE);

    // Desenha a mensagem de morte
    g.setColor(Color.RED);
    g.setFont(g.getFont().deriveFont(30f).deriveFont(Font.BOLD));

    // Centraliza a mensagem na área do jogo
    FontMetrics metrics = g.getFontMetrics();
    int x = (Consts.RES_X * Consts.CELL_SIDE - metrics.stringWidth(DEATH_MESSAGE)) / 2;
    int y = (Consts.RES_Y * Consts.CELL_SIDE) / 2;

    g.drawString(DEATH_MESSAGE, x, y);
  }
}
