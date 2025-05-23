package Auxiliar;

import java.util.ArrayList;
import java.util.Random;

import Controler.GameControl;
import Modelo.Entity;
import Modelo.Tree;

public class TreeGenerator {
  private Random rand;
  private ArrayList<Entity> entities;
  private int minDistance;
  private int numberOfTrees = 300; // Número padrão de árvores a serem geradas

  public TreeGenerator() {
    this.rand = new Random();
    this.minDistance = 2; // Espaçamento mínimo padrão entre árvores
  }

  /**
   * Adiciona árvores à fase usando o TreeGenerator
   * @param level o nível ao qual as árvores serão adicionadas
   */
  public void addTrees(Controler.Level level) {
    ArrayList<Tree> trees = this.generateTrees();
    for (Tree tree : trees) {
      level.addPersonagem(tree);
    }
  }

  // Método para definir o número de árvores a serem geradas
  public void setNumberOfTrees(int numberOfTrees) {
    this.numberOfTrees = numberOfTrees;
  }

  public int getNumberOfTrees() {
    return numberOfTrees;
  }

  /**
   * Define o espaçamento mínimo entre árvores
   * 
   * @param minDistance distância mínima entre árvores
   */
  public void setMinDistance(int minDistance) {
    this.minDistance = minDistance;
  }

  /**
   * Gera árvores aleatoriamente no cenário
   * 
   * @param entities      lista de entidades existentes no jogo
   * @param numberOfTrees número de árvores a serem geradas
   * @return lista com as árvores geradas
   */
  public ArrayList<Tree> generateTrees() {
    this.entities = GameControl.getCurrentLevel().getPersonagens();
    ArrayList<Tree> trees = new ArrayList<>();
    int treesCreated = 0;
    int maxAttempts = 1000; // Limite máximo de tentativas para evitar loops infinitos
    int attempts = 0;

    // Tentar criar o número especificado de árvores
    while (treesCreated < numberOfTrees && attempts < maxAttempts) {
      attempts++;

      // Gerar posição aleatória
      int linha = rand.nextInt(Auxiliar.Consts.MUNDO_ALTURA);
      int coluna = rand.nextInt(Auxiliar.Consts.MUNDO_LARGURA);

      // Verificar se a posição não está ocupada e se não há árvores próximas
      if (!isPositionOccupied(linha, coluna) && !hasNearbyTree(linha, coluna)) {
        // Criar a árvore
        Tree tree = createTreeEntity(linha, coluna);
        trees.add(tree);

        // Adicionar temporariamente à lista de entidades para verificação de colisões
        entities.add(tree);
        treesCreated++;
      }
    }

    // Remover as árvores adicionadas à lista de entidades
    // para que apenas a classe Level se encarregue de adicionar as entidades ao
    // jogo
    for (Tree tree : trees) {
      entities.remove(tree);
    }

    return trees;
  }

  /**
   * Cria uma entidade árvore
   * 
   * @param linha  linha onde a árvore será posicionada
   * @param coluna coluna onde a árvore será posicionada
   * @return entidade árvore criada
   */
  private Tree createTreeEntity(int linha, int coluna) {
    Tree tree = new Tree("tree.png");
    tree.setPosicao(linha, coluna);
    return tree;
  }

  /**
   * Verifica se uma posição já está ocupada por alguma entidade
   * 
   * @param linha  linha a ser verificada
   * @param coluna coluna a ser verificada
   * @return true se a posição estiver ocupada, false caso contrário
   */
  private boolean isPositionOccupied(int linha, int coluna) {
    for (Entity entity : entities) {
      if (entity.isSamePosition(linha, coluna)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Verifica se existe uma árvore próxima dentro do espaçamento mínimo
   * 
   * @param linha  linha a ser verificada
   * @param coluna coluna a ser verificada
   * @return true se existir uma árvore próxima, false caso contrário
   */
  private boolean hasNearbyTree(int linha, int coluna) {
    for (Entity entity : entities) {
      if (entity instanceof Tree) {
        int distLinha = Math.abs(entity.getLinha() - linha);
        int distColuna = Math.abs(entity.getColuna() - coluna);

        // Se a distância for menor que o espaçamento mínimo
        if (distLinha <= minDistance && distColuna <= minDistance) {
          return true;
        }
      }
    }
    return false;
  }
}
