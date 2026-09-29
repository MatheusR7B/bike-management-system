package com.bikeshare.persistence;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static EntityManagerFactory emf;

    public static EntityManagerFactory getEmf() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("bikeshare-pu");
        }
        return emf;
    }

    public static void close() {
        if (emf != null) {
            emf.close();
        }
    }

}
