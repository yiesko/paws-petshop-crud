package xq.yiesko.petshop.service;

import java.util.List;
import java.util.Optional;
import xq.yiesko.petshop.model.Animal;
import xq.yiesko.petshop.model.Cachorro;
import xq.yiesko.petshop.model.Gato;
import xq.yiesko.petshop.model.Proprietario;
import xq.yiesko.petshop.repository.InMemoryRepository;
import xq.yiesko.petshop.util.ValidationUtils;

/**
 * Regras de negócio para animais.
 */
public class AnimalService {

    private final InMemoryRepository<Animal> repository;

    /**
     * Cria o serviço utilizando o repositório informado.
     *
     * @param repository repositório responsável pelo armazenamento
     */
    public AnimalService(
            InMemoryRepository<Animal> repository
    ) {
        this.repository = repository;
    }

    /**
     * Cadastra um novo cachorro.
     *
     * @param nome        nome do animal
     * @param idade       idade aproximada
     * @param especie     espécie ou raça
     * @param proprietario proprietário responsável
     * @return animal persistido
     */
    public Animal cadastrarCachorro(
            String nome,
            int idade,
            String especie,
            Proprietario proprietario
    ) {
        return cadastrarAnimal(new Cachorro(validarNome(nome), validarIdade(idade), validarEspecie(especie), validarProprietario(proprietario)));
    }

    /**
     * Cadastra um novo gato.
     *
     * @param nome        nome do animal
     * @param idade       idade aproximada
     * @param especie     espécie ou raça
     * @param proprietario proprietário responsável
     * @return animal persistido
     */
    public Animal cadastrarGato(
            String nome,
            int idade,
            String especie,
            Proprietario proprietario
    ) {
        return cadastrarAnimal(new Gato(validarNome(nome), validarIdade(idade), validarEspecie(especie), validarProprietario(proprietario)));
    }

    private Animal cadastrarAnimal(
            Animal animal
    ) {
        return repository.adicionar(animal);
    }

    private String validarNome(
            String nome
    ) {
        ValidationUtils.requireNonBlank(nome, "Nome não pode ser vazio");
        return nome.trim();
    }

    private int validarIdade(
            int idade
    ) {
        ValidationUtils.requireNonNegative(idade, "Idade não pode ser negativa");
        return idade;
    }

    private String validarEspecie(
            String especie
    ) {
        ValidationUtils.requireNonBlank(especie, "Espécie não pode ser vazia");
        return especie.trim();
    }

    private Proprietario validarProprietario(
            Proprietario proprietario
    ) {
        if (proprietario == null) throw new IllegalArgumentException("Proprietário é obrigatório");
        return proprietario;
    }

    /**
     * Atualiza os dados de um animal.
     *
     * @param id          identificador do animal
     * @param nome        novo nome
     * @param idade       nova idade
     * @param especie     nova espécie
     * @param proprietario novo proprietário
     * @return true se houve atualização
     */
    public boolean atualizarAnimal(
            int id,
            String nome,
            int idade,
            String especie,
            Proprietario proprietario
    ) {
        var animalOpt = repository.buscarPorId(id);
        if (animalOpt.isEmpty()) return false;

        var animal = animalOpt.get();
        animal.setNome(validarNome(nome));
        animal.setIdade(validarIdade(idade));
        animal.setEspecie(validarEspecie(especie));
        animal.setProprietario(validarProprietario(proprietario));
        return repository.atualizar(animal);
    }

    /**
     * Remove um animal cadastrado.
     *
     * @param id identificador buscado
     * @return true se o registro foi removido
     */
    public boolean removerAnimal(
            int id
    ) {
        return repository.remover(id);
    }

    /**
     * Lista todos os animais cadastrados.
     *
     * @return lista de animais
     */
    public List<Animal> listarAnimais() {
        return repository.listar();
    }

    /**
     * Busca um animal pelo identificador.
     *
     * @param id identificador buscado
     * @return animal, se encontrado
     */
    public Optional<Animal> buscarPorId(
            int id
    ) {
        return repository.buscarPorId(id);
    }
}