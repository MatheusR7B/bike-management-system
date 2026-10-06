package com.bikeshare.entities.dao;

import com.bikeshare.entities.Ride;
import com.bikeshare.persistence.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

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


}
