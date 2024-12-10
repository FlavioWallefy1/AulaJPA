package br.com.VitrineCar.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public abstract class GenericDAO<T> {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");
    private Class<T> entityClass;

    public GenericDAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void salvar(T entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
        em.close();
    }

    public T buscarPorId(Long id) {
        EntityManager em = getEntityManager();
        T entity = em.find(entityClass, id);
        em.close();
        return entity;
    }

    public List<T> listar() {
        EntityManager em = getEntityManager();
        List<T> lista = em.createQuery("FROM " + entityClass.getSimpleName(), entityClass).getResultList();
        em.close();
        return lista;
    }

    public void atualizar(T entity) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        T entity = em.find(entityClass, id);
        if (entity != null) {
            em.remove(entity);
        }
        em.getTransaction().commit();
        em.close();
    }
}

