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

  public EntityGenerator(Class<T> entityClass, int numberOfEntities, int minDistance) {
    this.numberOfEntities = numberOfEntities;
    this.rand = new Random();
    this.minDistance = minDistance;
    this.EntityClass = entityClass;
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

    // Tentar criar o número especificado de entidades
    while (entitiesCreated < this.numberOfEntities && attempts < maxAttempts) {
      attempts++;

      // Gerar posição aleatória
      int linha = rand.nextInt(Auxiliar.Consts.MUNDO_ALTURA);
      int coluna = rand.nextInt(Auxiliar.Consts.MUNDO_LARGURA);

      if (!isPositionOccupied(linha, coluna) && !hasNearbyEntity(linha, coluna)) {
        // Criar a entidade
        T entity = createEntity(linha, coluna);
        entities.add(entity);

        entitiesCreated++;
      }
    }
    return entities;
  }

  private T createEntity(int linha, int coluna) {
    try {
      T entity = EntityClass.getConstructor().newInstance();
      entity.setPosicao(linha, coluna);
      return entity;
    } catch (Exception e) {
      throw new RuntimeException("Failed to instantiate entity", e);
    }
  }

  private boolean isPositionOccupied(int linha, int coluna) {
    for (T entity : entities) {
      if (entity.isSamePosition(linha, coluna)) {
        return true;
      }
    }
    return false;
  }

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
