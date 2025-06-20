package Controler;

import Modelo.Entity;
import Modelo.Hero;
import Screens.DeathScreen;
import Screens.WinScreen;
import Auxiliar.Consts;
import Auxiliar.Drawer;
import Auxiliar.EntityLoader;
import Auxiliar.LevelsFactory;
import Auxiliar.SaveGameData;

import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener {
  private Hero hero;
  private Graphics graphics;
  private GameControl gameControl;
  private static SaveGameData saveGameData;

  public Tela() {
    initUiComponents();
    Drawer.setCanvas(this);
    this.addMouseListener(this);
    this.addKeyListener(this);

    /* Cria a janela do tamanho do tabuleiro + insets (bordas) da janela */
    this.setSize(Consts.RES_X * Consts.CELL_SIDE + getInsets().left + getInsets().right,
        Consts.RES_Y * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

    /* Cria o heroi */
    this.hero = new Hero();
    this.hero.setPosicao(10, 10);

    /* Inicia o game controller */
    this.gameControl = new GameControl(this.hero);

    /* Ajusta a camera para a posição do personagem */
    GameControl.updateCameraToHero();

    /* Monta as fases */
    LevelsFactory levelsFactory = new LevelsFactory();
    levelsFactory.assembleLevel(0);
    levelsFactory.assembleLevel(1);
    levelsFactory.assembleLevel(2);
    levelsFactory.assembleLevel(3);
    levelsFactory.assembleLevel(4);

    saveGameData = new SaveGameData(this.hero);

    /* Configura o suporte a drag and drop */
    configureDragAndDrop();
  }

  public Graphics getGraphicsBuffer() {
    return graphics;
  }

  public void paint(Graphics gOld) {
    this.graphics = this.getBufferStrategy().getDrawGraphics().create(getInsets().left, getInsets().top,
        getWidth() - getInsets().right, getHeight() - getInsets().top);
    Drawer.drawGameTerrain();

    if (!this.hero.isAlive()) {
      DeathScreen.draw(graphics);
    } else if (GameControl.isGameWon) {
      WinScreen.draw(graphics);
    } else {
      // Desenho normal do jogo quando o jogador está vivo
      Level currentLevel = GameControl.getCurrentLevel();
      if (!currentLevel.getPersonagens().isEmpty()) {
        this.gameControl.desenhaTudo(currentLevel.getPersonagens());
        this.gameControl.processaTudo(currentLevel.getPersonagens());
        hero.autoDesenho();
      }

      // Desenha a UI
      Drawer.drawHealthBar();
      Drawer.drawLevelIndicator();
      Drawer.drawMissionText();
    }

    // Desenha mensagens do sistema, se houver
    if (saveGameData.showSystemMessage) {
      saveGameData.drawSystemMessage(graphics);
    }

    graphics.dispose();
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
    if (!this.hero.isAlive() && e.getKeyCode() == KeyEvent.VK_SPACE) {
      this.gameControl.restartLevel();
      return;
    }

    // Verifica teclas especiais
    if (e.getKeyCode() == KeyEvent.VK_S) {
      // Salva o jogo
      saveGameData.saveGame(this.hero);
      return;
    } else if (e.getKeyCode() == KeyEvent.VK_L) {
      // Carrega o jogo
      saveGameData.loadGame();
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
    GameControl.updateCameraToHero();
    this.setTitle("-> Cell: " + (hero.getColuna()) + ", "
        + (hero.getLinha()));

    hero.autoDesenho();
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

  private void configureDragAndDrop() {
    new DropTarget(this, new DropTargetAdapter() {
      @Override
      public void drop(DropTargetDropEvent event) {
        try {
          event.acceptDrop(DnDConstants.ACTION_COPY);
          Transferable transferable = event.getTransferable();
          @SuppressWarnings("unchecked")
          List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

          for (File file : files) {
            processDroppedFile(file);
          }

          event.dropComplete(true);

        } catch (UnsupportedFlavorException | IOException e) {
          e.printStackTrace();
          event.dropComplete(false);
        }
      }
    });
  }

  private void processDroppedFile(File file) {
    String fileName = file.getName().toLowerCase();
    if (!fileName.endsWith(".class") && !fileName.endsWith(".java")) {
      System.out.println("Arquivo não suportado: " + fileName);
      return;
    }

    Entity entity = EntityLoader.loadEntityFromFile(file);
    if (entity != null) {
      GameControl.getCurrentLevel().addPersonagem(entity);

      // Mostrar mensagem de sucesso
      System.out.println("Entidade " + fileName + " adicionada ao jogo com sucesso!");

      // Opcional: exibir mensagem na tela do jogo
      if (saveGameData != null) {
        saveGameData
            .showSystemMessage("Entidade " + fileName.replace(".class", "").replace(".java", "") + " adicionada!");
      }
    }
  }
}
