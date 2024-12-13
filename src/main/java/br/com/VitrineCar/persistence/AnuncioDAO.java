package br.com.VitrineCar.persistence;

import br.com.VitrineCar.entidade.Anuncio;

import javax.persistence.*;
import java.util.List;

public class AnuncioDAO {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");

    public void salvar(Anuncio anuncio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(anuncio);
        em.getTransaction().commit();
        em.close();
    }

    public List<Anuncio> listar() {
        EntityManager em = emf.createEntityManager();
        List<Anuncio> anuncios = em.createQuery("FROM Anuncio", Anuncio.class).getResultList();
        em.close();
        return anuncios;
    }

    public Anuncio buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Anuncio anuncio = em.find(Anuncio.class, id);
        em.close();
        return anuncio;
    }

    public void atualizar(Anuncio anuncio) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(anuncio);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Anuncio anuncio = em.find(Anuncio.class, id);
        if (anuncio != null) {
            em.remove(anuncio);
        }
        em.getTransaction().commit();
        em.close();
    }
}
