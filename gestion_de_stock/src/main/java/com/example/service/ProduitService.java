package com.example.service;

import com.example.classes.Categorie;
import com.example.classes.Commande;
import com.example.classes.Produit;
import com.example.classes.LigneCommandeProduit;
import com.example.dao.IDao;
import com.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public boolean create(Produit o) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Produit o) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.merge(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Produit o) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(o);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Produit getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Produit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Produit> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Produit", Produit.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Produits par catégorie
    public List<Produit> getByCategorie(Categorie cat) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "FROM Produit p WHERE p.categorie = :cat", Produit.class)
                    .setParameter("cat", cat)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Produits commandés entre deux dates
    public List<Object[]> getProduitsEntreDates(Date d1, Date d2) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT p.reference, p.prix, l.quantite " +
                                    "FROM LigneCommandeProduit l JOIN l.produit p JOIN l.commande c " +
                                    "WHERE c.dateCommande BETWEEN :d1 AND :d2",
                            Object[].class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Produits d'une commande donnée
    public List<Object[]> getProduitsByCommande(Commande cmd) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT p.reference, p.prix, l.quantite " +
                                    "FROM LigneCommandeProduit l JOIN l.produit p " +
                                    "WHERE l.commande = :cmd", Object[].class)
                    .setParameter("cmd", cmd)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Produits avec prix > 100 (requête nommée dans la classe Produit)
    public List<Produit> getProduitsPrixSup100() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createNamedQuery("Produit.findByPrixSup100", Produit.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
