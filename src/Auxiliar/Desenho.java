package Auxiliar;

import java.awt.Graphics;
import java.io.Serializable;
import javax.swing.ImageIcon;

import Controler.GameControl;
import Controler.Tela;

public class Desenho implements Serializable {
  private static Tela tela;

  public static void setTela(Tela telaa) {
    tela = telaa;
  }

  public static Graphics getGraphicsDaTela() {
    return tela.getGraphicsBuffer();
  }

  public static void desenhar(ImageIcon iImage, int iColuna, int iLinha) {
    int telaX = (iColuna - GameControl.getCameraPosition().getColuna()) * Consts.CELL_SIDE;
    int telaY = (iLinha - GameControl.getCameraPosition().getLinha()) * Consts.CELL_SIDE;

    if (telaX >= 0 && telaX < Consts.RES_X * Consts.CELL_SIDE &&
        telaY >= 0 && telaY < Consts.RES_Y * Consts.CELL_SIDE) {
      iImage.paintIcon(tela, getGraphicsDaTela(), telaX, telaY);
    }
  }

}
