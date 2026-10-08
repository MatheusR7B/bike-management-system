package com.bikeshare.entities.dao;

import com.bikeshare.entities.Ride;
import com.bikeshare.enums.RideStatus;
import com.bikeshare.persistence.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class RideDao {

    private EntityManagerFactory emf = JPAUtil.getEmf();

    public void salvar(Ride ride){

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(ride);
        em.getTransaction().commit();
        em.close();
    }

    public void update(Ride ride){
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(ride);
        em.getTransaction().commit();
        em.close();
    }

    public Ride buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Ride ride = em.find(Ride.class, id);
        em.getTransaction().commit();
        em.close();
        return ride;
    }

    public Long finishRide(){
        EntityManager em = emf.createEntityManager();
        Long quantity = em.createQuery("SELECT COUNT(b) FROM Ride b WHERE b.status = :status", Long.class)
                .setParameter("status", RideStatus.FINALIZADA)
                .getSingleResult();
        return quantity;
    }

    public List<Object[]> bikeMaisUsada(){
        EntityManager em = emf.createEntityManager();
        List<Object[]> result = em.createQuery(
                "SELECT r.bike, COUNT(r) FROM Ride r WHERE r.status = :status GROUP BY r.bike ORDER BY COUNT(r) DESC",
                Object[].class)
                .setParameter("status", RideStatus.FINALIZADA)
                .getResultList();
        em.close();
        return result;
    }

    public List<Object[]> estacaoMaisUsada(){
        EntityManager em =emf.createEntityManager();
        List<Object[]> result = em.createQuery(
                "SELECT r.startStation, COUNT(r) FROM Ride r WHERE r.status = :status GROUP BY r.startStation ORDER BY COUNT(r) DESC",
                Object[].class)
                .setParameter("status", RideStatus.FINALIZADA)
                .getResultList();
        em.close();
        return result;
    }

    public Double avgDuration() {
        EntityManager em = emf.createEntityManager();
        Double media = em.createQuery("" +
                "SELECT AVG(r.duration) FROM Ride r WHERE r.status = :status", Double.class)
                .setParameter("status", RideStatus.FINALIZADA)
                .getSingleResult();
        em.close();
        return media;
    }

}
