package com.example;

import com.example.classes.*;
import com.example.service.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class App 
{
    public static void main(String[] args) throws Exception {


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        // 🔹 Services
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService employeTacheService = new EmployeTacheService();

        // -------------------------------
        // 1️⃣ Création d’un employé chef de projet
        // -------------------------------
        Employe chef = new Employe();
        chef.setNom("Dupont");
        chef.setPrenom("Jean");
        chef.setTelephone("0600000000");
        employeService.create(chef);

        // -------------------------------
        // 2️⃣ Création du projet
        // -------------------------------
        Projet projet = new Projet();
        projet.setNom("Gestion de stock");
        projet.setDateDebut(sdf.parse("14/01/2013"));
        projet.setChefProjet(chef);
        projetService.create(projet);

        // -------------------------------
        // 3️⃣ Création des tâches
        // -------------------------------
        Tache t1 = new Tache();
        t1.setNom("Analyse");
        t1.setDateDebut(sdf.parse("10/02/2013"));
        t1.setDateFin(sdf.parse("20/02/2013"));
        t1.setPrix(1500);
        t1.setProjet(projet);
        tacheService.create(t1);

        Tache t2 = new Tache();
        t2.setNom("Conception");
        t2.setDateDebut(sdf.parse("10/03/2013"));
        t2.setDateFin(sdf.parse("15/03/2013"));
        t2.setPrix(2000);
        t2.setProjet(projet);
        tacheService.create(t2);

        Tache t3 = new Tache();
        t3.setNom("Développement");
        t3.setDateDebut(sdf.parse("10/04/2013"));
        t3.setDateFin(sdf.parse("25/04/2013"));
        t3.setPrix(3000);
        t3.setProjet(projet);
        tacheService.create(t3);

        // -------------------------------
        // 4️⃣ Affecter les tâches à l’employé (EmployeTache)
        // -------------------------------
        EmployeTache et1 = new EmployeTache();
        et1.setEmploye(chef);
        et1.setTache(t1);
        et1.setDateDebutReelle(sdf.parse("10/02/2013"));
        et1.setDateFinReelle(sdf.parse("20/02/2013"));
        employeTacheService.create(et1);

        EmployeTache et2 = new EmployeTache();
        et2.setEmploye(chef);
        et2.setTache(t2);
        et2.setDateDebutReelle(sdf.parse("10/03/2013"));
        et2.setDateFinReelle(sdf.parse("15/03/2013"));
        employeTacheService.create(et2);

        EmployeTache et3 = new EmployeTache();
        et3.setEmploye(chef);
        et3.setTache(t3);
        et3.setDateDebutReelle(sdf.parse("10/04/2013"));
        et3.setDateFinReelle(sdf.parse("25/04/2013"));
        employeTacheService.create(et3);

        // -------------------------------
        // 5️⃣ Affichage du résultat demandé
        // -------------------------------
        Projet projetLoaded = projetService.findAll().get(0);
        List<Object[]> taches = projetService.getTachesRealisees(projetLoaded.getId());

        System.out.println("Projet : " + projetLoaded.getId()
                + "     Nom : " + projetLoaded.getNom()
                + "     Date début : " + sdf.format(projetLoaded.getDateDebut()));
        System.out.println("Liste des tâches:");
        System.out.println("Num  Nom             Date Début Réelle     Date Fin Réelle");
        System.out.println("-------------------------------------------------------------");

        for (Object[] row : taches) {
            int id = (int) row[0];
            String nom = (String) row[1];
            String debut = (row[2] != null) ? sdf.format((Date) row[2]) : "--/--/----";
            String fin = (row[3] != null) ? sdf.format((Date) row[3]) : "--/--/----";
            System.out.printf("%-4d %-15s %-20s %-15s%n", id, nom, debut, fin);
        }

        System.out.println("-------------------------------------------------------------");
    }
}
