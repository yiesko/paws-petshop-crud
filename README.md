# Paws PetShop: Sistema de Gestão Veterinária

Sistema CRUD desenvolvido em Java 25 para gerenciamento completo de uma clínica veterinária via interface console. Desenvolvido como trabalho acadêmico para demonstrar domínio prático dos **4 pilares da Programação Orientada a Objetos**.

## Observação

Ainda não foi totalmente concluído, falta implementar algumas funcionalidades não-funcionais que ficaram soltas no código-fonte, mas completamente utilizável e funcional.

### Pilares da POO

- Encapsulamento: Atributos privados com controle de acesso via métodos
- Herança: Hierarquia `Animal → Cachorro/Gato` com especialização
- Polimorfismo: Método `emitirSom()` com comportamentos distintos
- Abstração: Classe `Animal` abstrata e interface `Identificavel`

## Estrutura do código-fonte

```bash
[yiesko@ysk ~]$ tree src
src/main/java/xq/yiesko/petshop/
├── PawsPetShop.java                 # Coordenação geral e menu principal
├── cli/                             # Interface com usuário
│   ├── MenuAnimal.java              # Gestão de pets
│   ├── MenuConsulta.java            # Agendamento de consultas
│   ├── MenuProprietario.java        # Cadastro de proprietários
│   └── MenuVeterinario.java         # Gestão de veterinários
├── model/                           # Entidades de domínio
│   ├── Animal.java                  # Classe abstrata base
│   ├── Cachorro.java, Gato.java     # Especializações com polimorfismo
│   ├── Proprietario.java            # Modelo de dono dos pets
│   ├── Veterinario.java             # Profissionais com especialidade
│   ├── Consulta.java                # Agendamentos
│   └── impl/Identificavel.java      # Contrato para entidades
├── repository/                      # Persistência em memória
│   └── InMemoryRepository.java      # Repositório genérico
├── service/                         # Lógica de negócio
│   ├── AnimalService.java           # Regras para pets
│   ├── ConsultaService.java         # Validações de agendamento
│   ├── ProprietarioService.java     # Gestão de proprietários
│   └── VeterinarioService.java      # Controle de veterinários
└── util/
    └── ValidationUtils.java         # Validações auxiliares
```

## Funcionalidades

### Gestão de animais
- Cadastro de cães e gatos com dados específicos
- Listagem, atualização e remoção
- Polimorfismo: Sons diferentes para cada espécie

### Gestão de pessoas
- Proprietários: CRUD completo com dados de contato
- Veterinários: Cadastro com especialidades

### Sistema de consultas
- Agendamento vinculando animal e veterinário
- Controle completo de horários
- Validações de integridade

### Validações funcionais
- Campos obrigatórios
- Idades não-negativas
- Seleção guiada de relacionamentos
- Prevenção de inconsistências

## Propósito Acadêmico

Este projeto foi desenvolvido como **trabalho prático de POO** para demonstrar:
- Aplicação real dos conceitos de orientação a objetos
- Organização de código em camadas responsáveis
- Implementação de padrões de projeto
- Desenvolvimento de sistema completo com Java puro, sem utilização de bibliotecas externas (exceto pelo empacotamento do JAR final com **GradleUp**: `./gradlew shadowJar`)