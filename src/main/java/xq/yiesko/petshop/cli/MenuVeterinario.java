package xq.yiesko.petshop.cli;

import java.util.List;
import java.util.Scanner;
import xq.yiesko.petshop.model.Veterinario;
import xq.yiesko.petshop.service.VeterinarioService;

/**
 * Menu de operações relacionadas a veterinários.
 */
public class MenuVeterinario {

    private final Scanner scanner;
    private final VeterinarioService veterinarioService;

    /**
     * Cria o menu com as dependências necessárias.
     *
     * @param scanner            leitor de entradas
     * @param veterinarioService serviço para veterinários
     */
    public MenuVeterinario(
            Scanner scanner,
            VeterinarioService veterinarioService
    ) {
        this.scanner = scanner;
        this.veterinarioService = veterinarioService;
    }

    /**
     * Exibe o menu e processa as operações até o usuário retornar.
     */
    public void exibir() {
        var continuar = true;

        while (continuar) {
            System.out.println("\n--- Menu de Veterinários ---");
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
        System.out.println("\nCadastro de Veterinário");
        var nome = lerTextoObrigatorio("Nome: ");
        var telefone = lerTextoObrigatorio("Telefone: ");
        var especialidade = lerTextoObrigatorio("Especialidade: ");

        try {
            var cadastrado = veterinarioService.cadastrar(nome, telefone, especialidade);
            System.out.printf("Veterinário cadastrado com ID %d.%n", cadastrado.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\nLista de Veterinários");
        var veterinarios = veterinarioService.listar();

        if (veterinarios.isEmpty()) {
            System.out.println("Nenhum veterinário cadastrado.");
            return;
        }

        System.out.printf("%-4s %-20s %-15s %-20s%n", "ID", "Nome", "Telefone", "Especialidade");
        for (var veterinario : veterinarios)
            System.out.printf("%-4d %-20s %-15s %-20s%n",
                    veterinario.getId(),
                    veterinario.getNome(),
                    veterinario.getTelefone(),
                    veterinario.getEspecialidade());
    }

    private void atualizar() {
        System.out.println("\nAtualização de Veterinário");
        var id = lerInteiro("ID do veterinário: ");
        var nome = lerTextoObrigatorio("Novo nome: ");
        var telefone = lerTextoObrigatorio("Novo telefone: ");
        var especialidade = lerTextoObrigatorio("Nova especialidade: ");

        try {
            var atualizado = veterinarioService.atualizar(id, nome, telefone, especialidade);
            System.out.println(atualizado
                    ? "Veterinário atualizado com sucesso."
                    : "Veterinário não encontrado.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void remover() {
        System.out.println("\nRemoção de Veterinário");
        var id = lerInteiro("ID do veterinário: ");
        var removido = veterinarioService.remover(id);
        System.out.println(removido ? "Veterinário removido com sucesso." : "Veterinário não encontrado.");
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