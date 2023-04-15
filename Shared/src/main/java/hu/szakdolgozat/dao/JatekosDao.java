package hu.szakdolgozat.dao;

import hu.szakdolgozat.Jatekos;
import hu.szakdolgozat.Pozicio;
import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;


public class JatekosDao {

    public Jatekos getJatekos(String name) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        Jatekos jatekos;
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            EntityManager em = sessionFactory.createEntityManager();

            em.getTransaction().begin();
            jatekos = em.find(Jatekos.class, name);
            if (jatekos == null) {
                jatekos = new Jatekos(name, new Pozicio());
                em.persist(jatekos);
            }
//            jatekos.getPozicio().randomizalas(); // Játékos mindig véletlenszerű pozícióban kezd
            em.getTransaction().commit();
        }
        return jatekos;
    }

    public void jatekosokMentese(List<Jatekos> jatekosLista) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        try (SessionFactory sessionFactory = configuration.buildSessionFactory()) {
            EntityManager em = sessionFactory.createEntityManager();

            em.getTransaction().begin();
            for (Jatekos jatekos : jatekosLista) {
                em.merge(jatekos);
            }
            em.getTransaction().commit();
        }
    }
}
