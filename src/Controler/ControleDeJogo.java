package Controler;

import Modelo.Chaser;
import Modelo.Personagem;
import Modelo.Hero;
import Auxiliar.Posicao;
import java.util.ArrayList;

public class ControleDeJogo {
  public void desenhaTudo(ArrayList<Personagem> e) {
    for (int i = 1; i < e.size(); i++) {
      e.get(i).autoDesenho();
    }
  }

  public void processaTudo(ArrayList<Personagem> umaFase) {
    Hero hero = (Hero) umaFase.get(0);
    Personagem pIesimoPersonagem;
    for (int i = 1; i < umaFase.size(); i++) {
      pIesimoPersonagem = umaFase.get(i);
      if (hero.isSamePosition(pIesimoPersonagem.getLinha(), pIesimoPersonagem.getColuna())) {
        if (pIesimoPersonagem.isbTransponivel()) /* TO-DO: verificar se o personagem eh mortal antes de retirar */ {
          if (pIesimoPersonagem.isbMortal())
            umaFase.remove(pIesimoPersonagem);
        }
      }

      if (pIesimoPersonagem instanceof Chaser) {
        ((Chaser) pIesimoPersonagem).computeDirection(hero);
      }
    }

  }

  /*
   * Retorna true se a posicao p é válida para Hero com relacao a todos os
   * personagens no array
   */
  public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Personagem personagem) {
    Personagem pIesimoPersonagem;
    for (int i = 1; i < umaFase.size(); i++) {
      pIesimoPersonagem = umaFase.get(i);
      if (!pIesimoPersonagem.isbTransponivel()) {
        if (pIesimoPersonagem.isSamePosition(personagem.getLinha(), personagem.getColuna())) {
          return false;
        }
      }
    }
    return true;
  }
}
