package com.example.Contact.dao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.Contact.entities.Personne;

@Repository 
public interface PersonneRepository extends JpaRepository<Personne, Long>{
    List<Personne> findByContactsIsEmpty();

    @Query ("SELECT p FROM Personne p JOIN p.contacts c GROUP BY p ORDER BY AVG(c.note) DESC")
    List<Personne> findPersonneWithHighestAverageContactNote();
}
