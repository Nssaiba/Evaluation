package com.example;

import com.example.util.HibernateUtil;
import org.hibernate.Session;

public class TestConnexion {
    public static void main(String[] args) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            System.out.println("✅ Connexion Hibernate réussie !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
