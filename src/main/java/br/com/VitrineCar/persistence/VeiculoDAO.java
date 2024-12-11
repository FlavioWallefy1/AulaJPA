package br.com.VitrineCar.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;

import br.com.VitrineCar.entidade.Veiculo;

import java.util.List;

public class VeiculoDAO {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("crud-basic");

    public List<Veiculo> listar() {
        EntityManager em = emf.createEntityManager();
        List<Veiculo> veiculos = em.createQuery(
                "SELECT DISTINCT v FROM Veiculo v LEFT JOIN FETCH v.tags", Veiculo.class
        ).getResultList();
        em.close();
        return veiculos;
    }

    public void salvar(Veiculo veiculo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(veiculo);
        em.getTransaction().commit();
        em.close();
    }

    public Veiculo buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        Veiculo veiculo = em.find(Veiculo.class, id);
        em.close();
        return veiculo;
    }

    public Veiculo buscarPorPlaca(String placa) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM Veiculo WHERE placa = :placa", Veiculo.class)
                     .setParameter("placa", placa)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public void atualizar(Veiculo veiculo) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(veiculo);
        em.getTransaction().commit();
        em.close();
    }

    public void remover(Long id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Veiculo veiculo = em.find(Veiculo.class, id);
        if (veiculo != null) {
            em.remove(veiculo);
        }
        em.getTransaction().commit();
        em.close();
    }
}
