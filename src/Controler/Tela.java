package Controler;

import Modelo.Caveira;
import Modelo.Hero;
import Modelo.Chaser;
import Modelo.HorizontalBouncer;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.EntityGenerator;
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
    Level fase = GameControl.getLevel(0);

    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>("robo.png", ZigueZague.class, 10,
        20);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    EntityGenerator<VerticalBouncer> vBouncerGenerator = new EntityGenerator<VerticalBouncer>("caveira.png", VerticalBouncer.class, 10,
        15);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());
    fase.addAllPersonagens(vBouncerGenerator.getEntities());

    EntityGenerator<Caveira> caveiraGenerator = new EntityGenerator<Caveira>("skoot.png", Caveira.class, 10,
        15);
    fase.addAllPersonagens(caveiraGenerator.getEntities());

    Chaser chase = new Chaser("chaser.png");
    chase.setPosicao(20, 5);
    fase.addPersonagem(chase);
  }

  private void assembleLevel2() {
    this.hero.setPosicao(10, 10);
    Level fase = GameControl.getLevel(1);

    HorizontalBouncer hBouncer = new HorizontalBouncer("roboPink.png");
    hBouncer.setPosicao(3, 3);
    fase.addPersonagem(hBouncer);
  }

  public Graphics getGraphicsBuffer() {
    return g2;
  }

  public void paint(Graphics gOld) {
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

      // Desenha a barra de vida
      this.drawHealthBar(g2);
      this.drawLevelIndicator(g2);
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

  private void nextLevel() {
    this.gameControl.nextLevel();
    this.setTitle("Nivel " + (GameControl.getCurrentLevelIndex() + 1));
    repaint();
  }

  public void mousePressed(MouseEvent e) {
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

  private void drawLevelIndicator(Graphics g) {
    g.setColor(java.awt.Color.YELLOW);
    g.setFont(g.getFont().deriveFont(20f).deriveFont(java.awt.Font.BOLD));
    g.drawString("Level: " + (GameControl.getCurrentLevelIndex() + 1), 10, 880);
  }

  /**
   * Desenha a barra de vida do herói na tela
   * 
   * @param g objeto Graphics para desenho
   */
  private void drawHealthBar(Graphics g) {
    int healthBarWidth = 200;
    int healthBarHeight = 20;
    int healthBarX = 10;
    int healthBarY = 10;

    // Desenha o contorno da barra de vida
    g.setColor(java.awt.Color.BLACK);
    g.drawRect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);

    // Calcula a largura atual da barra de vida baseado na porcentagem de vida
    int currentHealthWidth = (int) ((hero.getHealth() / (float) hero.getMaxHealth()) * healthBarWidth);

    // Define a cor da barra baseada na quantidade de vida
    if (hero.getHealth() > 70) {
      g.setColor(java.awt.Color.GREEN);
    } else if (hero.getHealth() > 30) {
      g.setColor(java.awt.Color.YELLOW);
    } else {
      g.setColor(java.awt.Color.RED);
    }

    // Desenha a barra de vida atual
    g.fillRect(healthBarX, healthBarY, currentHealthWidth, healthBarHeight);

    // Mostra o valor numérico da vida
    g.setColor(java.awt.Color.BLACK);
    g.drawString(hero.getHealth() + "/" + hero.getMaxHealth(), healthBarX + healthBarWidth / 2 - 15, healthBarY + 15);
  }
}
