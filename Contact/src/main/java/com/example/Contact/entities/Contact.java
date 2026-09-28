package com.example.Contact.entities;
import java.io.Serializable;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;

@Entity
public class Contact implements Serializable{
    @Id
    private String email;
    
    private String nom;
    private int note;

    @ManyToOne
    @JoinColumn(name = "personne_id")
    private Personne personne;

    @Override
    public String toString() {
        return "Contacts [email=" + email + ", nom=" + nom + ", note=" + note + "]";
    }
    
    public Contact() {
        super();
    }

    public Contact(String email, String nom, int note, Personne personne) {
        super();
        this.email = email;
        this.nom = nom;
        this.note = note;
        this.personne = personne;
    }

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public int getNote() {
        return note;
    }
    
    public  void setEmail(String email){
        this.email = email;
    }

    public  void setNom(String nom){
        this.nom = nom;
    }

    public void setNote(int note) {
        this.note = note;
    }
}
