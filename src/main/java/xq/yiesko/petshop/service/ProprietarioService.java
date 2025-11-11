package xq.yiesko.petshop.service;

import java.util.List;
import java.util.Optional;
import xq.yiesko.petshop.model.Proprietario;
import xq.yiesko.petshop.repository.InMemoryRepository;
import xq.yiesko.petshop.util.ValidationUtils;

/**
 * Regras de negócio para proprietários.
 */
public class ProprietarioService {

    private final InMemoryRepository<Proprietario> repository;

    /**
     * Cria o serviço utilizando o repositório informado.
     *
     * @param repository repositório responsável pelo armazenamento
     */
    public ProprietarioService(
            InMemoryRepository<Proprietario> repository
    ) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo proprietário.
     *
     * @param nome     nome completo
     * @param telefone telefone para contato
     * @return proprietário persistido
     */
    public Proprietario cadastrar(
            String nome,
            String telefone
    ) {
        ValidationUtils.requireNonBlank(nome, "Nome não pode ser vazio");
        ValidationUtils.requireNonBlank(telefone, "Telefone não pode ser vazio");
        var novo = new Proprietario(nome.trim(), telefone.trim());
        return repository.adicionar(novo);
    }

    /**
     * Atualiza um proprietário existente.
     *
     * @param id       identificador do proprietário
     * @param nome     novo nome
     * @param telefone novo telefone
     * @return true se houve atualização
     */
    public boolean atualizar(
            int id,
            String nome,
            String telefone
    ) {
        ValidationUtils.requireNonBlank(nome, "Nome não pode ser vazio");
        ValidationUtils.requireNonBlank(telefone, "Telefone não pode ser vazio");

        var proprietarioOpt = repository.buscarPorId(id);
        if (proprietarioOpt.isEmpty()) return false;

        var proprietario = proprietarioOpt.get();
        proprietario.setNome(nome.trim());
        proprietario.setTelefone(telefone.trim());
        return repository.atualizar(proprietario);
    }

    /**
     * Remove um proprietário pelo identificador.
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
     * Lista todos os proprietários cadastrados.
     *
     * @return lista de proprietários
     */
    public List<Proprietario> listar() {
        return repository.listar();
    }

    /**
     * Busca um proprietário pelo identificador.
     *
     * @param id identificador buscado
     * @return proprietário, se encontrado
     */
    public Optional<Proprietario> buscarPorId(
            int id
    ) {
        return repository.buscarPorId(id);
    }
}