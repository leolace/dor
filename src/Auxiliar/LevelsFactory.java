package Auxiliar;

import Controler.GameControl;
import Controler.Level;
import Modelo.FireThrower;
import Modelo.Chaser;
import Modelo.HorizontalBouncer;
import Modelo.VerticalBouncer;
import Modelo.Wall;
import Modelo.ZigueZague;

public class LevelsFactory {

  public void assembleLevel(int levelIndex) {
    switch (levelIndex) {
      case 0:
        this.assembleLevel0();
        break;
      case 1:
        this.assembleLevel1();
        break;
      case 2:
        this.assembleLevel2();
        break;
      case 3:
        this.assembleLevel3();
        break;
      case 4:
        this.assembleLevel4();
        break;
      default:
        throw new IllegalArgumentException("Erro.");
    }
  }

  private Level assembleLevel0() {
    Level fase = GameControl.getLevel(0);

    // Inimigos ZigueZague
    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>(ZigueZague.class, 5,
        15);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    // Inimigos VerticalBouncer
    EntityGenerator<VerticalBouncer> vBouncerGenerator = new EntityGenerator<VerticalBouncer>(
        VerticalBouncer.class, 5, 12);
    fase.addAllPersonagens(vBouncerGenerator.getEntities());

    // Inimigos Caveira
    EntityGenerator<FireThrower> caveiraGenerator = new EntityGenerator<FireThrower>(FireThrower.class, 7, 12);
    fase.addAllPersonagens(caveiraGenerator.getEntities());

    this.addWallsAroundMap(fase);

    return fase;
  }

  private Level assembleLevel1() {
    Level fase = GameControl.getLevel(1);

    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>(ZigueZague.class, 5,
        15);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    EntityGenerator<VerticalBouncer> vBouncerGenerator = new EntityGenerator<VerticalBouncer>(
        VerticalBouncer.class, 5, 12);
    fase.addAllPersonagens(vBouncerGenerator.getEntities());

    EntityGenerator<FireThrower> caveiraGenerator = new EntityGenerator<FireThrower>(FireThrower.class, 5, 12);
    fase.addAllPersonagens(caveiraGenerator.getEntities());

    EntityGenerator<Chaser> chaserGenerator = new EntityGenerator<Chaser>(Chaser.class, 1,
        5);
    fase.addAllPersonagens(chaserGenerator.getEntities());

    this.addWallsAroundMap(fase);

    return fase;
  }

  private Level assembleLevel2() {
    Level fase = GameControl.getLevel(2);

    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>(ZigueZague.class, 5,
        10);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    EntityGenerator<Chaser> chaserGenerator = new EntityGenerator<Chaser>(Chaser.class, 2,
        5);
    fase.addAllPersonagens(chaserGenerator.getEntities());

    EntityGenerator<FireThrower> caveiraGenerator = new EntityGenerator<FireThrower>(FireThrower.class, 10, 12);
    fase.addAllPersonagens(caveiraGenerator.getEntities());

    this.addWallsAroundMap(fase);

    return fase;
  }

  private Level assembleLevel3() {
    Level fase = GameControl.getLevel(3);

    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>(ZigueZague.class, 5,
        10);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    EntityGenerator<FireThrower> caveiraGenerator = new EntityGenerator<FireThrower>(FireThrower.class, 10, 20);
    fase.addAllPersonagens(caveiraGenerator.getEntities());

    EntityGenerator<VerticalBouncer> vBouncerGenerator = new EntityGenerator<VerticalBouncer>(
        VerticalBouncer.class, 10, 15);
    fase.addAllPersonagens(vBouncerGenerator.getEntities());

    EntityGenerator<HorizontalBouncer> hBouncerGenerator = new EntityGenerator<HorizontalBouncer>(
        HorizontalBouncer.class, 10, 15);
    fase.addAllPersonagens(hBouncerGenerator.getEntities());

    EntityGenerator<Chaser> chaserGenerator = new EntityGenerator<Chaser>(Chaser.class, 3,
        5);
    fase.addAllPersonagens(chaserGenerator.getEntities());

    this.addWallsAroundMap(fase);

    return fase;
  }

  private Level assembleLevel4() {
    Level fase = GameControl.getLevel(4);

    EntityGenerator<ZigueZague> zigueZagueGenerator = new EntityGenerator<ZigueZague>(ZigueZague.class, 15,
        25);
    fase.addAllPersonagens(zigueZagueGenerator.getEntities());

    EntityGenerator<HorizontalBouncer> hBouncerGenerator = new EntityGenerator<HorizontalBouncer>(
        HorizontalBouncer.class, 10, 20);
    fase.addAllPersonagens(hBouncerGenerator.getEntities());

    EntityGenerator<VerticalBouncer> vBouncerGenerator = new EntityGenerator<VerticalBouncer>(
        VerticalBouncer.class, 10, 20);
    fase.addAllPersonagens(vBouncerGenerator.getEntities());

    EntityGenerator<FireThrower> caveiraGenerator = new EntityGenerator<FireThrower>(FireThrower.class, 10, 20);
    fase.addAllPersonagens(caveiraGenerator.getEntities());

    EntityGenerator<Chaser> chaserGenerator = new EntityGenerator<Chaser>(Chaser.class, 5,
        5);
    fase.addAllPersonagens(chaserGenerator.getEntities());

    this.addWallsAroundMap(fase);

    return fase;
  }

  private void addWallsAroundMap(Level level) {
    for (int col = 0; col < Auxiliar.Consts.MUNDO_LARGURA; col++) {
      // Borda superior
      Wall topWall = new Wall();
      topWall.setPosicao(0, col);
      level.addPersonagem(topWall);

      // Borda inferior
      Wall bottomWall = new Wall();
      bottomWall.setPosicao(Auxiliar.Consts.MUNDO_ALTURA - 1, col);
      level.addPersonagem(bottomWall);
    }

    for (int row = 1; row < Auxiliar.Consts.MUNDO_ALTURA - 1; row++) {
      // Borda esquerda
      Wall leftWall = new Wall();
      leftWall.setPosicao(row, 0);
      level.addPersonagem(leftWall);

      // Borda direita
      Wall rightWall = new Wall();
      rightWall.setPosicao(row, Auxiliar.Consts.MUNDO_LARGURA - 1);
      level.addPersonagem(rightWall);
    }
  }
}
