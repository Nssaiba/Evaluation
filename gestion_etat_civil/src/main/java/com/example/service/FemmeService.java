package com.example.service;

import com.example.beans.Femme;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FemmeService {

    public boolean create(Femme femme) {
        Transaction t = null;
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            t = session.beginTransaction();
            session.persist(femme);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }


    public boolean update(Femme femme) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.merge(femme);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(Femme femme) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.remove(session.merge(femme)); // merge pour éviter les detached entity
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public List<Femme> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Femme", Femme.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public Femme findOldest() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Femme f order by f.dateNaissance asc", Femme.class)
                    .setMaxResults(1)
                    .uniqueResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Femme> findFemmesMarieesPlusieursFois() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                    "SELECT f FROM Femme f JOIN f.mariages m GROUP BY f HAVING COUNT(m) >= 2",
                    Femme.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
