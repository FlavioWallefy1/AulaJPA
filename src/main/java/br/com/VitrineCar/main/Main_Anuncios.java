package br.com.VitrineCar.main;

import br.com.VitrineCar.entidade.Anuncio;
import br.com.VitrineCar.entidade.Usuario;
import br.com.VitrineCar.entidade.Veiculo;
import br.com.VitrineCar.entidade.Marca;
import br.com.VitrineCar.entidade.Modelo;
import br.com.VitrineCar.entidade.Categoria;
import br.com.VitrineCar.persistence.AnuncioDAO;
import br.com.VitrineCar.persistence.UsuarioDAO;
import br.com.VitrineCar.persistence.VeiculoDAO;
import br.com.VitrineCar.persistence.MarcaDAO;
import br.com.VitrineCar.persistence.ModeloDAO;
import br.com.VitrineCar.persistence.CategoriaDAO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main_Anuncios {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        AnuncioDAO anuncioDAO = new AnuncioDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        MarcaDAO marcaDAO = new MarcaDAO();
        ModeloDAO modeloDAO = new ModeloDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        // Inicializar usuários e veículos pré-definidos no sistema
        inicializarUsuarios(usuarioDAO);
        inicializarVeiculos(veiculoDAO, marcaDAO, modeloDAO, categoriaDAO);

        while (true) {
            System.out.println("\n=== Gerenciamento de Anúncios ===");
            System.out.println("1. Cadastrar Anúncio");
            System.out.println("2. Listar Anúncios");
            System.out.println("3. Editar Anúncio");
            System.out.println("4. Remover Anúncio");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        cadastrarAnuncio(scanner, anuncioDAO, usuarioDAO, veiculoDAO);
                        break;
                    case 2:
                        listarAnuncios(anuncioDAO);
                        break;
                    case 3:
                        editarAnuncio(scanner, anuncioDAO, veiculoDAO);
                        break;
                    case 4:
                        removerAnuncio(scanner, anuncioDAO);
                        break;
                    case 5:
                        System.out.println("Saindo do sistema...");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida. Por favor, digite um número.");
            }
        }
    }

    private static void inicializarUsuarios(UsuarioDAO usuarioDAO) {
        if (usuarioDAO.listar().isEmpty()) {
            System.out.println("Inicializando usuários no sistema...");
            String[] emails = {"Leo Lima", "Victor Lira", "Delano Helio"};
            for (String email : emails) {
                Usuario usuario = new Usuario();
                usuario.setEmail(email);
                usuarioDAO.salvar(usuario);
            }
            System.out.println("Usuários pré-definidos foram adicionados ao sistema.");
        }
    }

    private static void inicializarVeiculos(VeiculoDAO veiculoDAO, MarcaDAO marcaDAO, ModeloDAO modeloDAO, CategoriaDAO categoriaDAO) {
        if (veiculoDAO.listar().isEmpty()) {
            System.out.println("Inicializando veículos no sistema...");

            // Criar uma marca
            Marca marca = new Marca();
            marca.setNome("Toyota");
            marcaDAO.salvar(marca);

            // Criar um modelo
            Modelo modelo = new Modelo();
            modelo.setNome("Corolla");
            modelo.setMarca(marca);
            modeloDAO.salvar(modelo);

            // Criar uma categoria
            Categoria categoria = new Categoria();
            categoria.setNome("Sedan");
            categoriaDAO.salvar(categoria);

            // Criar veículos
            String[] placas = {"ABC1234", "XYZ5678", "DEF4321"};
            for (String placa : placas) {
                Veiculo veiculo = new Veiculo();
                veiculo.setPlaca(placa);
                veiculo.setModelo(modelo);
                veiculo.setCategoria(categoria);
                veiculoDAO.salvar(veiculo);
            }
            System.out.println("Veículos pré-definidos foram adicionados ao sistema.");
        }
    }

    private static void cadastrarAnuncio(Scanner scanner, AnuncioDAO anuncioDAO, UsuarioDAO usuarioDAO, VeiculoDAO veiculoDAO) {
        System.out.println("\n=== Cadastrar Anúncio ===");

        // Selecionar Usuário
        System.out.println("Selecione o usuário que criará o anúncio:");
        List<Usuario> usuarios = usuarioDAO.listar();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário disponível. Operação cancelada.");
            return;
        }
        Usuario usuario = selecionarUsuario(scanner, usuarios);

        // Selecionar Veículo
        System.out.println("Selecione o veículo para o anúncio:");
        List<Veiculo> veiculos = veiculoDAO.listar();
        if (veiculos.isEmpty()) {
            System.out.println("Nenhum veículo disponível. Operação cancelada.");
            return;
        }
        Veiculo veiculo = selecionarVeiculo(scanner, veiculos);

        // Detalhes do Anúncio
        System.out.print("Informe o título do anúncio: ");
        String titulo = scanner.nextLine();

        System.out.print("Informe a descrição do anúncio: ");
        String descricao = scanner.nextLine();

        System.out.print("Informe o preço do veículo: ");
        Double preco;
        while (true) {
            try {
                preco = Double.parseDouble(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Erro: Informe um valor válido para o preço.");
            }
        }

        // Criar Anúncio
        Anuncio anuncio = new Anuncio();
        anuncio.setUsuario(usuario);
        anuncio.setVeiculo(veiculo);
        anuncio.setTitulo(titulo);
        anuncio.setDescricao(descricao);
        anuncio.setPreco(preco);
        anuncio.setDataCriacao(LocalDateTime.now());

        anuncioDAO.salvar(anuncio);
        System.out.println("Anúncio cadastrado com sucesso!");
    }

    private static void listarAnuncios(AnuncioDAO anuncioDAO) {
        System.out.println("\n=== Lista de Anúncios ===");
        List<Anuncio> anuncios = anuncioDAO.listar();
        if (anuncios.isEmpty()) {
            System.out.println("Nenhum anúncio cadastrado.");
        } else {
            for (Anuncio anuncio : anuncios) {
                System.out.printf("ID: %d | Título: %s | Preço: %.2f | Veículo: %s (%s) | Usuário: %s\n",
                        anuncio.getId(), anuncio.getTitulo(), anuncio.getPreco(),
                        anuncio.getVeiculo().getModelo().getNome(),
                        anuncio.getVeiculo().getPlaca(), anuncio.getUsuario().getEmail());
            }
        }
    }

    private static void editarAnuncio(Scanner scanner, AnuncioDAO anuncioDAO, VeiculoDAO veiculoDAO) {
        System.out.println("\n=== Editar Anúncio ===");
        System.out.print("Informe o ID do anúncio que deseja editar: ");
        Long id = Long.parseLong(scanner.nextLine());
        Anuncio anuncio = anuncioDAO.buscarPorId(id);

        if (anuncio == null) {
            System.out.println("Anúncio não encontrado.");
            return;
        }

        // Atualizar informações do anúncio
        System.out.print("Informe o novo título do anúncio (atual: " + anuncio.getTitulo() + "): ");
        anuncio.setTitulo(scanner.nextLine());

        System.out.print("Informe a nova descrição do anúncio (atual: " + anuncio.getDescricao() + "): ");
        anuncio.setDescricao(scanner.nextLine());

        System.out.print("Informe o novo preço do anúncio (atual: " + anuncio.getPreco() + "): ");
        anuncio.setPreco(Double.parseDouble(scanner.nextLine()));

        System.out.println("Selecione o novo veículo para o anúncio:");
        List<Veiculo> veiculos = veiculoDAO.listar();
        if (!veiculos.isEmpty()) {
            Veiculo novoVeiculo = selecionarVeiculo(scanner, veiculos);
            anuncio.setVeiculo(novoVeiculo);
        }

        anuncioDAO.atualizar(anuncio);
        System.out.println("Anúncio atualizado com sucesso!");
    }

    private static void removerAnuncio(Scanner scanner, AnuncioDAO anuncioDAO) {
        System.out.println("\n=== Remover Anúncio ===");
        System.out.print("Informe o ID do anúncio que deseja remover: ");
        Long id = Long.parseLong(scanner.nextLine());
        Anuncio anuncio = anuncioDAO.buscarPorId(id);

        if (anuncio == null) {
            System.out.println("Anúncio não encontrado.");
            return;
        }

        anuncioDAO.remover(id);
        System.out.println("Anúncio removido com sucesso!");
    }

    private static Usuario selecionarUsuario(Scanner scanner, List<Usuario> usuarios) {
        for (int i = 0; i < usuarios.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, usuarios.get(i).getEmail());
        }
        while (true) {
            System.out.print("Escolha um usuário: ");
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                if (opcao > 0 && opcao <= usuarios.size()) {
                    return usuarios.get(opcao - 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida.");
            }
        }
    }

    private static Veiculo selecionarVeiculo(Scanner scanner, List<Veiculo> veiculos) {
        for (int i = 0; i < veiculos.size(); i++) {
            System.out.printf("%d. %s (%s)\n", i + 1, veiculos.get(i).getModelo().getNome(), veiculos.get(i).getPlaca());
        }
        while (true) {
            System.out.print("Escolha um veículo: ");
            try {
                int opcao = Integer.parseInt(scanner.nextLine());
                if (opcao > 0 && opcao <= veiculos.size()) {
                    return veiculos.get(opcao - 1);
                }
            } catch (NumberFormatException e) {
                System.out.println("Erro: Entrada inválida.");
            }
        }
    }
}
