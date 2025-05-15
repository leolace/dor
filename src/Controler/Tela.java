package Controler;

import Modelo.Personagem;
import Modelo.Caveira;
import Modelo.Hero;
import Modelo.Chaser;
import Modelo.BichinhoVaiVemHorizontal;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.BichinhoVaiVemVertical;
import Modelo.ZigueZague;
import Auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {
  private Hero heroi;
  private ArrayList<Personagem> personagens = new ArrayList<Personagem>();
  private ControleDeJogo cj = new ControleDeJogo();
  private Graphics g2;
  private int cameraLinha = 0;
  private int cameraColuna = 0;

  public Tela() {
    Desenho.setCenario(this);
    initUiComponents();

    /* mouse */
    this.addMouseListener(this);

    /* teclado */
    this.addKeyListener(this);

    /* Cria a janela do tamanho do tabuleiro + insets (bordas) da janela */
    this.setSize(Consts.RES_X * Consts.CELL_SIDE + getInsets().left + getInsets().right,
        Consts.RES_Y * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

    /* Cria faseAtual adiciona personagens */
    heroi = new Hero("robbo.png");
    heroi.setPosicao(10, 10);
    this.addPersonagem(heroi);
    this.atualizaCamera();

    ZigueZague zz = new ZigueZague("robo.png");
    zz.setPosicao(0, 0);
    this.addPersonagem(zz);

    BichinhoVaiVemHorizontal bBichinhoH = new BichinhoVaiVemHorizontal("roboPink.png");
    bBichinhoH.setPosicao(3, 3);
    this.addPersonagem(bBichinhoH);

    BichinhoVaiVemHorizontal bBichinhoH2 = new BichinhoVaiVemHorizontal("roboPink.png");
    bBichinhoH2.setPosicao(6, 6);
    this.addPersonagem(bBichinhoH2);

    BichinhoVaiVemVertical bVv = new BichinhoVaiVemVertical("caveira.png");
    bVv.setPosicao(10, 10);
    this.addPersonagem(bVv);

    Caveira bV = new Caveira("caveira.png");
    bV.setPosicao(9, 1);
    this.addPersonagem(bV);

    Chaser chase = new Chaser("chaser.png");
    chase.setPosicao(12, 12);
    this.addPersonagem(chase);

  }

  public int getCameraLinha() {
    return cameraLinha;
  }

  public int getCameraColuna() {
    return cameraColuna;
  }

  public boolean ehPosicaoValida(Posicao p) {
    return cj.ehPosicaoValida(this.personagens, p);
  }

  public void addPersonagem(Personagem umPersonagem) {
    personagens.add(umPersonagem);
  }

  public void removePersonagem(Personagem umPersonagem) {
    personagens.remove(umPersonagem);
  }

  public Graphics getGraphicsBuffer() {
    return g2;
  }

  public void paint(Graphics gOld) {
    Graphics g = this.getBufferStrategy().getDrawGraphics();
    /* Criamos um contexto gráfico */
    g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
    /**
     * ***********Desenha cenário de fundo*************
     */
    for (int i = 0; i < Consts.RES_X; i++) {
      for (int j = 0; j < Consts.RES_Y; j++) {
        int mapaLinha = cameraLinha + i;
        int mapaColuna = cameraColuna + j;

        if (mapaLinha < Consts.MUNDO_ALTURA && mapaColuna < Consts.MUNDO_LARGURA) {
          try {
            Image newImage = Toolkit.getDefaultToolkit().getImage(
                new java.io.File("../").getCanonicalPath() + Consts.PATH + "blackTile.png");
            g2.drawImage(newImage,
                j * Consts.CELL_SIDE, i * Consts.CELL_SIDE,
                Consts.CELL_SIDE, Consts.CELL_SIDE, null);
          } catch (IOException ex) {
            Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      }
    }
    if (!this.personagens.isEmpty()) {
      this.cj.desenhaTudo(personagens);
      this.cj.processaTudo(personagens);
      this.repaintHeroi();
    }

    g.dispose();
    g2.dispose();
    if (!getBufferStrategy().contentsLost()) {
      getBufferStrategy().show();
    }
  }

  private void desenhaHeroi() {
    heroi.autoDesenho();
  }

  private void atualizaCamera() {
    int linha = heroi.getPosicao().getLinha();
    int coluna = heroi.getPosicao().getColuna();

    cameraLinha = Math.max(0, Math.min(linha - Consts.RES_X / 2, Consts.MUNDO_ALTURA - Consts.RES_X));
    cameraColuna = Math.max(0, Math.min(coluna - Consts.RES_Y / 2, Consts.MUNDO_LARGURA - Consts.RES_Y));
  }

  public void go() {
    TimerTask task = new TimerTask() {
      public void run() {
        repaint();
        // repaintHeroi();
      }
    };
    Timer timer = new Timer();
    timer.schedule(task, 0, Consts.PERIOD);
  }

  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_C) {
      this.personagens.clear();
    } else if (e.getKeyCode() == KeyEvent.VK_UP) {
      heroi.moveUp();
    } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      heroi.moveDown();
    } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      heroi.moveLeft();
    } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      heroi.moveRight();
    }
    this.atualizaCamera();
    this.setTitle("-> Cell: " + (heroi.getPosicao().getColuna()) + ", "
        + (heroi.getPosicao().getLinha()));

    this.repaintHeroi();
    // repaint(); /* invoca o paint imediatamente, sem aguardar o refresh */
  }

  public void mousePressed(MouseEvent e) {
    /* Clique do mouse desligado */
    int x = e.getX();
    int y = e.getY();

    this.setTitle("X: " + x + ", Y: " + y
        + " -> Cell: " + (y / Consts.CELL_SIDE) + ", " + (x / Consts.CELL_SIDE));

    this.heroi.getPosicao().setPosicao(y / Consts.CELL_SIDE, x / Consts.CELL_SIDE);

    repaint();
  }

  /**
   * Redesenha apenas o heroi na tela, sem atualizar os outros personagens.
   */
  public void repaintHeroi() {
    // Desenha o heroi
    heroi.autoDesenho();

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
