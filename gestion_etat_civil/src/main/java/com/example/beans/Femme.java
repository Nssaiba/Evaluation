package com.example.beans;

import jakarta.persistence.*;
import java.util.List;

@Entity
@DiscriminatorValue("F")
@Table(name = "femmes")
@NamedQueries({
        @NamedQuery(name = "Femme.findOldest",
                query = "SELECT f FROM Femme f ORDER BY f.dateNaissance ASC"),
        @NamedQuery(name = "Femme.findMarriedAtLeastTwice",
                query = "SELECT f FROM Femme f WHERE SIZE(f.mariages) >= 2")
})
public class Femme extends Personne {

    @OneToMany(mappedBy = "femme", cascade = CascadeType.ALL)
    private List<Mariage> mariages;

    public Femme() {}

    public Femme(String nom, String prenom, java.util.Date dateNaissance) {
        super(nom, prenom, dateNaissance);
    }

    public List<Mariage> getMariages() {
        return mariages;
    }

    public void setMariages(List<Mariage> mariages) {
        this.mariages = mariages;
    }
}
