package com.example.classes;


import javax.persistence.Entity;

import javax.persistence.NamedQuery;


import javax.persistence.*;

@Entity
@NamedQuery(name = "Produit.findByPrixSup100",
        query = "FROM Produit p WHERE p.prix > 100")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private double prix;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;


    public Produit() {
    }

    public Produit(String reference, double prix) {
        this.reference = reference;
        this.prix = prix;
    }

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
