package Auxiliar;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Imagem {
  private String filename;
  private Graphics graphics;
  private ImageIcon image;

  public Imagem(String filename, Graphics graphics) {
    this.filename = filename;
    this.graphics = graphics;

    try {
      this.image = new ImageIcon(
          new java.io.File("../").getCanonicalPath() + Consts.PATH + this.filename);
    } catch (Exception e) {
      System.out.println("Erro ao carregar imagem: " + filename);
    }
  }

  public void drawImage(int x, int y, int width, int height) {
    if (this.image != null) {
      this.graphics.drawImage(this.image.getImage(), x, y, width, height, null);
    } else {
      System.out.println("Imagem não carregada: " + this.filename);
    }
  }
}
