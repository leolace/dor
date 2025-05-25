package Auxiliar;

import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

/**
 * Classe responsável por controlar e desenhar a tela de morte do jogo
 */
public class DeathScreen implements Serializable {
    private boolean isActive = false;
    private static final String DEATH_MESSAGE = "Você morreu! Pressione a tecla [espaço] para continuar...";
    
    /**
     * Construtor padrão da tela de morte
     */
    public DeathScreen() {
        // Inicializa no estado inativo
        this.isActive = false;
    }
    
    /**
     * Ativa a tela de morte
     */
    public void activate() {
        this.isActive = true;
    }
    
    /**
     * Desativa a tela de morte
     */
    public void deactivate() {
        this.isActive = false;
    }
    
    /**
     * Verifica se a tela de morte está ativa
     * @return true se estiver ativa, false caso contrário
     */
    public boolean isActive() {
        return this.isActive;
    }
    
    /**
     * Desenha a tela de morte
     * @param g Contexto gráfico para desenho
     */
    public void draw(Graphics g) {
        if (!isActive) return;
        
        // Fundo escuro semi-transparente apenas para a área do jogo
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
