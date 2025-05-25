package Controler;

import java.util.ArrayList;
import java.util.Random;

import Auxiliar.EntityGenerator;
import Auxiliar.Posicao;
import Modelo.Entity;
import Modelo.HealthPotion;
import Modelo.Hero;
import Modelo.Key;
import Modelo.Tree;

public class Level {
  private ArrayList<Entity> personagens = new ArrayList<Entity>();
  private Key keyEntity;
  private Posicao keyPosition;
  private GameControl gameControl;
  private static final int NUMBER_OF_HEALTH_POTIONS = 5;
  private static final int NUMBER_OF_TREES = 500;

  public Level(Hero hero, GameControl gameControl) {
    this.gameControl = gameControl;

    /* Gera a chave */
    this.keyEntity = createKeyEntity();
    this.addPersonagem(this.keyEntity);

    this.personagens.addAll(this.generateTrees());
    this.personagens.addAll(this.generateHealthPotions());
  }

  public Key createKeyEntity() {
    this.keyPosition = this.genKeyPosition();
    Key key = new Key("key.png");
    key.setPosicao(this.keyPosition.getLinha(), this.keyPosition.getColuna());
    return key;
  }

  public void restartHealthPotions() {
    for (int i = personagens.size() - 1; i >= 0; i--) {
      if (personagens.get(i) instanceof HealthPotion) {
        personagens.remove(i);
      }
    }
    this.personagens.addAll(this.generateHealthPotions());
  }

  public void restartLevel() {
    for (int i = personagens.size() - 1; i >= 0; i--) {
      personagens.get(i).setPositionToInitial();
    }

    this.restartKey();
    this.restartHealthPotions();
  }

  public void restartKey() {
    this.personagens.remove(this.keyEntity);
    this.keyEntity = createKeyEntity();
    this.addPersonagem(this.keyEntity);
  }

  public ArrayList<Entity> getPersonagens() {
    return this.personagens;
  }

  public void addPersonagem(Entity personagem) {
    this.personagens.add(personagem);
  }

  public <T extends Entity>void addAllPersonagens(ArrayList<T> personagens) {
    this.personagens.addAll(personagens);
  }

  public void removePersonagem(Entity personagem) {
    personagens.remove(personagem);
  }

  private Posicao genKeyPosition() {
    Random rand = new Random();
    int linha = rand.nextInt(Auxiliar.Consts.MUNDO_ALTURA);
    int coluna = rand.nextInt(Auxiliar.Consts.MUNDO_LARGURA);
    return new Posicao(linha, coluna);
  }

  public boolean isLevelValidPosition(Entity entity) {
    return this.gameControl.isValidPosition(personagens, entity);
  }

  private ArrayList<Tree> generateTrees() {
    EntityGenerator<Tree> treeGenerator = new EntityGenerator<Tree>("tree.png", Tree.class, NUMBER_OF_TREES, 2);
    ArrayList<Tree> trees = treeGenerator.getEntities();
    return trees;
  }

  private ArrayList<HealthPotion> generateHealthPotions() {
    EntityGenerator<HealthPotion> healthPotionGenerator = new EntityGenerator<HealthPotion>("coracao.png",
        HealthPotion.class, NUMBER_OF_HEALTH_POTIONS, 2);
    ArrayList<HealthPotion> healthPotions = healthPotionGenerator.getEntities();
    return healthPotions;
  }
}
