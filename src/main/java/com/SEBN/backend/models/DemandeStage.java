package com.SEBN.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "demands_stage")
public class DemandeStage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private String cv;
    private String duré;
    private String lettrem;


    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public DemandeStage(Long id, String nom, String email, String cv, String duré, String lettrem, User user) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.cv = cv;
        this.duré = duré;
        this.lettrem = lettrem;
        this.user = user;
    }

    public DemandeStage (){

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", cv='" + cv + '\'' +
                ", duré='" + duré + '\'' +
                ", lettrem='" + lettrem + '\'' +
                ", user=" + user +
                '}';
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

    public String getDuré() {
        return duré;
    }

    public void setDuré(String duré) {
        this.duré = duré;
    }

    public String getLettrem() {
        return lettrem;
    }

    public void setLettrem(String lettrem) {
        this.lettrem = lettrem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}