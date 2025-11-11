package xq.yiesko.petshop.cli;

import java.util.List;
import java.util.Scanner;
import xq.yiesko.petshop.model.Proprietario;
import xq.yiesko.petshop.service.AnimalService;
import xq.yiesko.petshop.service.ProprietarioService;

/**
 * Menu de operações relacionadas a animais.
 */
public class MenuAnimal {

    private final Scanner scanner;
    private final AnimalService animalService;
    private final ProprietarioService proprietarioService;

    /**
     * Cria o menu com as dependências necessárias.
     *
     * @param scanner             leitor de entradas
     * @param animalService       serviço para animais
     * @param proprietarioService serviço para proprietários
     */
    public MenuAnimal(
            Scanner scanner,
            AnimalService animalService,
            ProprietarioService proprietarioService
    ) {
        this.scanner = scanner;
        this.animalService = animalService;
        this.proprietarioService = proprietarioService;
    }

    /**
     * Exibe o menu e processa as operações até o usuário retornar.
     */
    public void exibir() {
        var continuar = true;

        while (continuar) {
            System.out.println("\n--- Menu de Animais ---");
            System.out.println("1. Cadastrar");
            System.out.println("2. Listar");
            System.out.println("3. Atualizar");
            System.out.println("4. Remover");
            System.out.println("0. Voltar");

            var opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> atualizar();
                case 4 -> remover();
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void cadastrar() {
        System.out.println("\nCadastro de Animal");
        var proprietarios = proprietarioService.listar();

        if (proprietarios.isEmpty()) {
            System.out.println("Cadastre um proprietário antes de registrar animais.");
            return;
        }

        var tipo = solicitarTipoAnimal();
        var nome = lerTextoObrigatorio("Nome: ");
        var idade = lerInteiro("Idade (anos): ");
        var especie = lerTextoObrigatorio("Espécie/Raça: ");
        var proprietario = selecionarProprietario(proprietarios);

        if (proprietario == null) return;

        try {
            var cadastrado = tipo == 1
                ? animalService.cadastrarCachorro(nome, idade, especie, proprietario)
                : animalService.cadastrarGato(nome, idade, especie, proprietario);

            System.out.printf("Animal cadastrado com ID %d.%n", cadastrado.getId());
            System.out.print("Deseja ouvir o som do animal? (s/n): ");

            var resposta = scanner.nextLine();
            if (resposta != null && resposta.trim().equalsIgnoreCase("s")) cadastrado.emitirSom();
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\nLista de Animais");
        var animais = animalService.listarAnimais();

        if (animais.isEmpty()) {
            System.out.println("Nenhum animal cadastrado.");
            return;
        }

        System.out.printf("%-4s %-12s %-15s %-6s %-18s %-20s%n",
            "ID", "Tipo", "Nome", "Idade", "Espécie", "Proprietário");

        for (var animal : animais) {
            System.out.printf("%-4d %-12s %-15s %-6d %-18s %-20s%n",
                animal.getId(),
                animal.getClass().getSimpleName(),
                animal.getNome(),
                animal.getIdade(),
                animal.getEspecie(),
                animal.getProprietario() != null ? animal.getProprietario().getNome() : "-");
        }
    }

    private void atualizar() {
        System.out.println("\nAtualização de Animal");
        var id = lerInteiro("ID do animal: ");
        var animalOpt = animalService.buscarPorId(id);

        if (animalOpt.isEmpty()) {
            System.out.println("Animal não encontrado.");
            return;
        }

        var animal = animalOpt.get();
        var nome = lerTextoObrigatorio("Novo nome (atual: " + animal.getNome() + "): ");
        var idade = lerInteiro("Nova idade (atual: " + animal.getIdade() + "): ");
        var especie = lerTextoObrigatorio("Nova espécie/raça (atual: " + animal.getEspecie() + "): ");
        var proprietarios = proprietarioService.listar();

        if (proprietarios.isEmpty()) {
            System.out.println("Cadastre um proprietário antes de atualizar.");
            return;
        }

        var proprietario = selecionarProprietario(proprietarios);
        if (proprietario == null) return;

        try {
            var atualizado = animalService.atualizarAnimal(id, nome, idade, especie, proprietario);
            System.out.println(atualizado
                    ? "Animal atualizado com sucesso."
                    : "Animal não encontrado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\nRemoção de Animal");
        var id = lerInteiro("ID do animal: ");
        var removido = animalService.removerAnimal(id);

        System.out.println(removido
                ? "Animal removido com sucesso."
                : "Animal não encontrado.");
    }

    private int solicitarTipoAnimal() {
        while (true) {
            System.out.println("Tipo de animal: 1 - Cachorro, 2 - Gato");
            var tipo = lerInteiro("Escolha: ");
            if (tipo == 1 || tipo == 2) return tipo;
            System.out.println("Opção inválida. Informe 1 ou 2.");
        }
    }

    private Proprietario selecionarProprietario(
            List<Proprietario> proprietarios
    ) {
        System.out.println("Proprietários cadastrados:");
        for (var proprietario : proprietarios)
            System.out.printf("%d - %s (%s)%n",
                proprietario.getId(),
                proprietario.getNome(),
                proprietario.getTelefone());

        var idSelecionado = lerInteiro("Informe o ID do proprietário: ");
        var proprietarioOpt = proprietarioService.buscarPorId(idSelecionado);

        if (proprietarioOpt.isEmpty()) {
            System.out.println("Proprietário não encontrado.");
            return null;
        }

        return proprietarioOpt.get();
    }

    private int lerInteiro(
            String mensagem
    ) {
        while (true) {
            System.out.print(mensagem);
            var linha = scanner.nextLine();

            try {
                return Integer.parseInt(linha.trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }
    }

    private String lerTextoObrigatorio(
            String mensagem
    ) {
        while (true) {
            System.out.print(mensagem);

            var linha = scanner.nextLine();
            if (linha != null && !linha.isBlank()) return linha.trim();

            System.out.println("Campo obrigatório. Tente novamente.");
        }
    }
}