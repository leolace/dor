package Controler;

import java.util.ArrayList;
import java.util.Random;

import Auxiliar.Posicao;
import Auxiliar.TreeGenerator;
import Modelo.Entity;
import Modelo.Hero;
import Modelo.Key;

public class Level {
  private ArrayList<Entity> personagens = new ArrayList<Entity>();
  private Posicao keyPosition;
  private GameControl gameControl;

  public Level(Hero hero, GameControl gameControl) {
    this.gameControl = gameControl;

    /* Gera a chave */
    this.keyPosition = this.genKeyPosition();
    Key key = createKeyEntity();
    this.addPersonagem(key);
  }

  public Key createKeyEntity() {
    Key key = new Key("key.png");
    key.setPosicao(this.keyPosition.getLinha(), this.keyPosition.getColuna());
    return key;
  }

  public ArrayList<Entity> getPersonagens() {
    return this.personagens;
  }

  public void addPersonagem(Entity personagem) {
    this.personagens.add(personagem);
  }

  public void removePersonagem(Entity personagem) {
    personagens.remove(personagem);
  }

  private Posicao genKeyPosition() {
    Random rand = new Random();
    int linha = rand.nextInt(Auxiliar.Consts.RES_X);
    int coluna = rand.nextInt(Auxiliar.Consts.RES_Y);
    return new Posicao(linha, coluna);
  }

  public boolean isLevelValidPosition(Entity entity) {
    return this.gameControl.isValidPosition(personagens, entity);
  }

  /**
   * Adiciona árvores à fase
   */
  public void addTrees() {
    TreeGenerator treeGenerator = new TreeGenerator();
    treeGenerator.addTrees(this);
  }
}
