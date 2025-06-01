package Modelo;

public class HealthPotion extends Entity {
    private int healAmount; // Quantidade de vida que o item recupera
    
    public HealthPotion() {
        super("coracao.png");
        this.isTransposable = true; // Pode ser coletado/atravessado
        this.isMortal = false; // Não causa dano
        this.isDangerous = false; // Não é perigoso
        this.healAmount = 25; // Recupera 25 de vida por padrão
    }
    
    public HealthPotion(String filename, int healAmount) {
        super(filename);
        this.isTransposable = true;
        this.isMortal = false;
        this.healAmount = healAmount;
    }
    
    public int getHealAmount() {
        return this.healAmount;
    }
    
    @Override
    public void movement() {
        return;
    }
}
