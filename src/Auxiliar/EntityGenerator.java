package Auxiliar;

import java.util.ArrayList;
import java.util.Random;

import Modelo.Entity;

public class EntityGenerator<T extends Entity> {
  private Random rand;
  private int numberOfEntities;
  private int minDistance;
  private ArrayList<T> entities = new ArrayList<T>();
  private Class<T> EntityClass;
  private String filename;

  public EntityGenerator(String filename, Class<T> entityClass, int numberOfEntities, int minDistance) {
    this.numberOfEntities = numberOfEntities;
    this.rand = new Random();
    this.minDistance = minDistance;
    this.EntityClass = entityClass;
    this.filename = filename;

    this.entities = this.generateEntities();
  }

  public ArrayList<T> getEntities() {
    return this.entities;
  }

  private ArrayList<T> generateEntities() {
    ArrayList<T> entities = new ArrayList<T>();
    int entitiesCreated = 0;
    int maxAttempts = 1000; // Limite máximo de tentativas para evitar loops infinitos
    int attempts = 0;

    // Tentar criar o número especificado de árvores
    while (entitiesCreated < this.numberOfEntities && attempts < maxAttempts) {
      attempts++;

      // Gerar posição aleatória
      int linha = rand.nextInt(Auxiliar.Consts.MUNDO_ALTURA);
      int coluna = rand.nextInt(Auxiliar.Consts.MUNDO_LARGURA);

      // Verificar se a posição não está ocupada e se não há árvores próximas
      if (!isPositionOccupied(linha, coluna) && !hasNearbyEntity(linha, coluna)) {
        // Criar a árvore
        T entity = createEntity(linha, coluna);
        entities.add(entity);

        // Adicionar temporariamente à lista de entidades para verificação de colisões
        entitiesCreated++;
      }
    }

    // Remover as árvores adicionadas à lista de entidades
    // para que apenas a classe Level se encarregue de adicionar as entidades ao
    // jogo
    // for (Entity entity : entities) {
    // levelPersonagens.remove(entity);
    // }

    return entities;
  }

  private T createEntity(int linha, int coluna) {
    try {
      T entity = EntityClass.getConstructor(String.class).newInstance(this.filename);
      entity.setPosicao(linha, coluna);
      return entity;
    } catch (Exception e) {
      throw new RuntimeException("Failed to instantiate entity", e);
    }
  }

  /**
   * Verifica se uma posição já está ocupada por alguma entidade
   * 
   * @param linha  linha a ser verificada
   * @param coluna coluna a ser verificada
   * @return true se a posição estiver ocupada, false caso contrário
   */
  private boolean isPositionOccupied(int linha, int coluna) {
    for (T entity : entities) {
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
  private boolean hasNearbyEntity(int linha, int coluna) {
    for (T entity : entities) {
      if (entity instanceof T) {
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
