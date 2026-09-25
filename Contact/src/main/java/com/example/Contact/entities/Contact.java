package com.example.Contact.entities;
import java.io.Serializable;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

@Entity
public class Contact implements Serializable{
    @Id @GeneratedValue
    private long id;

    @Override
    public String toString() {
        return "Contacts [id=" + id + ", nom=" + nom + ", email=" + "]";
    }

    private String nom;
    private String email;
    private int note;

    public String getEmail() {
        return email;
    }

    public String getNom() {
        return nom;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public  void setNom(String nom){
        this.nom = nom;
    }
    
    public  void setEmail(String email){
        this.email = email;
    }

    public Contact() {
        super();
    }

    public Contact(String nom, String email, int note) {
        super();
        this.nom = nom;
        this.email = email;
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    //getters et setters
}
