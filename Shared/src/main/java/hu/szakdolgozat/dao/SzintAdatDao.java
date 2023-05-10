package hu.szakdolgozat.dao;

import hu.szakdolgozat.hajo.SzintAdat;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SzintAdatDao {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public SzintAdat getSzintAdat(int szint) {
        SzintAdat szintAdat;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            szintAdat = session.find(SzintAdat.class, szint);
            session.getTransaction().commit();
        }
        return szintAdat;
    }
}
