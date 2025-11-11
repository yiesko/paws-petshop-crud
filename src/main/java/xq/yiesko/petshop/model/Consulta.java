package xq.yiesko.petshop.model;

import xq.yiesko.petshop.model.impl.Identificavel;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Representa uma consulta agendada na clínica.
 */
public class Consulta implements Identificavel {
    private int id;
    private LocalDate data;
    private Animal animal;
    private Veterinario veterinario;
    private String observacoes;

    /**
     * Cria uma nova consulta.
     *
     * @param data         data da consulta
     * @param animal       animal atendido
     * @param veterinario  veterinário responsável
     * @param observacoes  observações adicionais
     */
    public Consulta(
            LocalDate data,
            Animal animal,
            Veterinario veterinario,
            String observacoes
    ) {
        this.data = data;
        this.animal = animal;
        this.veterinario = veterinario;
        this.observacoes = observacoes;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(
            LocalDate data
    ) {
        this.data = data;
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(
            Animal animal
    ) {
        this.animal = animal;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(
            Veterinario veterinario
    ) {
        this.veterinario = veterinario;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(
            String observacoes
    ) {
        this.observacoes = observacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Consulta consulta)) return false;
        return id == consulta.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Consulta{" +
            "id=" + id +
            ", data=" + data +
            ", animal=" + (animal != null ? animal.getNome() : "-") +
            ", veterinário=" + (veterinario != null ? veterinario.getNome() : "-") +
            ", observações='" + observacoes + '\'' +
            '}';
    }
}