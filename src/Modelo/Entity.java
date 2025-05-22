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
  protected Posicao pPosicao;
  protected boolean bTransponivel; /* Pode passar por cima? */
  protected boolean bMortal; /* Se encostar, morre? */
  private int contadorMovimento = 0;
  private int movementDelay = 5;

  public boolean isbMortal() {
    return bMortal;
  }

  protected abstract void movement();

  public void setMovementDelay(int movementDelay) {
    this.movementDelay = movementDelay;
  }

  protected Entity(String filename, Posicao posicao) {
    this.pPosicao = posicao;
    this.bTransponivel = true;
    this.bMortal = false;

    BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
    Imagem entityImage = new Imagem(filename, bi.createGraphics());
    entityImage.drawImage(0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE);
    this.iImage = new ImageIcon(bi);
  }

  public int getLinha() {
    return pPosicao.getLinha();
  }

  public int getColuna() {
    return pPosicao.getColuna();
  }

  public boolean isbTransponivel() {
    return bTransponivel;
  }

  public void setbTransponivel(boolean bTransponivel) {
    this.bTransponivel = bTransponivel;
  }

  public void autoDesenho() {
    Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());

    contadorMovimento++;
    if (contadorMovimento >= movementDelay) {
      contadorMovimento = 0;
      movement();
    }
  }

  public boolean setPosicao(int linha, int coluna) {
    return pPosicao.setPosicao(linha, coluna);
  }

  public boolean isSamePosition(int linha, int coluna) {
    return this.pPosicao.igual(new Posicao(linha, coluna));
  }

  public boolean moveUp() {
    return this.pPosicao.moveUp();
  }

  public boolean moveDown() {
    return this.pPosicao.moveDown();
  }

  public boolean moveRight() {
    return this.pPosicao.moveRight();
  }

  public boolean moveLeft() {
    return this.pPosicao.moveLeft();
  }
}
