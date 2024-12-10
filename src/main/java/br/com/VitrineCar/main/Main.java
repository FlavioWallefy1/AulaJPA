package br.com.VitrineCar.main;

import br.com.VitrineCar.entidade.*;
import br.com.VitrineCar.persistence.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Instanciar os DAOs
        MarcaDAO marcaDAO = new MarcaDAO();
        ModeloDAO modeloDAO = new ModeloDAO();
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        PerfilDAO perfilDAO = new PerfilDAO();
        VeiculoDAO veiculoDAO = new VeiculoDAO();
        TagDAO tagDAO = new TagDAO();

        // Criar e salvar uma Marca
        Marca marca = new Marca();
        marca.setNome("Honda");
        marcaDAO.salvar(marca);
        System.out.println("Marca salva: " + marca.getNome());

        // Criar e salvar um Modelo vinculado à Marca
        Modelo modelo = new Modelo();
        modelo.setNome("Civic");
        modelo.setMarca(marca);
        modeloDAO.salvar(modelo);
        System.out.println("Modelo salvo: " + modelo.getNome() + " da marca " + modelo.getMarca().getNome());

        // Criar e salvar um Usuário
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@email.com");
        usuario.setSenha("senha123");
        usuarioDAO.salvar(usuario);
        System.out.println("Usuário salvo: " + usuario.getEmail());

        // Criar e salvar um Perfil vinculado ao Usuário
        Perfil perfil = new Perfil();
        perfil.setNome("Administrador");
        perfil.setUsuario(usuario);
        perfilDAO.salvar(perfil);
        System.out.println("Perfil salvo: " + perfil.getNome() + " vinculado ao usuário " + perfil.getUsuario().getEmail());

        // Criar e salvar um Veículo vinculado ao Modelo
        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca("XYZ-9876");
        veiculo.setModelo(modelo);
        veiculoDAO.salvar(veiculo);
        System.out.println("Veículo salvo: " + veiculo.getPlaca() + " do modelo " + veiculo.getModelo().getNome());

        // Criar e salvar uma Tag
        Tag tag = new Tag();
        tag.setNome("Sedan");
        tagDAO.salvar(tag);
        System.out.println("Tag salva: " + tag.getNome());

        // Associar a Tag ao Veículo
        veiculo.setTags(List.of(tag));
        veiculoDAO.atualizar(veiculo);
        System.out.println("Tag associada ao veículo: " + veiculo.getPlaca());

        // Listar todas as Marcas
        List<Marca> marcas = marcaDAO.listar();
        System.out.println("\nMarcas cadastradas:");
        for (Marca m : marcas) {
            System.out.println("- " + m.getNome());
        }

        // Listar todos os Veículos
        List<Veiculo> veiculos = veiculoDAO.listar();
        System.out.println("\nVeículos cadastrados:");
        for (Veiculo v : veiculos) {
            System.out.println("- Placa: " + v.getPlaca() + ", Modelo: " + v.getModelo().getNome());
        }

        // Buscar um Usuário por ID
        Usuario usuarioEncontrado = usuarioDAO.buscarPorId(usuario.getId());
        System.out.println("\nUsuário encontrado: " + usuarioEncontrado.getEmail());

        // Atualizar dados do Usuário
        usuarioEncontrado.setSenha("novaSenha456");
        usuarioDAO.atualizar(usuarioEncontrado);
        System.out.println("Senha do usuário atualizada para: " + usuarioEncontrado.getSenha());

        // Remover um Modelo
        modeloDAO.remover(modelo.getId());
        System.out.println("\nModelo removido: " + modelo.getNome());
    }
}

