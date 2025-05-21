package Modelo;

import Auxiliar.Desenho;

public class Fogo extends Personagem {

  public Fogo(String sNomeImagePNG) {
    super(sNomeImagePNG);
    this.bMortal = true;
    this.setMovementDelay(3);
  }

  @Override
  protected void movement() {
    if (!this.moveRight())
      Desenho.acessoATelaDoJogo().removePersonagem(this);
  }

}
