package com.example.service;

import com.example.beans.Homme;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class HommeService {

    public boolean create(Homme homme) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.persist(homme);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public boolean update(Homme homme) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.merge(homme);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(Homme homme) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.remove(session.merge(homme));
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public List<Homme> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Homme", Homme.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Homme> findHommesMarieesAQuatreFemmesEntreDates(Date debut, Date fin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT h FROM Homme h JOIN h.mariages m " +
                                    "WHERE m.dateDebut BETWEEN :d1 AND :d2 " +
                                    "GROUP BY h HAVING COUNT(DISTINCT m.femme) = 4",
                            Homme.class)
                    .setParameter("d1", debut)
                    .setParameter("d2", fin)
                    .list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
