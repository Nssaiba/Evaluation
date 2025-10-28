package com.example.service;

import com.example.beans.Mariage;
import com.example.beans.Homme;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MariageService {

    public boolean create(Mariage mariage) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.persist(mariage);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public boolean update(Mariage mariage) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.merge(mariage);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public boolean delete(Mariage mariage) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.remove(session.merge(mariage));
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public List<Mariage> findByHomme(Homme homme) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Mariage m where m.homme = :homme", Mariage.class)
                    .setParameter("homme", homme)
                    .list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<Mariage> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Mariage", Mariage.class).list();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
