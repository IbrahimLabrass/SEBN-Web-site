package com.SEBN.backend.models;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;



@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "nom"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    private String email;

    private String role;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private RoleRespo roleRespo;




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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public User() {
    }

    public User(Long id, String nom, String email, String role, Set<Role> roles, RoleRespo roleRespo) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.role = role;
        this.roles = roles;
        this.roleRespo = roleRespo;

    }
}
