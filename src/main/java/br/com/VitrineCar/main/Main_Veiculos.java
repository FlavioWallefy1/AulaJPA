package br.com.VitrineCar.main;

import br.com.VitrineCar.entidade.*;
import br.com.VitrineCar.persistence.*;

import java.util.List;
import java.util.Scanner;

public class Main_Veiculos {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Instanciar os DAOs
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        ModeloDAO modeloDAO = new ModeloDAO();
        MarcaDAO marcaDAO = new MarcaDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        TagDAO tagDAO = new TagDAO();

        // Inicializar as tags disponíveis
        inicializarTags(tagDAO);

        while (true) {
            int opcao = -1;
            boolean entradaValida = false;

            // Validação da entrada
            while (!entradaValida) {
                System.out.println("\n=== VitrineCar ===");
                System.out.println("1. Cadastrar Veículo");
                System.out.println("2. Listar Veículos");
                System.out.println("3. Editar Veículo");
                System.out.println("4. Remover Veículo");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opção: ");
                try {
                    opcao = Integer.parseInt(scanner.nextLine());
                    if (opcao >= 1 && opcao <= 5) {
                        entradaValida = true;
                    } else {
                        System.out.println("Opção inválida. Tente novamente.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Entrada inválida. Por favor, digite um número entre 1 e 5.");
                }
            }

            // Processar a opção escolhida
            switch (opcao) {
                case 1: // Criar veículo
                    cadastrarVeiculo(scanner, veiculoDAO, modeloDAO, marcaDAO, categoriaDAO, tagDAO);
                    break;
                case 2: // Listar veículos
                    listarVeiculos(veiculoDAO);
                    break;
                case 3: // Editar veículo
                    editarVeiculo(scanner, veiculoDAO, modeloDAO, marcaDAO, categoriaDAO, tagDAO);
                    break;
                case 4: // Remover veículo
                    removerVeiculo(scanner, veiculoDAO);
                    break;
                case 5: // Sair
                    System.out.println("Saindo do sistema...");
                    scanner.close();
                    return;
            }
        }
    }

    
    
    private static void inicializarTags(TagDAO tagDAO) {
        String[] tagsIniciais = {"Teto Solar", "Ar Condicionado", "Bancos de Couro", "Vidros Elétricos", "Sensor de Estacionamento"};
        for (String nomeTag : tagsIniciais) {
            if (tagDAO.buscarPorNome(nomeTag) == null) {
                Tag tag = new Tag();
                tag.setNome(nomeTag);
                tagDAO.salvar(tag);
            }
        }
    }

    
    
    private static void cadastrarVeiculo(Scanner scanner, VeiculoDAO veiculoDAO, ModeloDAO modeloDAO, MarcaDAO marcaDAO, CategoriaDAO categoriaDAO, TagDAO tagDAO) {
        System.out.println("\n=== Cadastro de Veículo ===");

        // Criar ou selecionar Marca
        System.out.print("Informe o nome da marca: ");
        String nomeMarca = scanner.nextLine();
        Marca marca = new Marca();
        marca.setNome(nomeMarca);
        marcaDAO.salvar(marca);

        // Criar ou selecionar Modelo
        System.out.print("Informe o nome do modelo: ");
        String nomeModelo = scanner.nextLine();
        Modelo modelo = new Modelo();
        modelo.setNome(nomeModelo);
        modelo.setMarca(marca);
        modeloDAO.salvar(modelo);

        // Criar ou selecionar Categoria
        System.out.print("Informe a categoria do veículo (ex: Sedan, SUV): ");
        String nomeCategoria = scanner.nextLine();
        Categoria categoria = new Categoria();
        categoria.setNome(nomeCategoria);
        categoriaDAO.salvar(categoria);

        // Criar Veículo
        Veiculo veiculo = new Veiculo();
        String placa;

        // Verificar se a placa já existe
        while (true) {
            System.out.print("Informe a placa do veículo: ");
            placa = scanner.nextLine();
            Veiculo veiculoExistente = veiculoDAO.buscarPorPlaca(placa);
            if (veiculoExistente == null) {
                break; // Placa não existe, pode continuar
            }
            System.out.println("Erro: Já existe um veículo cadastrado com essa placa. Tente novamente.");
        }
        veiculo.setPlaca(placa);
        veiculo.setModelo(modelo);
        veiculo.setCategoria(categoria);

        // Selecionar Tags (opcionais do veículo)
        System.out.println("\nOpcionais disponíveis:");
        List<Tag> tagsDisponiveis = tagDAO.listar();
        for (int i = 0; i < tagsDisponiveis.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, tagsDisponiveis.get(i).getNome());
        }

        System.out.println("Selecione os opcionais digitando o número correspondente (digite 0 para encerrar):");
        while (true) {
        	 try {
                 System.out.print("Escolha uma opção: ");
                 String input = scanner.nextLine();
                 int opcao = Integer.parseInt(input);

                 if (opcao == 0) {
                     break; // Encerrar seleção de tags
                 }
                 if (opcao > 0 && opcao <= tagsDisponiveis.size()) {
                     veiculo.getTags().add(tagsDisponiveis.get(opcao - 1));
                 } else {
                     System.out.println("Erro: Opção inválida. Escolha um número entre 1 e " + tagsDisponiveis.size() + ".");
                 }
             } catch (NumberFormatException e) {
                 System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
             }
        }

        // Salvar Veículo
        veiculoDAO.salvar(veiculo);
        System.out.println("Veículo cadastrado com sucesso com os seguintes opcionais:");
        for (Tag t : veiculo.getTags()) {
            System.out.println("- " + t.getNome());
        }
    }

    
    
    private static void listarVeiculos(VeiculoDAO veiculoDAO) {
        System.out.println("\n=== Lista de Veículos ===");
        List<Veiculo> veiculos = veiculoDAO.listar();
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo cadastrado.");
        } else {
            System.out.println("===== Veículos Encontrados =====");
            for (Veiculo v : veiculos) {
            	System.out.println("ID: " + v.getId());
                System.out.println("Modelo: " + v.getModelo().getNome());
                System.out.println("Marca: " + v.getModelo().getMarca().getNome());
                System.out.println("Categoria: " + v.getCategoria().getNome());
                System.out.println("Placa: " + v.getPlaca());               
                if (v.getTags().isEmpty()) {
                    System.out.println("Nenhum opcional.");
                } else {
                	System.out.println("Opcionais:");
                    for (Tag t : v.getTags()) {
                        System.out.println("  - " + t.getNome());
                    }
                }
                System.out.println("===============================");
            }
        }
    }



    private static void editarVeiculo(Scanner scanner, VeiculoDAO veiculoDAO, ModeloDAO modeloDAO, MarcaDAO marcaDAO, CategoriaDAO categoriaDAO, TagDAO tagDAO) {
        System.out.println("\n=== Editar Veículo ===");
        System.out.print("Informe o ID do veículo que deseja editar: ");
        try {
	        Long id = Long.parseLong(scanner.nextLine());
	        Veiculo veiculo = veiculoDAO.buscarPorId(id);
	
	        if (veiculo == null) {
	            System.out.println("Veículo não encontrado.");
	            return;
	        }
	
	        // Atualizar informações da Marca
	        System.out.print("Informe a nova marca (atual: " + veiculo.getModelo().getMarca().getNome() + "): ");
	        String nomeMarca = scanner.nextLine();
	        Marca novaMarca = new Marca();
	        novaMarca.setNome(nomeMarca);
	        marcaDAO.salvar(novaMarca);
	
	        // Atualizar informações do Modelo
	        System.out.print("Informe o novo modelo (atual: " + veiculo.getModelo().getNome() + "): ");
	        String nomeModelo = scanner.nextLine();
	        Modelo novoModelo = new Modelo();
	        novoModelo.setNome(nomeModelo);
	        novoModelo.setMarca(novaMarca);
	        modeloDAO.salvar(novoModelo);
	
	        // Atualizar informações da Categoria
	        System.out.print("Informe a nova categoria (atual: " + veiculo.getCategoria().getNome() + "): ");
	        String nomeCategoria = scanner.nextLine();
	        Categoria novaCategoria = new Categoria();
	        novaCategoria.setNome(nomeCategoria);
	        categoriaDAO.salvar(novaCategoria);
	
	        // Atualizar informações da Placa
	        String novaPlaca;
	        while (true) {
	            System.out.print("Informe a nova placa do veículo (atual: " + veiculo.getPlaca() + "): ");
	            novaPlaca = scanner.nextLine();
	            Veiculo veiculoExistente = veiculoDAO.buscarPorPlaca(novaPlaca);
	            if (veiculoExistente == null || veiculoExistente.getId().equals(veiculo.getId())) {
	                break; // Placa não existe ou pertence ao mesmo veículo
	            }
	            System.out.println("Erro: Já existe outro veículo cadastrado com essa placa. Tente novamente.");
	        }
	        veiculo.setPlaca(novaPlaca);
	
	        // Atualizar Tags
	        System.out.println("\nOpcionais disponíveis:");
	        List<Tag> tagsDisponiveis = tagDAO.listar();
	        for (int i = 0; i < tagsDisponiveis.size(); i++) {
	            System.out.printf("%d. %s\n", i + 1, tagsDisponiveis.get(i).getNome());
	        }
	
	        System.out.println("Selecione os opcionais digitando o número correspondente (digite 0 para encerrar):");
	        veiculo.getTags().clear(); // Limpar tags existentes
	        while (true) {
	        	 try {
	                 System.out.print("Escolha uma opção: ");
	                 String input = scanner.nextLine();
	                 int opcao = Integer.parseInt(input);
	
	                 if (opcao == 0) {
	                     break; // Encerrar seleção de tags
	                 }
	                 if (opcao > 0 && opcao <= tagsDisponiveis.size()) {
	                     veiculo.getTags().add(tagsDisponiveis.get(opcao - 1));
	                 } else {
	                     System.out.println("Erro: Opção inválida. Escolha um número entre 1 e " + tagsDisponiveis.size() + ".");
	                 }
	             } catch (NumberFormatException e) {
	                 System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
	             }
	        }
	
	        // Atualizar o veículo no banco de dados
	        veiculo.setModelo(novoModelo);
	        veiculo.setCategoria(novaCategoria);
	        veiculoDAO.atualizar(veiculo);
	
	        System.out.println("Veículo atualizado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida. Por favor, digite um número válido.");
        }
    }


    
    
    private static void removerVeiculo(Scanner scanner, VeiculoDAO veiculoDAO) {
        System.out.println("\n=== Remover Veículo ===");
        System.out.print("Informe o ID do veículo que deseja remover: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Veiculo veiculo = veiculoDAO.buscarPorId(id);

            if (veiculo == null) {
                System.out.println("Erro: Veículo não encontrado.");
                return;
            }
                veiculoDAO.remover(id);
                System.out.println("Veículo removido com sucesso!");
            
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida. Por favor, digite um número válido.");
        }
    }

}
