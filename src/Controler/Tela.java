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
  private boolean isDeathScreen = false; // Controla se está na tela de morte
  private static final String DEATH_MESSAGE = "Você morreu! Pressione qualquer tecla para continuar...";

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
    this.assembleLevel0();
    this.assembleLevel1();
    this.assembleLevel2();
    this.assembleLevel3();
    this.assembleLevel4();
  }

  private void assembleLevel0() {
    Level fase = GameControl.getLevel(0);

    // Inimigos ZigueZague
    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>("robo.png", ZigueZague.class, 5,
        15);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    // Inimigos VerticalBouncer
    EntityGenerator<VerticalBouncer> vBouncerGenerator = new EntityGenerator<VerticalBouncer>("caveira.png",
        VerticalBouncer.class, 5, 12);
    fase.addAllPersonagens(vBouncerGenerator.getEntities());

    // Inimigos Caveira
    EntityGenerator<Caveira> caveiraGenerator = new EntityGenerator<Caveira>("skoot.png", Caveira.class, 7, 12);
    fase.addAllPersonagens(caveiraGenerator.getEntities());
  }

  private void assembleLevel1() {
    this.hero.setPosicao(10, 10);
    Level fase = GameControl.getLevel(1);

    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>("robo.png", ZigueZague.class, 5,
        15);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    EntityGenerator<VerticalBouncer> vBouncerGenerator = new EntityGenerator<VerticalBouncer>("caveira.png",
        VerticalBouncer.class, 5, 12);
    fase.addAllPersonagens(vBouncerGenerator.getEntities());

    EntityGenerator<Caveira> caveiraGenerator = new EntityGenerator<Caveira>("skoot.png", Caveira.class, 5, 12);
    fase.addAllPersonagens(caveiraGenerator.getEntities());

    EntityGenerator<Chaser> chaserGenerator = new EntityGenerator<Chaser>("chaser.png", Chaser.class, 1,
        5);
    fase.addAllPersonagens(chaserGenerator.getEntities());
  }

  private void assembleLevel2() {
    this.hero.setPosicao(10, 10);
    Level fase = GameControl.getLevel(2);

    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>("robo.png", ZigueZague.class, 5,
        10);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    EntityGenerator<Chaser> chaserGenerator = new EntityGenerator<Chaser>("chaser.png", Chaser.class, 2,
        5);
    fase.addAllPersonagens(chaserGenerator.getEntities());

    EntityGenerator<Caveira> caveiraGenerator = new EntityGenerator<Caveira>("skoot.png", Caveira.class, 10, 12);
    fase.addAllPersonagens(caveiraGenerator.getEntities());
  }

  private void assembleLevel3() {
    this.hero.setPosicao(10, 10);
    Level fase = GameControl.getLevel(3);

    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>("robo.png", ZigueZague.class, 5,
        10);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    EntityGenerator<Caveira> caveiraGenerator = new EntityGenerator<Caveira>("skoot.png", Caveira.class, 10, 20);
    fase.addAllPersonagens(caveiraGenerator.getEntities());

    EntityGenerator<VerticalBouncer> vBouncerGenerator = new EntityGenerator<VerticalBouncer>("caveira.png",
        VerticalBouncer.class, 10, 15);
    fase.addAllPersonagens(vBouncerGenerator.getEntities());

    EntityGenerator<HorizontalBouncer> hBouncerGenerator = new EntityGenerator<HorizontalBouncer>("caveira.png",
        HorizontalBouncer.class, 10, 15);
    fase.addAllPersonagens(hBouncerGenerator.getEntities());

    EntityGenerator<Chaser> chaserGenerator = new EntityGenerator<Chaser>("chaser.png", Chaser.class, 3,
        5);
    fase.addAllPersonagens(chaserGenerator.getEntities());
  }

  private void assembleLevel4() {
    this.hero.setPosicao(10, 10);
    Level fase = GameControl.getLevel(4);

    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>("robo.png", ZigueZague.class, 15,
        25);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    EntityGenerator<HorizontalBouncer> hBouncerGenerator = new EntityGenerator<HorizontalBouncer>("roboPink.png",
        HorizontalBouncer.class, 10, 20);
    fase.addAllPersonagens(hBouncerGenerator.getEntities());

    EntityGenerator<VerticalBouncer> vBouncerGenerator = new EntityGenerator<VerticalBouncer>("caveira.png",
        VerticalBouncer.class, 10, 20);
    fase.addAllPersonagens(vBouncerGenerator.getEntities());

    EntityGenerator<Caveira> caveiraGenerator = new EntityGenerator<Caveira>("skoot.png", Caveira.class, 10, 20);
    fase.addAllPersonagens(caveiraGenerator.getEntities());

    EntityGenerator<Chaser> chaserGenerator = new EntityGenerator<Chaser>("chaser.png", Chaser.class, 5,
        5);
    fase.addAllPersonagens(chaserGenerator.getEntities());
  }

  public Graphics getGraphicsBuffer() {
    return g2;
  }

  public void paint(Graphics gOld) {
    Graphics g = this.getBufferStrategy().getDrawGraphics();
    g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);

    // Desenha o fundo (grama) independentemente do estado do jogo
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

    // Verifica se o jogador está morto e ainda não está na tela de morte
    if (!hero.isAlive() && !this.isDeathScreen) {
      // Define que estamos na tela de morte
      this.isDeathScreen = true;
    }

    // Se estiver na tela de morte, desenha a mensagem
    if (this.isDeathScreen) {
      drawDeathScreen(g2);
    } else {
      // Desenho normal do jogo quando o jogador está vivo
      Level currentLevel = GameControl.getCurrentLevel();
      if (!currentLevel.getPersonagens().isEmpty()) {
        this.gameControl.desenhaTudo(currentLevel.getPersonagens());
        this.gameControl.processaTudo(currentLevel.getPersonagens());
        hero.autoDesenho();

        // Desenha a barra de vida
        this.drawHealthBar(g2);
        this.drawLevelIndicator(g2);
      }
    }

    g.dispose();
    g2.dispose();
    if (!getBufferStrategy().contentsLost()) {
      getBufferStrategy().show();
    }
  }

  /**
   * Desenha a tela de morte com mensagem para o jogador
   * 
   * @param g objeto Graphics para desenho
   */
  private void drawDeathScreen(Graphics g) {
    // Fundo escuro semi-transparente apenas para a área do jogo
    g.setColor(new java.awt.Color(0, 0, 0, 200));
    g.fillRect(0, 0, Consts.RES_X * Consts.CELL_SIDE, Consts.RES_Y * Consts.CELL_SIDE);

    // Desenha a mensagem de morte
    g.setColor(java.awt.Color.RED);
    g.setFont(g.getFont().deriveFont(30f).deriveFont(java.awt.Font.BOLD));
    
    // Centraliza a mensagem na área do jogo
    java.awt.FontMetrics metrics = g.getFontMetrics();
    int x = (Consts.RES_X * Consts.CELL_SIDE - metrics.stringWidth(DEATH_MESSAGE)) / 2;
    int y = (Consts.RES_Y * Consts.CELL_SIDE) / 2;
    
    g.drawString(DEATH_MESSAGE, x, y);
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
    // Se estiver na tela de morte, qualquer tecla reinicia o jogo
    if (this.isDeathScreen) {
      // Ressuscita o herói explicitamente
      this.hero.resurrect();
      // Reinicia o nível
      this.gameControl.restartLevel();
      // Sai do estado de tela de morte
      this.isDeathScreen = false;
      return;
    }

    // Comportamento normal quando o jogador está vivo
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

  /**
   * Define se a tela de morte deve ser exibida
   * 
   * @param isDeathScreen true para mostrar a tela de morte, false para desabilitar
   */
  public void setDeathScreen(boolean isDeathScreen) {
    this.isDeathScreen = isDeathScreen;
  }

  /**
   * Verifica se a tela de morte está sendo exibida
   * 
   * @return true se estiver na tela de morte, false caso contrário
   */
  public boolean isDeathScreen() {
    return this.isDeathScreen;
  }
}
