package Modelo;

import Controler.GameControl;

public class Hero extends Entity {
  private int health;
  private int maxHealth;
  private boolean invincible;
  private int invincibilityFrames;
  private int currentInvincibilityFrames;
  
  public Hero() {
    super("hero.png");
    this.maxHealth = 100;
    this.health = this.maxHealth;
    this.invincible = false;
    this.invincibilityFrames = 10; // Aproximadamente 2 segundos de invencibilidade após tomar dano
    this.currentInvincibilityFrames = 0;
  }

  public void voltaAUltimaPosicao() {
    this.position.volta();
  }

  public boolean setPosicao(int linha, int coluna) {
    this.position.setPosicao(linha, coluna);
    return true;
  }

  private boolean validaPosicao() {
    if (!GameControl.getCurrentLevel().isLevelValidPosition(this)) {
      this.voltaAUltimaPosicao();
      return false;
    }
    return true;
  }

  public boolean moveUp() {
    if (super.moveUp())
      return validaPosicao();
    return false;
  }

  public boolean moveDown() {
    if (super.moveDown())
      return validaPosicao();
    return false;
  }

  public boolean moveRight() {
    if (super.moveRight())
      return validaPosicao();
    return false;
  }

  public boolean moveLeft() {
    if (super.moveLeft())
      return validaPosicao();
    return false;
  }

  @Override
  public void movement() {
    // Atualiza os frames de invencibilidade
    if (this.invincible && this.currentInvincibilityFrames > 0) {
      this.currentInvincibilityFrames--;
      if (this.currentInvincibilityFrames <= 0) {
        this.invincible = false;
      }
    }
  }
  
  /**
   * Causar dano ao herói
   * @param damageAmount Quantidade de dano a ser aplicada
   * @return true se o herói sofreu dano, false se estava invencível
   */
  public boolean takeDamage(int damageAmount) {
    if (this.invincible) {
      return false; // Não sofre dano durante a invencibilidade
    }
    
    this.health -= damageAmount;
    if (this.health <= 0) {
      this.health = 0;
    }
    
    // Ativa invencibilidade temporária
    this.invincible = true;
    this.currentInvincibilityFrames = this.invincibilityFrames;
    
    return true;
  }
  
  /**
   * Recupera a vida do herói
   * @param healAmount Quantidade de vida a ser recuperada
   */
  public void heal(int healAmount) {
    this.health = Math.min(this.health + healAmount, this.maxHealth);
  }
  
  /**
   * Verifica se o herói está vivo
   * @return true se o herói tem vida maior que zero, false caso contrário
   */
  public boolean isAlive() {
    return this.health > 0;
  }
  
  /**
   * Obtém a vida atual do herói
   * @return quantidade atual de vida
   */
  public int getHealth() {
    return this.health;
  }
  
  /**
   * Obtém a vida máxima do herói
   * @return quantidade máxima de vida
   */
  public int getMaxHealth() {
    return this.maxHealth;
  }
  
  /**
   * Verifica se o herói está invencível
   * @return true se o herói está invencível, false caso contrário
   */
  public boolean isInvincible() {
    return this.invincible;
  }
  
  /**
   * Ressuscita o herói e restaura sua vida para o máximo
   */
  public void resurrect() {
    this.health = this.maxHealth;
  }
  
  /**
   * Define a quantidade de saúde do herói
   * @param health Nova quantidade de saúde
   */
  public void setHealth(int health) {
    this.health = Math.min(health, this.maxHealth);
  }
}
