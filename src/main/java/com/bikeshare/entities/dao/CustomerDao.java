package com.bikeshare.entities.dao;

import com.bikeshare.entities.Customer;
import com.bikeshare.persistence.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class CustomerDao {

    private EntityManagerFactory emf = JPAUtil.getEmf();

    public void salvar (Customer customer) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(customer);
        em.getTransaction().commit();
        em.close();
    }

    public Customer buscarPorId(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Customer customer = em.find(Customer.class, id);
        em.getTransaction().commit();
        em.close();
        return customer;
    }

}
