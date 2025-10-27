package com.example;

import com.example.classes.Tache;
import com.example.service.TacheService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestTacheService {
    public static void main(String[] args) {
        try {
            TacheService service = new TacheService();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            // 🔹 Test de la requête nommée : tâches dont le prix > 1000 DH
            System.out.println("=== Tâches avec prix > 1000 DH ===");
            List<Tache> tachesCheres = service.getTachesPrixSup1000();
            if (tachesCheres.isEmpty()) {
                System.out.println("Aucune tâche trouvée avec un prix > 1000 DH.");
            } else {
                tachesCheres.forEach(t ->
                        System.out.println(t.getNom() + " - " + t.getPrix() + " DH"));
            }

            // 🔹 Test des tâches réalisées entre deux dates
            System.out.println("\n=== Tâches entre 2025-01-01 et 2025-03-11 ===");
            Date d1 = sdf.parse("2013-02-09");
            Date d2 = sdf.parse("2013-03-16");

            List<Tache> tachesEntreDates = service.getTachesEntreDates(d1, d2);
            if (tachesEntreDates.isEmpty()) {
                System.out.println("Aucune tâche trouvée entre ces deux dates.");
            } else {
                tachesEntreDates.forEach(t ->
                        System.out.println(t.getNom() + " - du " + sdf.format(t.getDateDebut()) +
                                " au " + sdf.format(t.getDateFin())));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
