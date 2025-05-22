package Controler;

import Modelo.Caveira;
import Modelo.Hero;
import Modelo.Chaser;
import Modelo.HorizontalBouncer;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Imagem;
import Auxiliar.Posicao;
import Modelo.VerticalBouncer;
import Modelo.ZigueZague;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {
  private Hero hero = new Hero("hero.png", new Posicao(10, 10));
  private ArrayList<Fase> levels = new ArrayList<Fase>();
  private int currentLevelIndex = 0;
  private Graphics g2;

  public Tela() {
    initUiComponents();
    Desenho.setTela(this);
    this.addMouseListener(this);
    this.addKeyListener(this);

    /* Cria a janela do tamanho do tabuleiro + insets (bordas) da janela */
    this.setSize(Consts.RES_X * Consts.CELL_SIDE + getInsets().left + getInsets().right,
        Consts.RES_Y * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

    /* Monta as fases */
    this.assembleLevel1();
    this.assembleLevel2();

    /* Ajusta a camera para a posição do personagem */
    this.getCurrentLevel().updateCameraToHero(this.hero);

    /* Inicia a primeira fase */
    Desenho.setLevel(this.getCurrentLevel());
  }

  private void assembleLevel1() {
    Fase fase = new Fase(this.hero);

    ZigueZague zigueZague = new ZigueZague("robo.png", new Posicao(5, 11));
    fase.addPersonagem(zigueZague);

    VerticalBouncer vBouncer = new VerticalBouncer("caveira.png", new Posicao(10, 10));
    fase.addPersonagem(vBouncer);

    Caveira bV = new Caveira("caveira.png", new Posicao(9, 1));
    fase.addPersonagem(bV);

    Chaser chase = new Chaser("chaser.png", new Posicao(12, 12));
    fase.addPersonagem(chase);

    this.levels.add(fase);
  }

  private void assembleLevel2() {
    Fase fase = new Fase(this.hero);

    HorizontalBouncer hBouncer = new HorizontalBouncer("roboPink.png", new Posicao(3, 3));
    fase.addPersonagem(hBouncer);

    this.levels.add(fase);
  }

  public Graphics getGraphicsBuffer() {
    return g2;
  }

  public Fase getCurrentLevel() {
    return this.levels.get(this.currentLevelIndex);
  }

  public void paint(Graphics gOld) {
    System.out.println(this.getCurrentLevel().getCameraPosition().getLinha() + ", "
        + this.getCurrentLevel().getCameraPosition().getColuna());
    Graphics g = this.getBufferStrategy().getDrawGraphics();
    /* Criamos um contexto gráfico */
    g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
    /**
     * ***********Desenha cenário de fundo*************
     */
    for (int i = 0; i < Consts.RES_X; i++) {
      for (int j = 0; j < Consts.RES_Y; j++) {
        int mapaLinha = this.getCurrentLevel().getCameraPosition().getLinha() + i;
        int mapaColuna = this.getCurrentLevel().getCameraPosition().getColuna() + j;

        if (mapaLinha < Consts.MUNDO_ALTURA && mapaColuna < Consts.MUNDO_LARGURA) {
          Imagem grassImage = new Imagem("grass.png", g2);
          grassImage.drawImage(j * Consts.CELL_SIDE, i * Consts.CELL_SIDE,
              Consts.CELL_SIDE, Consts.CELL_SIDE);
        }
      }
    }
    if (!this.getCurrentLevel().getPersonagens().isEmpty()) {
      this.getCurrentLevel().desenhaTudo(this.getCurrentLevel().getPersonagens());
      this.getCurrentLevel().processaTudo(this.getCurrentLevel().getPersonagens());
      hero.autoDesenho();
    }

    this.getCurrentLevel().updateCameraToHero(this.hero);

    g.dispose();
    g2.dispose();
    if (!getBufferStrategy().contentsLost()) {
      getBufferStrategy().show();
    }
  }

  public void go() {
    TimerTask task = new TimerTask() {
      public void run() {
        repaint();
      }
    };
    Timer timer = new Timer();
    timer.schedule(task, 0, Consts.PERIOD);
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      hero.moveUp();
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      hero.moveDown();
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      hero.moveLeft();
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      hero.moveRight();
    }
    this.getCurrentLevel().updateCameraToHero(this.hero);
    this.setTitle("-> Cell: " + (hero.getColuna()) + ", "
        + (hero.getLinha()));

    hero.autoDesenho();
    // repaint(); /* invoca o paint imediatamente, sem aguardar o refresh */
  }

  public void mousePressed(MouseEvent e) {
    /* Clique do mouse desligado */
    int x = e.getX();
    int y = e.getY();

    this.setTitle("X: " + x + ", Y: " + y
        + " -> Cell: " + (y / Consts.CELL_SIDE) + ", " + (x / Consts.CELL_SIDE));

    this.hero.setPosicao(y / Consts.CELL_SIDE, x / Consts.CELL_SIDE);

    repaint();
  }

  private void initUiComponents() {
    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("POO2023-1 - Skooter");
    setAlwaysOnTop(false);
    setAutoRequestFocus(false);
    setResizable(true);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE));
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE));

    pack();
  }

  public void nextLevel() {
    if (this.currentLevelIndex >= this.levels.size() - 1) {
      System.out.println("Fim do jogo");
      return;
    }
    this.currentLevelIndex++;
    this.getCurrentLevel().updateCameraToHero(this.hero);
  }

  public void mouseMoved(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON3)
      this.nextLevel();
  }

  public void mouseReleased(MouseEvent e) {
  }

  public void mouseEntered(MouseEvent e) {
  }

  public void mouseExited(MouseEvent e) {
  }

  public void mouseDragged(MouseEvent e) {
  }

  public void keyTyped(KeyEvent e) {
  }

  public void keyReleased(KeyEvent e) {
  }
}
