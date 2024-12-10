package br.com.VitrineCar.persistence;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.VitrineCar.entidade.Modelo;

import java.util.List;

public class ModeloDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");

    public void salvar(Modelo modelo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(modelo);
        em.getTransaction().commit();
        em.close();
    }

    public Modelo buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Modelo modelo = em.find(Modelo.class, id);
        em.close();
        return modelo;
    }

    public List<Modelo> listar() {
        EntityManager em = emf.createEntityManager();
        List<Modelo> modelos = em.createQuery("FROM Modelo", Modelo.class).getResultList();
        em.close();
        return modelos;
    }

    public void atualizar(Modelo modelo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(modelo);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Modelo modelo = em.find(Modelo.class, id);
        if (modelo != null) {
            em.remove(modelo);
        }
        em.getTransaction().commit();
        em.close();
    }
}

