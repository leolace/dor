package Screens;

import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

import Auxiliar.Consts;

public class WinScreen implements Serializable {
  private static final String WIN_MESSAGE = "Você ganhou!";
  private static final String CREATORS_MESSAGE = "Criado por: Leonardo Gonsalez e Rafael Auada";

  public static void draw(Graphics g) {
    // Fundo escuro semi-transparente
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
