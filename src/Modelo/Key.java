package Modelo;

public class Key extends Entity {
  public Key(String filename) {
    super(filename);
    this.isMortal = false;
    this.setMovementDelay(0);
  }

  @Override
  protected void movement() {
    return;
  }
}
