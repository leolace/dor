package Modelo;

public class BichinhoVaiVemHorizontal extends Personagem {
  private boolean bRight;
  int iContador;

  public BichinhoVaiVemHorizontal(String sNomeImagePNG) {
    super(sNomeImagePNG);
    bRight = true;
    iContador = 0;
  }

  @Override
  protected void moviment() {
    if (iContador == 5) {
      iContador = 0;
      if (bRight) {
        this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() + 1);
      } else {
        this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() - 1);
      }

      bRight = !bRight;
    }
    iContador++;
  }
}
