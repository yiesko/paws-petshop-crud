package xq.yiesko.petshop.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import xq.yiesko.petshop.model.Animal;
import xq.yiesko.petshop.model.Veterinario;
import xq.yiesko.petshop.service.AnimalService;
import xq.yiesko.petshop.service.ConsultaService;
import xq.yiesko.petshop.service.VeterinarioService;

/**
 * Menu de operações relacionadas a consultas.
 */
public class MenuConsulta {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final Scanner scanner;
    private final ConsultaService consultaService;
    private final AnimalService animalService;
    private final VeterinarioService veterinarioService;

    /**
     * Cria o menu com as dependências necessárias.
     *
     * @param scanner            leitor de entradas
     * @param consultaService    serviço para consultas
     * @param animalService      serviço para animais
     * @param veterinarioService serviço para veterinários
     */
    public MenuConsulta(
            Scanner scanner,
            ConsultaService consultaService,
            AnimalService animalService,
            VeterinarioService veterinarioService
    ) {
        this.scanner = scanner;
        this.consultaService = consultaService;
        this.animalService = animalService;
        this.veterinarioService = veterinarioService;
    }

    /**
     * Exibe o menu e processa as operações até o usuário retornar.
     */
    public void exibir() {
        var continuar = true;

        while (continuar) {
            System.out.println("\n--- Menu de Consultas ---");
            System.out.println("1. Agendar");
            System.out.println("2. Listar");
            System.out.println("3. Atualizar");
            System.out.println("4. Cancelar");
            System.out.println("0. Voltar");

            var opcao = lerInteiro("Escolha uma opção: ");
            switch (opcao) {
                case 1 -> agendar();
                case 2 -> listar();
                case 3 -> atualizar();
                case 4 -> cancelar();
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private void agendar() {
        System.out.println("\nAgendamento de Consulta");
        var animais = animalService.listarAnimais();

        if (animais.isEmpty()) {
            System.out.println("Cadastre um animal antes de agendar consultas.");
            return;
        }

        var veterinarios = veterinarioService.listar();
        if (veterinarios.isEmpty()) {
            System.out.println("Cadastre um veterinário antes de agendar consultas.");
            return;
        }

        var animal = selecionarAnimal(animais);
        if (animal == null) return;

        var veterinario = selecionarVeterinario(veterinarios);
        if (veterinario == null) return;

        var data = lerData("Data (dd/MM/yyyy): ");
        System.out.print("Observações (opcional): ");
        var observacoes = scanner.nextLine();

        try {
            var consulta = consultaService.agendar(data, animal, veterinario, observacoes);
            System.out.printf("Consulta agendada com ID %d.%n", consulta.getId());
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void listar() {
        System.out.println("\nLista de Consultas");
        var consultas = consultaService.listar();

        if (consultas.isEmpty()) {
            System.out.println("Nenhuma consulta agendada.");
            return;
        }

        System.out.printf("%-4s %-12s %-20s %-20s %-30s%n",
            "ID", "Data", "Animal", "Veterinário", "Observações");

        for (var consulta : consultas) {
            System.out.printf("%-4d %-12s %-20s %-20s %-30s%n",
                consulta.getId(),
                    consulta.getData() != null ? consulta.getData().format(DATE_FORMAT) : "-",
                consulta.getAnimal() != null ? consulta.getAnimal().getNome() : "-",
                consulta.getVeterinario() != null ? consulta.getVeterinario().getNome() : "-",
                consulta.getObservacoes() != null ? consulta.getObservacoes() : "");
        }
    }

    private void atualizar() {
        System.out.println("\nAtualização de Consulta");
        var id = lerInteiro("ID da consulta: ");
        var consultaOpt = consultaService.buscarPorId(id);

        if (consultaOpt.isEmpty()) {
            System.out.println("Consulta não encontrada.");
            return;
        }

        var consulta = consultaOpt.get();
        var animais = animalService.listarAnimais();

        if (animais.isEmpty()) {
            System.out.println("Cadastre um animal antes de atualizar a consulta.");
            return;
        }

        var veterinarios = veterinarioService.listar();
        if (veterinarios.isEmpty()) {
            System.out.println("Cadastre um veterinário antes de atualizar a consulta.");
            return;
        }

        var animal = selecionarAnimal(animais);
        if (animal == null) return;

        var veterinario = selecionarVeterinario(veterinarios);
        if (veterinario == null) return;

        var dataAtual = consulta.getData() != null ? consulta.getData().format(DATE_FORMAT) : "-";
        var data = lerData("Nova data (dd/MM/yyyy) - atual " + dataAtual + ": ");

        System.out.print("Novas observações (opcional): ");
        var observacoes = scanner.nextLine();
        var atualizado = consultaService.atualizar(id, data, animal, veterinario, observacoes);
        System.out.println(atualizado ? "Consulta atualizada com sucesso." : "Consulta não encontrada.");
    }

    private void cancelar() {
        System.out.println("\nCancelamento de Consulta");
        var id = lerInteiro("ID da consulta: ");
        var cancelado = consultaService.cancelar(id);
        System.out.println(cancelado ? "Consulta cancelada com sucesso." : "Consulta não encontrada.");
    }

    private Animal selecionarAnimal(List<Animal> animais) {
        System.out.println("Animais cadastrados:");
        for (var animal : animais)
            System.out.printf("%d - %s (%s)%n",
                animal.getId(),
                animal.getNome(),
                animal.getProprietario() != null ? animal.getProprietario().getNome() : "sem proprietário");

        var idSelecionado = lerInteiro("ID do animal: ");
        var animalOpt = animalService.buscarPorId(idSelecionado);

        if (animalOpt.isEmpty()) {
            System.out.println("Animal não encontrado.");
            return null;
        }
        return animalOpt.get();
    }

    private Veterinario selecionarVeterinario(List<Veterinario> veterinarios) {
        System.out.println("Veterinários cadastrados:");
        for (var veterinario : veterinarios)
            System.out.printf("%d - %s (%s)%n",
                veterinario.getId(),
                veterinario.getNome(),
                veterinario.getEspecialidade());

        var idSelecionado = lerInteiro("ID do veterinário: ");
        var veterinarioOpt = veterinarioService.buscarPorId(idSelecionado);

        if (veterinarioOpt.isEmpty()) {
            System.out.println("Veterinário não encontrado.");
            return null;
        }
        return veterinarioOpt.get();
    }

    private LocalDate lerData(
            String mensagem
    ) {
        while (true) {
            System.out.print(mensagem);

            var linha = scanner.nextLine();
            try {
                return LocalDate.parse(linha.trim(), DATE_FORMAT);
            } catch (DateTimeParseException e) {
                System.out.println("Data inválida. Utilize o formato dd/MM/yyyy.");
            }
        }
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
}
