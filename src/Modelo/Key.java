package Modelo;

public class Key extends Entity {
  public Key(String filename) {
    super("key.png");
    this.isMortal = true;
    this.isTransposable = true; // Pode passar através de outros objetos
    this.isDangerous = false;
    this.setMovementDelay(0);
  }

  @Override
  protected void movement() {
    return;
  }
}
