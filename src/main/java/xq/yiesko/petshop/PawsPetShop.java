package xq.yiesko.petshop;

import java.util.Scanner;
import xq.yiesko.petshop.cli.MenuAnimal;
import xq.yiesko.petshop.cli.MenuConsulta;
import xq.yiesko.petshop.cli.MenuProprietario;
import xq.yiesko.petshop.cli.MenuVeterinario;
import xq.yiesko.petshop.model.Animal;
import xq.yiesko.petshop.model.Consulta;
import xq.yiesko.petshop.model.Proprietario;
import xq.yiesko.petshop.model.Veterinario;
import xq.yiesko.petshop.repository.InMemoryRepository;
import xq.yiesko.petshop.service.AnimalService;
import xq.yiesko.petshop.service.ConsultaService;
import xq.yiesko.petshop.service.ProprietarioService;
import xq.yiesko.petshop.service.VeterinarioService;

/**
 * Ponto de entrada do sistema de clínica veterinária "Paws PetShop".
 */
public final class PawsPetShop {

    /**
     * Inicia o nosso amigável "Paws PetShop" no console!
     */
    static void main() {
        try (var scanner = new Scanner(System.in)) {
            InMemoryRepository<Proprietario> proprietarioRepository = new InMemoryRepository<>();
            InMemoryRepository<Veterinario> veterinarioRepository = new InMemoryRepository<>();
            InMemoryRepository<Animal> animalRepository = new InMemoryRepository<>();
            InMemoryRepository<Consulta> consultaRepository = new InMemoryRepository<>();

            var proprietarioService = new ProprietarioService(proprietarioRepository);
            var veterinarioService = new VeterinarioService(veterinarioRepository);
            var animalService = new AnimalService(animalRepository);
            var consultaService = new ConsultaService(consultaRepository);

            var menuProprietario = new MenuProprietario(scanner, proprietarioService);
            var menuVeterinario = new MenuVeterinario(scanner, veterinarioService);
            var menuAnimal = new MenuAnimal(scanner, animalService, proprietarioService);
            var menuConsulta = new MenuConsulta(scanner, consultaService, animalService, veterinarioService);

            var executando = true;

            while (executando) {
                System.out.println("\n--- Paws PetShop ---");
                System.out.println("1. Gerenciar Animais");
                System.out.println("2. Gerenciar Proprietários");
                System.out.println("3. Gerenciar Veterinários");
                System.out.println("4. Gerenciar Consultas");
                System.out.println("0. Sair");

                var opcao = lerInteiro(scanner);

                switch (opcao) {
                    case 1 -> menuAnimal.exibir();
                    case 2 -> menuProprietario.exibir();
                    case 3 -> menuVeterinario.exibir();
                    case 4 -> menuConsulta.exibir();
                    case 0 -> {
                        executando = false;
                        System.out.println("Encerrando o sistema de clínica, até logo!");
                    }
                    default -> System.out.println("Opção inválida.");
                }
            }
        }
    }

    private static int lerInteiro(
            Scanner scanner
    ) {
        while (true) {
            System.out.print("Escolha uma opção: ");
            var linha = scanner.nextLine();
            try {
                return Integer.parseInt(linha.trim());
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Tente novamente.");
            }
        }
    }
}