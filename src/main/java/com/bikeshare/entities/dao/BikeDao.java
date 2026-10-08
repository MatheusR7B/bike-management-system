package com.bikeshare.entities.dao;

import com.bikeshare.entities.Bike;
import com.bikeshare.entities.Station;
import com.bikeshare.enums.BikeStatus;
import com.bikeshare.persistence.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class BikeDao {

    private EntityManagerFactory emf = JPAUtil.getEmf();

    public void salvar(Bike bike) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(bike);
        em.getTransaction().commit();
        em.close();
    }

    public Bike bucasPorId(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Bike bike = em.find(Bike.class, id);
        em.getTransaction().commit();
        em.close();
        return bike;
    }

    public void update(Bike bike) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(bike);
        em.getTransaction().commit();
        em.close();
    }

    public List<Bike> buscarDisponiveisPorEstacao(Station station) {
        EntityManager em = emf.createEntityManager();
        List<Bike> result = em.createQuery(
                "SELECT b FROM Bike b WHERE b.station = :station AND b.status = :status", Bike.class)
                .setParameter("station", station)
                .setParameter("status", BikeStatus.DISPONIVEL)
                .getResultList();
        em.close();
        return result;
    }

    public Long totalBike() {
        EntityManager em = emf.createEntityManager();
        Long quantity = em.createQuery("SELECT COUNT(b) FROM Bike b",Long.class)
                .getSingleResult();
        em.close();
        return quantity;
    }

    public Long countStatus(BikeStatus status) {
        EntityManager em = emf.createEntityManager();
        Long quantity = em.createQuery("SELECT COUNT(b) FROM Bike b WHERE b.status = :status", Long.class)
                .setParameter("status", status)
                .getSingleResult();
        em.close();
        return quantity;
    }

}
