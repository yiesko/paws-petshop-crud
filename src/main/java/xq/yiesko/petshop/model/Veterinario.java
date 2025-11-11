package xq.yiesko.petshop.model;

import xq.yiesko.petshop.model.impl.Identificavel;

import java.util.Objects;

/**
 * Representa um veterinário da clínica.
 */
public class Veterinario implements Identificavel {
    private int id;
    private String nome;
    private String telefone;
    private String especialidade;

    /**
     * Cria um novo veterinário.
     *
     * @param nome          nome completo do veterinário
     * @param telefone      telefone para contato
     * @param especialidade área principal de atuação
     */
    public Veterinario(
            String nome,
            String telefone,
            String especialidade
    ) {
        this.nome = nome;
        this.telefone = telefone;
        this.especialidade = especialidade;
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
     * Obtém o nome do veterinário.
     *
     * @return nome atual
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome do veterinário.
     *
     * @param nome novo nome
     */
    public void setNome(
            String nome
    ) {
        this.nome = nome;
    }

    /**
     * Obtém o telefone do veterinário.
     *
     * @return telefone atual
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Atualiza o telefone do veterinário.
     *
     * @param telefone novo telefone
     */
    public void setTelefone(
            String telefone
    ) {
        this.telefone = telefone;
    }

    /**
     * Obtém a especialidade do veterinário.
     *
     * @return especialidade atual
     */
    public String getEspecialidade() {
        return especialidade;
    }

    /**
     * Atualiza a especialidade do veterinário.
     *
     * @param especialidade nova especialidade
     */
    public void setEspecialidade(
            String especialidade
    ) {
        this.especialidade = especialidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Veterinario veterinario)) return false;
        return id == veterinario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Veterinário{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", telefone='" + telefone + '\'' +
            ", especialidade='" + especialidade + '\'' +
            '}';
    }
}