package com.SEBN.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "stage")
public class Stage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom_stagaire;
    private String sujet;
    private String duré;
    private String encadrant;

    public Stage(Long id, String nom_stagaire, String sujet, String duré, String encadrant) {
        this.id = id;
        this.nom_stagaire = nom_stagaire;
        this.sujet = sujet;
        this.duré = duré;
        this.encadrant = encadrant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom_stagaire() {
        return nom_stagaire;
    }

    public void setNom_stagaire(String nom_stagaire) {
        this.nom_stagaire = nom_stagaire;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDuré() {
        return duré;
    }

    public void setDuré(String duré) {
        this.duré = duré;
    }

    public String getEncadrant() {
        return encadrant;
    }

    public void setEncadrant(String encadrant) {
        this.encadrant = encadrant;
    }
}
