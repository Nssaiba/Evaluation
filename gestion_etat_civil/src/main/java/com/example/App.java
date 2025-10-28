package com.example;

import com.example.beans.*;
import com.example.service.*;
import com.example.util.HibernateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class App {

    public static void main(String[] args) throws Exception {

        // 🔹 Initialisation du format de date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 🔹 Initialisation de Hibernate (cela crée la SessionFactory)
        HibernateUtil.getSessionFactory();

        // 🔹 Initialisation des services
        FemmeService femmeService = new FemmeService();
        HommeService hommeService = new HommeService();
        MariageService mariageService = new MariageService();

        // -------------------------------
        // 1️⃣ Création des femmes
        // -------------------------------
        List<Femme> femmes = new ArrayList<>();
        femmes.add(new Femme("Dupont", "Marie", sdf.parse("1980-05-12")));
        femmes.add(new Femme("Martin", "Sophie", sdf.parse("1985-03-21")));
        femmes.add(new Femme("Durand", "Claire", sdf.parse("1990-08-15")));
        femmes.add(new Femme("Bernard", "Lucie", sdf.parse("1978-01-22")));
        femmes.add(new Femme("Petit", "Emma", sdf.parse("1995-09-05")));
        femmes.add(new Femme("Rousseau", "Julie", sdf.parse("1982-04-18")));
        femmes.add(new Femme("Moreau", "Chloé", sdf.parse("1987-07-30")));
        femmes.add(new Femme("Fournier", "Laura", sdf.parse("1992-12-09")));
        femmes.add(new Femme("Girard", "Camille", sdf.parse("1984-10-10")));
        femmes.add(new Femme("Andre", "Sarah", sdf.parse("1979-02-25")));

        for (Femme f : femmes) femmeService.create(f);

        // -------------------------------
        // 2️⃣ Création des hommes
        // -------------------------------
        Homme h1 = new Homme("Lemoine", "Pierre", sdf.parse("1970-01-10"));
        Homme h2 = new Homme("Henry", "Ali", sdf.parse("1975-09-20"));
        Homme h3 = new Homme("Dubois", "Marc", sdf.parse("1968-04-12"));
        Homme h4 = new Homme("Thomas", "Jean", sdf.parse("1980-11-02"));
        Homme h5 = new Homme("Richard", "Paul", sdf.parse("1983-05-17"));

        hommeService.create(h1);
        hommeService.create(h2);
        hommeService.create(h3);
        hommeService.create(h4);
        hommeService.create(h5);

        // -------------------------------
        // 3️⃣ Affichage des femmes
        // -------------------------------
        System.out.println("\n👩 Liste des femmes :");
        femmeService.findAll().forEach(System.out::println);

        // -------------------------------
        // 4️⃣ Femme la plus âgée
        // -------------------------------
        Femme oldest = femmeService.findOldest();
        System.out.println("\n🧓 Femme la plus âgée : " + oldest.getPrenom() + " " + oldest.getNom());

        // -------------------------------
        // 5️⃣ Exemple de mariage
        // -------------------------------
        Mariage m1 = new Mariage(h1, femmes.get(0), sdf.parse("2005-06-15"), sdf.parse("2015-06-15"), 2);
        mariageService.create(m1);

        System.out.println("\n💍 Mariages de " + h1.getPrenom() + " :");
        mariageService.findByHomme(h1).forEach(System.out::println);

        // -------------------------------
        // 6️⃣ Fermeture propre d’Hibernate
        // -------------------------------
        HibernateUtil.getSessionFactory().close();
    }
}
