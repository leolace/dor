package Modelo;

public class Tree extends Entity {
    public Tree(String filename) {
        super(filename);
        this.isTransposable = false; // Não pode passar através da árvore
        this.isMortal = false;
        this.isDangerous = false;
        this.setMovementDelay(0); // Não se move
    }

    @Override
    protected void movement() {
        // Árvores não se movem
        return;
    }
}
