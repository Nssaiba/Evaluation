package com.example.service;

import com.example.dao.IDao;
import com.example.classes.Employe;
import com.example.classes.Projet;
import com.example.classes.Tache;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public boolean create(Employe e) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.persist(e);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Employe e) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.merge(e);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Employe e) {
        Transaction t = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            t = session.beginTransaction();
            session.remove(e);
            t.commit();
            return true;
        } catch (Exception ex) {
            if (t != null) t.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Employe findById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Employe.class, id);
        }
    }

    @Override
    public List<Employe> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Employe", Employe.class).list();
        }
    }



    /**
     * Affiche la liste des tâches réalisées par un employé donné.
     */
    public List<Tache> getTachesParEmploye(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "select et.tache from EmployeTache et where et.employe.id = :id",
                            Tache.class
                    ).setParameter("id", employeId)
                    .list();
        }
    }

    /**
     * Affiche la liste des projets gérés par un employé donné (chef de projet).
     */
    public List<Projet> getProjetsGeres(int employeId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "from Projet p where p.chefProjet.id = :id",
                            Projet.class
                    ).setParameter("id", employeId)
                    .list();
        }
    }
}
