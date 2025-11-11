package xq.yiesko.petshop.cli;

import java.util.Scanner;

import xq.yiesko.petshop.service.ProprietarioService;

/**
 * Menu de operações relacionadas a proprietários.
 */
public class MenuProprietario {

    private final Scanner scanner;
    private final ProprietarioService proprietarioService;

    /**
     * Cria o menu com as dependências necessárias.
     *
     * @param scanner             leitor de entradas
     * @param proprietarioService serviço para proprietários
     */
    public MenuProprietario(
            Scanner scanner,
            ProprietarioService proprietarioService
    ) {
        this.scanner = scanner;
        this.proprietarioService = proprietarioService;
    }

    /**
     * Exibe o menu e processa as operações até o usuário retornar.
     */
    public void exibir() {
        var continuar = true;

        while (continuar) {
            System.out.println("\n--- Menu de Proprietários ---");
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
        System.out.println("\nCadastro de Proprietário");
        var nome = lerTextoObrigatorio("Nome: ");
        var telefone = lerTextoObrigatorio("Telefone: ");

        try {
            var cadastrado = proprietarioService.cadastrar(nome, telefone);
            System.out.printf("Proprietário cadastrado com ID %d.%n", cadastrado.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\nLista de Proprietários");
        var proprietarios = proprietarioService.listar();

        if (proprietarios.isEmpty()) {
            System.out.println("Nenhum proprietário cadastrado.");
            return;
        }

        System.out.printf("%-4s %-20s %-15s%n", "ID", "Nome", "Telefone");
        for (var proprietario : proprietarios)
            System.out.printf("%-4d %-20s %-15s%n",
                    proprietario.getId(),
                    proprietario.getNome(),
                    proprietario.getTelefone());
    }

    private void atualizar() {
        System.out.println("\nAtualização de Proprietário");
        var id = lerInteiro("ID do proprietário: ");
        var nome = lerTextoObrigatorio("Novo nome: ");
        var telefone = lerTextoObrigatorio("Novo telefone: ");

        try {
            var atualizado = proprietarioService.atualizar(id, nome, telefone);
            System.out.println(atualizado
                    ? "Proprietário atualizado com sucesso."
                    : "Proprietário não encontrado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\nRemoção de Proprietário");
        var id = lerInteiro("ID do proprietário: ");
        var removido = proprietarioService.remover(id);
        System.out.println(removido
                ? "Proprietário removido com sucesso."
                : "Proprietário não encontrado.");
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