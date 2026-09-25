package com.example.Contact.dao;
import java.util.List;
import com.example.Contact.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    //Recherche par note
    List<Contact> findByNote(int note);

    //Recherche de contact avec note max
    List<Contact> findByNoteLessThanEqual(int note);

    //Recherche par nom
    List<Contact> findByNom(String nom);
}