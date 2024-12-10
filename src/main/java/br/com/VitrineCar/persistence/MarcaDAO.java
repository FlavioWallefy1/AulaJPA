package br.com.VitrineCar.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.VitrineCar.entidade.Marca;

import java.util.List;

public class MarcaDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");

    public void salvar(Marca marca) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(marca);
        em.getTransaction().commit();
        em.close();
    }

    public Marca buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Marca marca = em.find(Marca.class, id);
        em.close();
        return marca;
    }

    public List<Marca> listar() {
        EntityManager em = emf.createEntityManager();
        List<Marca> marcas = em.createQuery("FROM Marca", Marca.class).getResultList();
        em.close();
        return marcas;
    }

    public void atualizar(Marca marca) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(marca);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Marca marca = em.find(Marca.class, id);
        if (marca != null) {
            em.remove(marca);
        }
        em.getTransaction().commit();
        em.close();
    }
}

