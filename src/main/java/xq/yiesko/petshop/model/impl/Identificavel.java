package xq.yiesko.petshop.model.impl;

/**
 * Representa entidades que podem ser identificadas por um identificador numérico.
 */
public interface Identificavel {

    /**
     * Obtém o identificador único da entidade.
     *
     * @return identificador atual
     */
    int getId();

    /**
     * Define o identificador único da entidade.
     *
     * @param id novo identificador
     */
    void setId(int id);
}