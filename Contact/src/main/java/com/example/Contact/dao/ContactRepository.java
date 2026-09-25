package com.example.Contact.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Contact.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact,Long> {
    
}