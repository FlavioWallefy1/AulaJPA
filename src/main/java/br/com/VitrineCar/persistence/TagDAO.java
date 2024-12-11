package br.com.VitrineCar.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import br.com.VitrineCar.entidade.Tag;

import java.util.List;

public class TagDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");

    public List<Tag> listar() {
        EntityManager em = emf.createEntityManager();
        List<Tag> tags = em.createQuery("FROM Tag", Tag.class).getResultList();
        em.close();
        return tags;
    }

    public Tag buscarPorNome(String nome) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Tag WHERE nome = :nome", Tag.class)
                     .setParameter("nome", nome)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void salvar(Tag tag) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tag);
        em.getTransaction().commit();
        em.close();
    }
}

