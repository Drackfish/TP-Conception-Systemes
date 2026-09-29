package com.example.Contact.entities;

import java.io.Serializable;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

@Entity
public class Metier implements Serializable {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private String intitule; // Ex: Développeur, Designer, Manager...
    private String description;

    // Un métier regroupe plusieurs personnes
    @OneToMany(mappedBy = "metier", cascade = CascadeType.ALL)
    private List<Personne> personnes;

    public Metier() {
        super();
    }

    public Metier(String intitule, String description) {
        super();
        this.intitule = intitule;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Personne> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(List<Personne> personnes) {
        this.personnes = personnes;
    }
}