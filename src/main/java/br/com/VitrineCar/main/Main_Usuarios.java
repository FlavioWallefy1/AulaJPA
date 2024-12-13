package br.com.VitrineCar.main;

import br.com.VitrineCar.entidade.Perfil;
import br.com.VitrineCar.entidade.Usuario;
import br.com.VitrineCar.persistence.UsuarioDAO;

import java.util.List;
import java.util.Scanner;

public class Main_Usuarios {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        while (true) {
            System.out.println("\n=== Gerenciamento de Usuários ===");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Listar Usuários");
            System.out.println("3. Editar Usuário");
            System.out.println("4. Remover Usuário");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                int opcao = Integer.parseInt(scanner.nextLine());

                switch (opcao) {
                    case 1:
                        cadastrarUsuario(scanner, usuarioDAO);
                        break;
                    case 2:
                        listarUsuarios(usuarioDAO);
                        break;
                    case 3:
                        editarUsuario(scanner, usuarioDAO);
                        break;
                    case 4:
                        removerUsuario(scanner, usuarioDAO);
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

    private static void cadastrarUsuario(Scanner scanner, UsuarioDAO usuarioDAO) {
        System.out.println("\n=== Cadastrar Usuário ===");

        // Dados do Usuário
        System.out.print("Informe o e-mail do usuário: ");
        String email = scanner.nextLine();

        // Dados do Perfil
        System.out.print("Informe o nome do perfil: ");
        String nomePerfil = scanner.nextLine();

        System.out.print("Informe o endereço do perfil: ");
        String enderecoPerfil = scanner.nextLine();

        // Criar Usuário e Perfil
        Perfil perfil = new Perfil();
        perfil.setNome(nomePerfil);
        perfil.setEndereco(enderecoPerfil);

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPerfil(perfil);

        usuarioDAO.salvar(usuario);
        System.out.println("Usuário cadastrado com sucesso!");
    }

    private static void listarUsuarios(UsuarioDAO usuarioDAO) {
        System.out.println("\n=== Lista de Usuários ===");
        List<Usuario> usuarios = usuarioDAO.listar();
        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
        } else {
            for (Usuario u : usuarios) {
                Perfil perfil = u.getPerfil();
                System.out.printf("ID: %d | E-mail: %s | Nome: %s | Endereço: %s\n",
                        u.getId(), u.getEmail(), perfil.getNome(), perfil.getEndereco());
            }
        }
    }

    private static void editarUsuario(Scanner scanner, UsuarioDAO usuarioDAO) {
        System.out.println("\n=== Editar Usuário ===");
        System.out.print("Informe o ID do usuário que deseja editar: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Usuario usuario = usuarioDAO.buscarPorId(id);

            if (usuario == null) {
                System.out.println("Usuário não encontrado.");
                return;
            }

            // Atualizar e-mail
            System.out.print("Informe o novo e-mail do usuário (atual: " + usuario.getEmail() + "): ");
            usuario.setEmail(scanner.nextLine());

            // Atualizar Perfil
            Perfil perfil = usuario.getPerfil();
            System.out.print("Informe o novo nome do perfil (atual: " + perfil.getNome() + "): ");
            perfil.setNome(scanner.nextLine());

            System.out.print("Informe o novo endereço do perfil (atual: " + perfil.getEndereco() + "): ");
            perfil.setEndereco(scanner.nextLine());

            usuarioDAO.atualizar(usuario);
            System.out.println("Usuário atualizado com sucesso!");
        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida. Por favor, digite um número válido.");
        }
    }

    private static void removerUsuario(Scanner scanner, UsuarioDAO usuarioDAO) {
        System.out.println("\n=== Remover Usuário ===");
        System.out.print("Informe o ID do usuário que deseja remover: ");
        try {
            Long id = Long.parseLong(scanner.nextLine());
            Usuario usuario = usuarioDAO.buscarPorId(id);

            if (usuario == null) {
                System.out.println("Usuário não encontrado.");
                return;
            }

            System.out.printf("Tem certeza que deseja remover o usuário '%s'? (s/n): ", usuario.getEmail());
            String confirmacao = scanner.nextLine();
            if (confirmacao.equalsIgnoreCase("s")) {
                usuarioDAO.remover(id);
                System.out.println("Usuário removido com sucesso!");
            } else {
                System.out.println("Operação cancelada.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Erro: Entrada inválida. Por favor, digite um número válido.");
        }
    }
}
