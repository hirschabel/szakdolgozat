package hu.szakdolgozat.dao;

import hu.szakdolgozat.hajo.SzintAdat;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SzintAdatDao {

    public SzintAdat getSzintAdat(int szint) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SzintAdat szintAdat;
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            EntityManager em = sessionFactory.createEntityManager();

            em.getTransaction().begin();
            szintAdat = em.find(SzintAdat.class, szint);
            em.getTransaction().commit();
        }
        return szintAdat;
    }
}
