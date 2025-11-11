package xq.yiesko.petshop.repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import xq.yiesko.petshop.model.impl.Identificavel;

/**
 * Implementação de repositório em memória com identificação incremental.
 *
 * @param <T> tipo da entidade persistida
 */
public class InMemoryRepository<T extends Identificavel> {

    private final Map<Integer, T> storage = new LinkedHashMap<>();
    private final AtomicInteger sequence = new AtomicInteger(1);

    /**
     * Persiste uma nova entidade gerando um identificador automaticamente.
     *
     * @param entity entidade a ser salva
     * @return entidade com identificador atribuído
     */
    public T adicionar(
            T entity
    ) {
        var id = sequence.getAndIncrement();
        entity.setId(id);
        storage.put(id, entity);
        return entity;
    }

    /**
     * Substitui uma entidade existente.
     *
     * @param entity entidade atualizada
     * @return true se a entidade existia e foi substituída
     */
    public boolean atualizar(
            T entity
    ) {
        var id = entity.getId();
        if (!storage.containsKey(id)) return false;
        storage.put(id, entity);
        return true;
    }

    /**
     * Obtém uma entidade pelo identificador.
     *
     * @param id identificador procurado
     * @return entidade, se encontrada
     */
    public Optional<T> buscarPorId(
            int id
    ) {
        return Optional.ofNullable(storage.get(id));
    }

    /**
     * Lista todas as entidades preservando a ordem de inserção.
     *
     * @return lista das entidades salvas
     */
    public List<T> listar() {
        return new ArrayList<>(storage.values());
    }

    /**
     * Remove a entidade com o identificador informado.
     *
     * @param id identificador da entidade a remover
     * @return true se uma entidade foi removida
     */
    public boolean remover(
            int id
    ) {
        return storage.remove(id) != null;
    }

    /**
     * Verifica se há registros armazenados.
     *
     * @return quantidade de entidades salvas
     */
    public int tamanho() {
        return storage.size();
    }
}