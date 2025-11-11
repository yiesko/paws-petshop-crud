package xq.yiesko.petshop.model;

import xq.yiesko.petshop.model.impl.Identificavel;

/**
 * Representa um animal atendido na clínica veterinária.
 */
public abstract class Animal implements Identificavel {
    private int id;
    private String nome;
    private int idade;
    private String especie;
    private Proprietario proprietario;

    /**
     * Cria um animal.
     *
     * @param nome        nome do animal
     * @param idade       idade aproximada
     * @param especie     descrição da espécie ou raça
     * @param proprietario proprietário responsável
     */
    protected Animal(
            String nome,
            int idade,
            String especie,
            Proprietario proprietario
    ) {
        this.nome = nome;
        this.idade = idade;
        this.especie = especie;
        this.proprietario = proprietario;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(
            int id
    ) {
        this.id = id;
    }

    /**
     * Obtém o nome do animal.
     *
     * @return nome atual
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome do animal.
     *
     * @param nome novo nome
     */
    public void setNome(
            String nome
    ) {
        this.nome = nome;
    }

    /**
     * Obtém a idade do animal.
     *
     * @return idade atual
     */
    public int getIdade() {
        return idade;
    }

    /**
     * Atualiza a idade do animal.
     *
     * @param idade nova idade
     */
    public void setIdade(
            int idade
    ) {
        this.idade = idade;
    }

    /**
     * Obtém a descrição da espécie ou raça do animal.
     *
     * @return espécie atual
     */
    public String getEspecie() {
        return especie;
    }

    /**
     * Atualiza a descrição da espécie ou raça do aninal.
     *
     * @param especie nova espécie
     */
    public void setEspecie(
            String especie
    ) {
        this.especie = especie;
    }

    /**
     * Obtém o proprietário responsável pelo animal.
     *
     * @return proprietário atual
     */
    public Proprietario getProprietario() {
        return proprietario;
    }

    /**
     * Atualiza o proprietário responsável pelo animal.
     *
     * @param proprietario novo proprietário
     */
    public void setProprietario(
            Proprietario proprietario
    ) {
        this.proprietario = proprietario;
    }

    /**
     * Emite o som característico do animal.
     */
    public abstract void emitirSom();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", idade=" + idade +
            ", especie='" + especie + '\'' +
            ", proprietário=" + (proprietario != null ? proprietario.getNome() : "-") +
            '}';
    }
}