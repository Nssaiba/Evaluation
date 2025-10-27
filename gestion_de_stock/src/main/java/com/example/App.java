package com.example;

import com.example.classes.Commande;
import com.example.classes.LigneCommandeProduit;
import com.example.classes.Produit;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import com.example.util.HibernateConfig;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class App {
    public static void main(String[] args) {


        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(HibernateConfig.class);

        SessionFactory sessionFactory = context.getBean(SessionFactory.class);
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        try {
            // --- Création des produits ---
            Produit p1 = new Produit("ES12", 120);
            Produit p2 = new Produit("ZR85", 100);
            Produit p3 = new Produit("EE85", 200);

            session.save(p1);
            session.save(p2);
            session.save(p3);

            // --- Création de la commande ---
            Commande commande = new Commande();
            LocalDate localDate = LocalDate.of(2013, 3, 14);
            Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            commande.setDateCommande(date);
            session.save(commande);

            // --- Création des lignes de commande ---
            LigneCommandeProduit l1 = new LigneCommandeProduit(commande, p1, 7);
            LigneCommandeProduit l2 = new LigneCommandeProduit(commande, p2, 14);
            LigneCommandeProduit l3 = new LigneCommandeProduit(commande, p3, 5);

            session.save(l1);
            session.save(l2);
            session.save(l3);

            tx.commit();

            // --- Affichage ---
            System.out.println("✅ Commande créée avec succès !");
            System.out.println("Commande : " + commande.getId() + "     Date : " + commande.getDateCommande());
            System.out.println("Liste des produits :");
            System.out.println("Référence\tPrix\tQuantité");
            System.out.println("---------------------------------");
            System.out.println(p1.getReference() + "\t" + p1.getPrix() + " DH\t" + l1.getQuantite());
            System.out.println(p2.getReference() + "\t" + p2.getPrix() + " DH\t" + l2.getQuantite());
            System.out.println(p3.getReference() + "\t" + p3.getPrix() + " DH\t" + l3.getQuantite());

        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            sessionFactory.close();
            context.close();
        }
    }
}
