package xq.yiesko.petshop.model;

import xq.yiesko.petshop.model.impl.Identificavel;

import java.util.Objects;

/**
 * Representa um proprietário de animal da clínica veterinária.
 */
public class Proprietario implements Identificavel {
    private int id;
    private String nome;
    private String telefone;

    /**
     * Cria um novo proprietário.
     *
     * @param nome     nome completo do proprietário
     * @param telefone telefone para contato
     */
    public Proprietario(
            String nome,
            String telefone
    ) {
        this.nome = nome;
        this.telefone = telefone;
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
     * Obtém o nome do proprietário.
     *
     * @return nome atual
     */
    public String getNome() {
        return nome;
    }

    /**
     * Atualiza o nome do proprietário.
     *
     * @param nome novo nome
     */
    public void setNome(
            String nome
    ) {
        this.nome = nome;
    }

    /**
     * Obtém o telefone do proprietário.
     *
     * @return telefone atual
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Atualiza o telefone do proprietário.
     *
     * @param telefone novo telefone
     */
    public void setTelefone(
            String telefone
    ) {
        this.telefone = telefone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proprietario proprietario)) return false;
        return id == proprietario.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Proprietário{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", telefone='" + telefone + '\'' +
            '}';
    }
}