package Modelo;

public class Tree extends Entity {
    public Tree() {
        super("tree.png");
        this.isTransposable = false; // Não pode passar através da árvore
        this.isMortal = false;
        this.isDangerous = false;
        this.setMovementDelay(0); // Não se move
    }

    @Override
    public void movement() {

    }
}
