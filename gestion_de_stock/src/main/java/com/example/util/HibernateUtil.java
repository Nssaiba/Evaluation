package com.example.util;

import com.example.classes.Categorie;
import com.example.classes.Commande;
import com.example.classes.LigneCommandeProduit;
import com.example.classes.Produit;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    static {
        try {
            // Création de la configuration Hibernate
            Configuration configuration = new Configuration();

            // Ajout des entités annotées
            configuration.addAnnotatedClass(Categorie.class);
            configuration.addAnnotatedClass(Produit.class);
            configuration.addAnnotatedClass(Commande.class);
            configuration.addAnnotatedClass(LigneCommandeProduit.class);

            // Lecture des paramètres de la base de données depuis application.properties ou hibernate.cfg.xml
            configuration.configure(); // si tu as un hibernate.cfg.xml, sinon tu peux configurer ici directement

            // Construction du ServiceRegistry
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties());

            // Construction de la SessionFactory
            sessionFactory = configuration.buildSessionFactory(builder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir la SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
