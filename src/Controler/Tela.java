package Controler;

import Modelo.Entity;
import Modelo.Hero;
import Auxiliar.Consts;
import Auxiliar.DeathScreen;
import Auxiliar.Desenho;
import Auxiliar.EntityLoader;
import Auxiliar.Imagem;
import Auxiliar.LevelsFactory;
import Auxiliar.SaveGameData;
import Auxiliar.UIComponents;
import Auxiliar.WinScreen;
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
  private Graphics g2;
  private GameControl gameControl;
  private static SaveGameData saveGameData;

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
    return g2;
  }

  public void paint(Graphics gOld) {
    Graphics g = this.getBufferStrategy().getDrawGraphics();
    g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
    UIComponents UI = UIComponents.getInstance(g2);

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

    if (!this.hero.isAlive()) {
      DeathScreen.draw(g2);
    } else if (GameControl.isGameWon) {
      WinScreen.draw(g2);
    } else {
      // Desenho normal do jogo quando o jogador está vivo
      Level currentLevel = GameControl.getCurrentLevel();
      if (!currentLevel.getPersonagens().isEmpty()) {
        this.gameControl.desenhaTudo(currentLevel.getPersonagens());
        this.gameControl.processaTudo(currentLevel.getPersonagens());
        hero.autoDesenho();
      }

      // Desenha a UI
      UI.drawHealthBar();
      UI.drawLevelIndicator();
      UI.drawMissionText();
    }

    // Desenha mensagens do sistema, se houver
    if (saveGameData.showSystemMessage) {
      saveGameData.drawSystemMessage(g2);
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
    if (!this.hero.isAlive() && e.getKeyCode() == KeyEvent.VK_SPACE) {
      this.hero.resurrect();
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

  /**
   * Configura o suporte a drag and drop para permitir arrastar arquivos para o jogo
   */
  private void configureDragAndDrop() {
    // Criar um DropTarget que aceita arquivos arrastados
    new DropTarget(this, new DropTargetAdapter() {
      @Override
      public void drop(DropTargetDropEvent event) {
        try {
          // Aceitar o drop
          event.acceptDrop(DnDConstants.ACTION_COPY);
          
          // Obter a lista de arquivos arrastados
          Transferable transferable = event.getTransferable();
          @SuppressWarnings("unchecked")
          List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
          
          // Processar cada arquivo
          for (File file : files) {
            processDroppedFile(file);
          }
          
          // Informar que a operação foi concluída com sucesso
          event.dropComplete(true);
          
        } catch (UnsupportedFlavorException | IOException e) {
          e.printStackTrace();
          event.dropComplete(false);
        }
      }
    });
  }
  
  /**
   * Processa um arquivo arrastado para o jogo
   * 
   * @param file O arquivo arrastado
   */
  private void processDroppedFile(File file) {
    // Verificar se é um arquivo de classe ou fonte Java
    String fileName = file.getName().toLowerCase();
    if (!fileName.endsWith(".class") && !fileName.endsWith(".java")) {
      System.out.println("Arquivo não suportado: " + fileName);
      return;
    }
    
    // Carregar a entidade do arquivo
    Entity entity = EntityLoader.loadEntityFromFile(file);
    if (entity != null) {
      // Adicionar a entidade ao nível atual
      GameControl.getCurrentLevel().addPersonagem(entity);
      
      // Mostrar mensagem de sucesso
      System.out.println("Entidade " + fileName + " adicionada ao jogo com sucesso!");
      
      // Opcional: exibir mensagem na tela do jogo
      if (saveGameData != null) {
        saveGameData.showSystemMessage("Entidade " + fileName.replace(".class", "").replace(".java", "") + " adicionada!");
      }
    }
  }
}
