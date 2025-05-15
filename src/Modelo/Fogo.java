package Modelo;

import Auxiliar.Desenho;

public class Fogo extends Personagem {

  public Fogo(String sNomeImagePNG) {
    super(sNomeImagePNG);
    this.bMortal = true;
  }

  @Override
  protected void moviment() {
    if (!this.moveRight())
      Desenho.acessoATelaDoJogo().removePersonagem(this);
  }

}
