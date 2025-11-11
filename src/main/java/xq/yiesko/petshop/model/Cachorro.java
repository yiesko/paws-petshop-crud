package xq.yiesko.petshop.model;

/**
 * Representa um cachorro.
 */
public class Cachorro extends Animal {

    /**
     * Cria um cachorro.
     *
     * @param nome        nome do animal
     * @param idade       idade aproximada
     * @param especie     espécie ou raça
     * @param proprietario proprietário responsável
     */
    public Cachorro(
            String nome,
            int idade,
            String especie,
            Proprietario proprietario
    ) {
        super(nome, idade, especie, proprietario);
    }

    @Override
    public void emitirSom() {
        System.out.println("Au Au!");
    }
}