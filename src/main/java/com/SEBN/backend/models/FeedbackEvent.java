package com.SEBN.backend.models;


import jakarta.persistence.*;

@Entity
@Table(name = "feedbacks_event")
public class FeedbackEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_id")
    private Event event;

    private String commentaire;
    private int note; // Peut être utilisé

    public FeedbackEvent(Long id, Event event, String commentaire, int note) {
        this.id = id;
        this.event = event;
        this.commentaire = commentaire;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    // Constructeurs, getters et setters

}

