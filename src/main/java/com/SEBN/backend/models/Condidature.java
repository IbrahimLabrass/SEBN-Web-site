package com.SEBN.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "candidates")
public class Condidature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private String cv;
    private String lettremo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offers_id")
    private OffreEmpl offreEmpl;

    public Condidature(Long id, String nom, String email, String cv, String lettremo, OffreEmpl offreEmpl) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.cv = cv;
        this.lettremo = lettremo;
        this.offreEmpl = offreEmpl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCv() {
        return cv;
    }

    public void setCv(String cv) {
        this.cv = cv;
    }

    public String getLettremo() {
        return lettremo;
    }

    public void setLettremo(String lettremo) {
        this.lettremo = lettremo;
    }

    public OffreEmpl getOffreEmpl() {
        return offreEmpl;
    }

    public void setOffreEmpl(OffreEmpl offreEmpl) {
        this.offreEmpl = offreEmpl;
    }
}

