package Modelo;

public class BichinhoVaiVemVertical extends Personagem {
  boolean bUp;

  public BichinhoVaiVemVertical(String sNomeImagePNG) {
    super(sNomeImagePNG);
    bUp = true;
  }

  @Override
  protected void moviment() {
    if (bUp)
      this.setPosicao(pPosicao.getLinha() - 1, pPosicao.getColuna());
    else
      this.setPosicao(pPosicao.getLinha() + 1, pPosicao.getColuna());

    bUp = !bUp;
  }
}
