package Controler;

import java.util.ArrayList;
import java.util.Random;

import Auxiliar.Posicao;
import Auxiliar.TreeGenerator;
import Modelo.Entity;
import Modelo.HealthPotion;
import Modelo.Hero;
import Modelo.Key;

public class Level {
  private ArrayList<Entity> personagens = new ArrayList<Entity>();
  private Key keyEntity;
  private Posicao keyPosition;
  private GameControl gameControl;
  private static final int HEALTH_POTIONS_COUNT = 5;

  public Level(Hero hero, GameControl gameControl) {
    this.gameControl = gameControl;
    
    /* Gera a chave */
    this.keyEntity = createKeyEntity();
    this.addPersonagem(this.keyEntity);
    
    // Adiciona poções de cura aleatoriamente
    addHealthPotions(Level.HEALTH_POTIONS_COUNT); // Adiciona 3 poções de cura por nível
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
    addHealthPotions(Level.HEALTH_POTIONS_COUNT); // Adiciona 3 poções de cura por nível
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

  /**
   * Adiciona árvores à fase
   */
  public void addTrees() {
    TreeGenerator treeGenerator = new TreeGenerator();
    treeGenerator.addTrees(this);
  }
  
  /**
   * Adiciona poções de cura em posições aleatórias do nível
   * @param count Número de poções a serem adicionadas
   */
  private void addHealthPotions(int count) {
    Random rand = new Random();
    int potionsAdded = 0;
    int maxAttempts = 100;
    int attempts = 0;
    
    while (potionsAdded < count && attempts < maxAttempts) {
      attempts++;
      
      // Gera uma posição aleatória
      int linha = rand.nextInt(Auxiliar.Consts.MUNDO_ALTURA);
      int coluna = rand.nextInt(Auxiliar.Consts.MUNDO_LARGURA);
      
      // Verifica se a posição não está ocupada
      boolean positionOccupied = false;
      for (Entity entity : personagens) {
        if (entity.isSamePosition(linha, coluna)) {
          positionOccupied = true;
          break;
        }
      }
      
      // Se a posição estiver livre, adiciona a poção
      if (!positionOccupied) {
        HealthPotion potion = new HealthPotion("coracao.png");
        potion.setPosicao(linha, coluna);
        this.addPersonagem(potion);
        potionsAdded++;
      }
    }
    
    System.out.println("Poções de cura adicionadas: " + potionsAdded);
  }
}
