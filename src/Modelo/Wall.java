package Modelo;

public class Wall extends Entity {
  public Wall() {
    super("bricks.png");
    this.isDangerous = false;
    this.isMortal = false;
    this.isTransposable = false;
  }

  @Override
  public void movement() {

  }
}
