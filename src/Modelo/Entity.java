package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Auxiliar.Imagem;
import Auxiliar.Posicao;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;

public abstract class Entity implements Serializable {
  protected ImageIcon iImage;
  protected Posicao position;
  protected boolean isTransposable; /* Pode passar por cima? */
  protected boolean isMortal; /* Se encostar, morre? */
  private int contadorMovimento = 0;
  private int movementDelay = 5;

  public boolean isMortal() {
    return isMortal;
  }

  protected abstract void movement();

  public void setMovementDelay(int movementDelay) {
    this.movementDelay = movementDelay;
  }

  protected Entity(String filename) {
    this.position = new Posicao(0, 0);
    this.isTransposable = true;
    this.isMortal = false;

    BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
    Imagem entityImage = new Imagem(filename, bi.createGraphics());
    entityImage.drawImage(0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE);
    this.iImage = new ImageIcon(bi);
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
    Desenho.desenhar(this.iImage, this.position.getColuna(), this.position.getLinha());

    contadorMovimento++;
    if (contadorMovimento >= movementDelay) {
      contadorMovimento = 0;
      movement();
    }
  }

  public boolean setPosicao(int linha, int coluna) {
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
