package com.SEBN.backend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "role_respos")

public class RoleRespo {

    @Id
    private String numBadge; // Clé primaire

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Clé étrangère vers User

    public RoleRespo(String numBadge, User user) {
        this.numBadge = numBadge;
        this.user = user;
    }

    public String getNumBadge() {
        return numBadge;
    }

    public void setNumBadge(String numBadge) {
        this.numBadge = numBadge;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
