package br.com.VitrineCar.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.VitrineCar.entidade.Tag;

import java.util.List;

public class TagDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");

    public void salvar(Tag tag) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(tag);
        em.getTransaction().commit();
        em.close();
    }

    public Tag buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Tag tag = em.find(Tag.class, id);
        em.close();
        return tag;
    }

    public List<Tag> listar() {
        EntityManager em = emf.createEntityManager();
        List<Tag> tags = em.createQuery("FROM Tag", Tag.class).getResultList();
        em.close();
        return tags;
    }

    public void atualizar(Tag tag) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(tag);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Tag tag = em.find(Tag.class, id);
        if (tag != null) {
            em.remove(tag);
        }
        em.getTransaction().commit();
        em.close();
    }
}

