package xq.yiesko.petshop.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import xq.yiesko.petshop.model.Animal;
import xq.yiesko.petshop.model.Consulta;
import xq.yiesko.petshop.model.Veterinario;
import xq.yiesko.petshop.repository.InMemoryRepository;

/**
 * Regras de negócio para consultas.
 */
public class ConsultaService {

    private final InMemoryRepository<Consulta> repository;

    /**
     * Cria o serviço utilizando o repositório informado.
     *
     * @param repository repositório responsável pelo armazenamento
     */
    public ConsultaService(
            InMemoryRepository<Consulta> repository
    ) {
        this.repository = repository;
    }

    /**
     * Agenda uma consulta.
     *
     * @param data         data da consulta
     * @param animal       animal atendido
     * @param veterinario  veterinário responsável
     * @param observacoes  observações adicionais
     * @return consulta persistida
     */
    public Consulta agendar(
            LocalDate data,
            Animal animal,
            Veterinario veterinario,
            String observacoes
    ) {
        var consulta = new Consulta(validarData(data), validarAnimal(animal), validarVeterinario(veterinario), observacoes);
        return repository.adicionar(consulta);
    }

    private LocalDate validarData(
            LocalDate data
    ) {
        if (data == null) throw new IllegalArgumentException("Data é obrigatória");
        return data;
    }

    private Animal validarAnimal(
            Animal animal
    ) {
        if (animal == null) throw new IllegalArgumentException("Pet é obrigatório");
        return animal;
    }

    private Veterinario validarVeterinario(
            Veterinario veterinario
    ) {
        if (veterinario == null) throw new IllegalArgumentException("Veterinário é obrigatório");
        return veterinario;
    }

    /**
     * Atualiza uma consulta existente.
     *
     * @param id          identificador da consulta
     * @param data        nova data
     * @param animal      novo animal
     * @param veterinario novo veterinário
     * @param observacoes novas observações
     * @return true se houve atualização
     */
    public boolean atualizar(
            int id,
            LocalDate data,
            Animal animal,
            Veterinario veterinario,
            String observacoes
    ) {
        var consultaOpt = repository.buscarPorId(id);
        if (consultaOpt.isEmpty()) return false;

        var consulta = consultaOpt.get();
        consulta.setData(validarData(data));
        consulta.setAnimal(validarAnimal(animal));
        consulta.setVeterinario(validarVeterinario(veterinario));
        consulta.setObservacoes(observacoes);
        return repository.atualizar(consulta);
    }

    /**
     * Cancela uma consulta.
     *
     * @param id identificador buscado
     * @return true se o registro foi removido
     */
    public boolean cancelar(
            int id
    ) {
        return repository.remover(id);
    }

    /**
     * Lista todas as consultas.
     *
     * @return lista de consultas
     */
    public List<Consulta> listar() {
        return repository.listar();
    }

    /**
     * Busca uma consulta pelo identificador.
     *
     * @param id identificador buscado
     * @return consulta, se encontrada
     */
    public Optional<Consulta> buscarPorId(
            int id
    ) {
        return repository.buscarPorId(id);
    }
}