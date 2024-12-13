package br.com.VitrineCar.persistence;

import br.com.VitrineCar.entidade.Perfil;

import javax.persistence.*;
import java.util.List;

public class PerfilDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");

    // Salvar um novo perfil
    public void salvar(Perfil perfil) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(perfil);
        em.getTransaction().commit();
        em.close();
    }

    // Listar todos os perfis
    public List<Perfil> listar() {
        EntityManager em = emf.createEntityManager();
        List<Perfil> perfis = em.createQuery("FROM Perfil", Perfil.class).getResultList();
        em.close();
        return perfis;
    }

    // Buscar perfil por nome
    public Perfil buscarPorNome(String nome) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Perfil WHERE nome = :nome", Perfil.class)
                     .setParameter("nome", nome)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; // Retorna null se não encontrar
        } finally {
            em.close();
        }
    }

    // Atualizar um perfil
    public void atualizar(Perfil perfil) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(perfil);
        em.getTransaction().commit();
        em.close();
    }

    // Remover um perfil
    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Perfil perfil = em.find(Perfil.class, id);
        if (perfil != null) {
            em.remove(perfil);
        }
        em.getTransaction().commit();
        em.close();
    }
}
