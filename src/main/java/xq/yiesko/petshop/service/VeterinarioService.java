package xq.yiesko.petshop.service;

import java.util.List;
import java.util.Optional;
import xq.yiesko.petshop.model.Veterinario;
import xq.yiesko.petshop.repository.InMemoryRepository;
import xq.yiesko.petshop.util.ValidationUtils;

/**
 * Regras de negócio para veterinários.
 */
public class VeterinarioService {

    private final InMemoryRepository<Veterinario> repository;

    /**
     * Cria o serviço utilizando o repositório informado.
     *
     * @param repository repositório responsável pelo armazenamento
     */
    public VeterinarioService(
            InMemoryRepository<Veterinario> repository
    ) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo veterinário.
     *
     * @param nome          nome completo
     * @param telefone      telefone para contato
     * @param especialidade especialidade principal
     * @return veterinário persistido
     */
    public Veterinario cadastrar(
            String nome,
            String telefone,
            String especialidade
    ) {
        ValidationUtils.requireNonBlank(nome, "Nome não pode ser vazio");
        ValidationUtils.requireNonBlank(telefone, "Telefone não pode ser vazio");
        ValidationUtils.requireNonBlank(especialidade, "Especialidade não pode ser vazia");
        var novo = new Veterinario(nome.trim(), telefone.trim(), especialidade.trim());
        return repository.adicionar(novo);
    }

    /**
     * Atualiza um veterinário existente.
     *
     * @param id            identificador
     * @param nome          novo nome
     * @param telefone      novo telefone
     * @param especialidade nova especialidade
     * @return true se houve atualização
     */
    public boolean atualizar(
            int id,
            String nome,
            String telefone,
            String especialidade
    ) {
        ValidationUtils.requireNonBlank(nome, "Nome não pode ser vazio");
        ValidationUtils.requireNonBlank(telefone, "Telefone não pode ser vazio");
        ValidationUtils.requireNonBlank(especialidade, "Especialidade não pode ser vazia");

        var veterinarioOpt = repository.buscarPorId(id);
        if (veterinarioOpt.isEmpty()) return false;

        var veterinario = veterinarioOpt.get();
        veterinario.setNome(nome.trim());
        veterinario.setTelefone(telefone.trim());
        veterinario.setEspecialidade(especialidade.trim());
        return repository.atualizar(veterinario);
    }

    /**
     * Remove um veterinário.
     *
     * @param id identificador buscado
     * @return true se o registro foi removido
     */
    public boolean remover(
            int id
    ) {
        return repository.remover(id);
    }

    /**
     * Lista todos os veterinários cadastrados.
     *
     * @return lista de veterinários
     */
    public List<Veterinario> listar() {
        return repository.listar();
    }

    /**
     * Busca um veterinário pelo identificador.
     *
     * @param id identificador buscado
     * @return veterinário, se encontrado
     */
    public Optional<Veterinario> buscarPorId(
            int id
    ) {
        return repository.buscarPorId(id);
    }
}