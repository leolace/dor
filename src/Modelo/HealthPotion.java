package Modelo;

/**
 * Representa um item de cura que o herói pode coletar para recuperar vida
 */
public class HealthPotion extends Entity {
    private int healAmount; // Quantidade de vida que o item recupera
    
    /**
     * Construtor da poção de cura
     * @param filename Nome do arquivo de imagem
     */
    public HealthPotion(String filename) {
        super("coracao.png");
        this.isTransposable = true; // Pode ser coletado/atravessado
        this.isMortal = false; // Não causa dano
        this.isDangerous = false; // Não é perigoso
        this.healAmount = 25; // Recupera 25 de vida por padrão
    }
    
    /**
     * Construtor da poção de cura com quantidade personalizada
     * @param filename Nome do arquivo de imagem
     * @param healAmount Quantidade de vida a ser recuperada
     */
    public HealthPotion(String filename, int healAmount) {
        super(filename);
        this.isTransposable = true;
        this.isMortal = false;
        this.healAmount = healAmount;
    }
    
    /**
     * Retorna a quantidade de vida que este item recupera
     * @return quantidade de vida a ser recuperada
     */
    public int getHealAmount() {
        return this.healAmount;
    }
    
    @Override
    protected void movement() {
        // Items de cura não se movem
        return;
    }
}
