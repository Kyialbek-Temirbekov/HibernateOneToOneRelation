package org.example;

import org.example.model.Principal;
import org.example.model.School;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Principal.class).addAnnotatedClass(School.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        try (sessionFactory) {
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            Principal principal1 = session.get(Principal.class, 3);
            System.out.println(principal1);
            School school1 = principal1.getSchool();
            System.out.println(school1);

            School school2 = session.get(School.class, 3);
            System.out.println(school2);
            Principal principal2 = school2.getPrincipal();
            System.out.println(principal2);

            Principal principal3 = new Principal("Ben", 44);
            School school3 = new School(121);
            principal3.setSchool(school3);
            session.persist(principal3);

            School school4 = session.get(School.class, 5);
            Principal principal4 = new Principal("Gaby", 25);
            session.persist(principal4);
            principal4.setSchool(school4);

            Principal principal = session.get(Principal.class, 1);
            principal.getSchool().setPrincipal(null);

            session.getTransaction().commit();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            principal = session.get(Principal.class, 1);
            School school = session.get(School.class, 11);
            principal.setSchool(school);

            session.getTransaction().commit();
        }
    }
}
