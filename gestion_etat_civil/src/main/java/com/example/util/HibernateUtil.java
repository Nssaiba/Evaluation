package com.example.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                // Charger application.properties
                Properties properties = new Properties();
                try (InputStream input = HibernateUtil.class.getClassLoader()
                        .getResourceAsStream("application.properties")) {
                    if (input == null) {
                        throw new RuntimeException("Fichier application.properties introuvable !");
                    }
                    properties.load(input);
                }

                // Configurer Hibernate
                Configuration configuration = new Configuration();

                configuration.setProperty(Environment.DRIVER, properties.getProperty("spring.datasource.driver-class-name"));
                configuration.setProperty(Environment.URL, properties.getProperty("spring.datasource.url"));
                configuration.setProperty(Environment.USER, properties.getProperty("spring.datasource.username"));
                configuration.setProperty(Environment.PASS, properties.getProperty("spring.datasource.password"));
                configuration.setProperty(Environment.DIALECT, properties.getProperty("spring.jpa.properties.hibernate.dialect"));
                configuration.setProperty(Environment.HBM2DDL_AUTO, properties.getProperty("spring.jpa.hibernate.ddl-auto"));
                configuration.setProperty(Environment.SHOW_SQL, properties.getProperty("spring.jpa.show-sql"));
                configuration.setProperty(Environment.FORMAT_SQL, properties.getProperty("spring.jpa.properties.hibernate.format_sql"));

                // Ajouter les entités ici
                configuration.addAnnotatedClass(com.example.beans.Femme.class);
                configuration.addAnnotatedClass(com.example.beans.Homme.class);
                configuration.addAnnotatedClass(com.example.beans.Mariage.class);
                configuration.addAnnotatedClass(com.example.beans.Personne.class);

                ServiceRegistry serviceRegistry =
                        new StandardServiceRegistryBuilder()
                                .applySettings(configuration.getProperties())
                                .build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (IOException e) {
                throw new RuntimeException("Erreur lors du chargement du fichier application.properties", e);
            } catch (Exception e) {
                throw new RuntimeException("Erreur de configuration Hibernate", e);
            }
        }
        return sessionFactory;
    }
}


