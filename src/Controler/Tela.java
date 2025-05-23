package Controler;

import Modelo.Caveira;
import Modelo.Hero;
import Modelo.Chaser;
import Modelo.HorizontalBouncer;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Imagem;
import Modelo.VerticalBouncer;
import Modelo.ZigueZague;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {
  private Hero hero;
  private Graphics g2;
  private GameControl gameControl;

  public Tela() {
    initUiComponents();
    Desenho.setTela(this);
    this.addMouseListener(this);
    this.addKeyListener(this);

    /* Cria a janela do tamanho do tabuleiro + insets (bordas) da janela */
    this.setSize(Consts.RES_X * Consts.CELL_SIDE + getInsets().left + getInsets().right,
        Consts.RES_Y * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

    /* Cria o heroi */
    this.hero = new Hero("hero.png");
    this.hero.setPosicao(10, 10);

    /* Inicia o game controller */
    this.gameControl = new GameControl(this.hero);

    /* Ajusta a camera para a posição do personagem */
    this.gameControl.updateCameraToHero();

    /* Monta as fases */
    this.assembleLevel1();
    this.assembleLevel2();
  }

  private void assembleLevel1() {
    Level fase = new Level(this.hero, this.gameControl);

    ZigueZague zigueZague = new ZigueZague("robo.png");
    zigueZague.setPosicao(5, 11);
    fase.addPersonagem(zigueZague);

    VerticalBouncer vBouncer = new VerticalBouncer("caveira.png");
    vBouncer.setPosicao(10, 10);
    fase.addPersonagem(vBouncer);

    Caveira bV = new Caveira("caveira.png");
    bV.setPosicao(9, 1);
    fase.addPersonagem(bV);

    Chaser chase = new Chaser("chaser.png");
    chase.setPosicao(12, 12);
    fase.addPersonagem(chase);

    this.gameControl.addLevel(fase);
  }

  private void assembleLevel2() {
    this.hero.setPosicao(10, 10);
    Level fase = new Level(this.hero, this.gameControl);

    HorizontalBouncer hBouncer = new HorizontalBouncer("roboPink.png");
    hBouncer.setPosicao(3, 3);
    fase.addPersonagem(hBouncer);

    this.gameControl.addLevel(fase);
  }

  public Graphics getGraphicsBuffer() {
    return g2;
  }

  public void paint(Graphics gOld) {
    System.out.println(GameControl.getCameraPosition().getLinha() + ", "
        + GameControl.getCameraPosition().getColuna());
    Graphics g = this.getBufferStrategy().getDrawGraphics();
    g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);

    for (int i = 0; i < Consts.RES_X; i++) {
      for (int j = 0; j < Consts.RES_Y; j++) {
        int mapaLinha = GameControl.getCameraPosition().getLinha() + i;
        int mapaColuna = GameControl.getCameraPosition().getColuna() + j;

        if (mapaLinha < Consts.MUNDO_ALTURA && mapaColuna < Consts.MUNDO_LARGURA) {
          Imagem grassImage = new Imagem("grass.png", g2);
          grassImage.drawImage(j * Consts.CELL_SIDE, i * Consts.CELL_SIDE,
              Consts.CELL_SIDE, Consts.CELL_SIDE);
        }
      }
    }

    Level currentLevel = GameControl.getCurrentLevel();
    if (!currentLevel.getPersonagens().isEmpty()) {
      this.gameControl.desenhaTudo(currentLevel.getPersonagens());
      this.gameControl.processaTudo(currentLevel.getPersonagens());
      hero.autoDesenho();
    }

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
    this.gameControl.updateCameraToHero();
    this.setTitle("-> Cell: " + (hero.getColuna()) + ", "
        + (hero.getLinha()));

    hero.autoDesenho();
  }

  private void nextLevelRepaint() {
    this.gameControl.nextLevel();
    repaint();
  }

  public void mousePressed(MouseEvent e) {
  }

  public void handleClick(MouseEvent e) {
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

  public void mouseMoved(MouseEvent e) {
  }

  public void mouseClicked(MouseEvent e) {
    if (e.getButton() == MouseEvent.BUTTON3)
      this.nextLevelRepaint();
    else
      handleClick(e);
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
