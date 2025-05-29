package Modelo;

public class Key extends Entity {
  public Key() {
    super("key.png");
    this.isMortal = true;
    this.isTransposable = true; // Pode passar através de outros objetos
    this.isDangerous = false;
    this.setMovementDelay(0);
  }

  @Override
  public void movement() {

  }
}
