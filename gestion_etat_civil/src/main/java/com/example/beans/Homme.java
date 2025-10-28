package com.example.beans;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("H")
@Table(name = "hommes")
@NamedQueries({
        @NamedQuery(
                name = "Homme.findByNombreEpousesEntreDates",
                query = "SELECT h FROM Homme h WHERE " +
                        "(SELECT COUNT(DISTINCT m.femme) FROM Mariage m " +
                        "WHERE m.homme = h AND m.dateDebut >= :d1 " +
                        "AND (m.dateFin IS NULL OR m.dateFin <= :d2)) >= :nombreEpouses"
        )
})
public class Homme extends Personne {

    @OneToMany(mappedBy = "homme", cascade = CascadeType.ALL)
    private List<Mariage> mariages;

    public Homme() {}

    public Homme(String nom, String prenom, java.util.Date dateNaissance) {
        super(nom, prenom, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}
