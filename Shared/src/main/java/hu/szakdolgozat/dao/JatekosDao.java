package hu.szakdolgozat.dao;

import hu.szakdolgozat.jatekos.Jatekos;
import hu.szakdolgozat.Pozicio;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.List;

public class JatekosDao {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Jatekos getJatekos(String name) {
        Jatekos jatekos;
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            jatekos = session.find(Jatekos.class, name);
            if (jatekos == null) {
                jatekos = new Jatekos(name, new Pozicio());
                session.persist(jatekos);
            }
            jatekos.pozicioRandomizalas();
            session.getTransaction().commit();
        }
        return jatekos;
    }

    public void jatekosokMentese(List<Jatekos> jatekosLista) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            for (Jatekos jatekos : jatekosLista) {
                session.merge(jatekos);
            }
            session.getTransaction().commit();
        }
    }

    public void jatekosMentes(Jatekos jatekos) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.merge(jatekos);
            session.getTransaction().commit();
        }
    }
}
