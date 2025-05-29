package Modelo;

import Auxiliar.Consts;
import Auxiliar.Drawer;
import Auxiliar.Imagem;
import Auxiliar.Posicao;
import Controler.GameControl;
import Interfaces.Moveable;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Random;

import javax.swing.ImageIcon;

public abstract class Entity implements Serializable, Moveable {
  protected ImageIcon iImage;
  protected Posicao position;
  private Posicao initialPosition = null; /* Posição inicial do personagem */
  protected boolean isTransposable; /* Pode passar por cima? */
  protected boolean isMortal; /* Se encostar, morre? */
  protected boolean isDangerous; /* Pode se mover? */
  private int contadorMovimento = 0;
  private int movementDelay = 5;

  public boolean isMortal() {
    return isMortal;
  }

  public boolean isDangerous() {
    return isDangerous;
  }

  public void setMovementDelay(int movementDelay) {
    this.movementDelay = movementDelay;
  }

  protected Entity(String filename) {
    this.position = new Posicao(0, 0);
    this.isTransposable = true;
    this.isMortal = false;
    this.isDangerous = true;

    BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
    Imagem entityImage = new Imagem(filename, bi.createGraphics());
    entityImage.drawImage(0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE);
    this.iImage = new ImageIcon(bi);
  }

  public void setPositionToInitial() {
    this.position.setPosicao(initialPosition.getLinha(), initialPosition.getColuna());
  }

  public void setRandomPosition() {
    int attempts = 0;
    int maxAttempts = 1000;
    Random rand = new Random();

    while (attempts < maxAttempts) {
      attempts++;

      // Gerar posição aleatória
      int linha = rand.nextInt(Auxiliar.Consts.MUNDO_ALTURA);
      int coluna = rand.nextInt(Auxiliar.Consts.MUNDO_LARGURA);

      if (!isPositionOccupied(linha, coluna)) {
        this.setPosicao(linha, coluna);
        break;
      }
    }
  }

  /**
   * Verifica se uma posição já está ocupada por alguma entidade
   * 
   * @param linha  linha a ser verificada
   * @param coluna coluna a ser verificada
   * @return true se a posição estiver ocupada, false caso contrário
   */
  private boolean isPositionOccupied(int linha, int coluna) {
    for (Entity entity : GameControl.getCurrentLevel().getPersonagens()) {
      if (entity.isSamePosition(linha, coluna)) {
        return true;
      }
    }
    return false;
  }

  public int getLinha() {
    return position.getLinha();
  }

  public int getColuna() {
    return position.getColuna();
  }

  public boolean isTransposable() {
    return isTransposable;
  }

  public void setTransposable(boolean bTransponivel) {
    this.isTransposable = bTransponivel;
  }

  public void autoDesenho() {
    Drawer.drawImage(this.iImage, this.position.getColuna(), this.position.getLinha());

    contadorMovimento++;
    if (contadorMovimento >= movementDelay) {
      contadorMovimento = 0;
      movement();
    }
  }

  public boolean setPosicao(int linha, int coluna) {
    if (this.initialPosition == null) {
      this.initialPosition = new Posicao(linha, coluna);
    }
    return position.setPosicao(linha, coluna);
  }

  public boolean isSamePosition(int linha, int coluna) {
    return this.position.igual(new Posicao(linha, coluna));
  }

  public boolean moveUp() {
    return this.position.moveUp();
  }

  public boolean moveDown() {
    return this.position.moveDown();
  }

  public boolean moveRight() {
    return this.position.moveRight();
  }

  public boolean moveLeft() {
    return this.position.moveLeft();
  }
}
