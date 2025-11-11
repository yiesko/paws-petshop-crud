package xq.yiesko.petshop.model;

/**
 * Representa um gato.
 */
public class Gato extends Animal {

    /**
     * Cria um gato.
     *
     * @param nome        nome do animal
     * @param idade       idade aproximada
     * @param especie     espécie ou raça
     * @param proprietario proprietário responsável
     */
    public Gato(
            String nome,
            int idade,
            String especie,
            Proprietario proprietario
    ) {
        super(nome, idade, especie, proprietario);
    }

    @Override
    public void emitirSom() {
        System.out.println("Miau!");
    }
}