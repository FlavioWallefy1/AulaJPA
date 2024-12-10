package br.com.VitrineCar.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.VitrineCar.entidade.Usuario;

import java.util.List;

public class UsuarioDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");

    public void salvar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public Usuario buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Usuario usuario = em.find(Usuario.class, id);
        em.close();
        return usuario;
    }

    public List<Usuario> listar() {
        EntityManager em = emf.createEntityManager();
        List<Usuario> usuarios = em.createQuery("FROM Usuario", Usuario.class).getResultList();
        em.close();
        return usuarios;
    }

    public void atualizar(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(usuario);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Usuario usuario = em.find(Usuario.class, id);
        if (usuario != null) {
            em.remove(usuario);
        }
        em.getTransaction().commit();
        em.close();
    }
}

