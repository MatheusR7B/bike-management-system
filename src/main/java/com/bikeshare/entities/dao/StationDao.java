package com.bikeshare.entities.dao;

import com.bikeshare.entities.Station;
import com.bikeshare.persistence.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class StationDao {

    private EntityManagerFactory emf = JPAUtil.getEmf();

    public void salvar(Station station) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(station);
        em.getTransaction().commit();
        em.close();
    }

    public Station buscaPorId(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Station station = em.find(Station.class, id);
        em.close();
        return station;
    }

    public List<Station> todasStation() {
        EntityManager em = emf.createEntityManager();
        List<Station> station = em.createQuery("SELECT s FROM Station s", Station.class)
                        .getResultList();
        em.close();
        return station;
    }


}
