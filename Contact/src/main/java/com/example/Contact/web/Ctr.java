package com.example.Contact.web;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Contact.dao.ContactRepository;
import com.example.Contact.dao.PersonneRepository;
import com.example.Contact.entities.Contact;
import com.example.Contact.entities.Personne;

@Controller
public class Ctr {
    
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    PersonneRepository personneRepository;
    
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("personnes", personneRepository.findAll());
        return "index";
    }

    @RequestMapping("/contacts")
    public String contacts(
            @RequestParam(name= "noteExacte", required = false) Integer noteExact,
            @RequestParam(name= "noteMax", required = false) Integer noteMax,
            Model model) throws InterruptedException {

        List<Contact> contacts;

        if (noteExact != null){
            //Recherche afficher en fonction de la note
            contacts = contactRepository.findByNote(noteExact);
        }
        else if (noteMax != null) {
            contacts = contactRepository.findByNoteLessThanEqual(noteMax);
        }
        else {
            contacts = contactRepository.findAll();
        }

        model.addAttribute("contacts", contacts);
        return "list";
    }

    @RequestMapping("/ajoutContactForm")
    public String ajoutContactForm(Model model) {
        model.addAttribute("personnes", personneRepository.findAll());
        return "addContact"; // ou le nom de votre page HTML de formulaire
    }

    @RequestMapping ("/saveContacts")
    public String saveContacts(@RequestParam String email, @RequestParam String nom, @RequestParam int note, @RequestParam Long personneId) {
        Optional<Personne> personneOpt = personneRepository.findById(personneId);
        if (personneOpt.isPresent()) {
            Contact contact = new Contact(email, nom, note, personneOpt.get());
            contactRepository.save(contact);
        }
        return "redirect:/contacts";
    }

    //@RequestMapping("/save")
    //public String save(@RequestParam String nom, @RequestParam String email, @RequestParam String note, Model model) throws InterruptedException {
    //contactRepository.save(new Contact(nom, email, Integer.parseInt(note)));
    //    return "redirect:/contacts";
    //}

    // A modif
    @RequestMapping("/modifyContact")
    public String modifyContact(@RequestParam String email, Model model) {
        Optional<Contact> contact = contactRepository.findById(email);
        if (contact.isPresent()){
            model.addAttribute("contact", contact.get());
            model.addAttribute("personnes", personneRepository.findAll());
            return "modifyContactForm";
        }
        return "redirect:/contacts";
    }

    // A modif
    @RequestMapping("/doModifyContact")
    public String doModifyContact(@RequestParam String email, @RequestParam String nom, @RequestParam int note, @RequestParam Long personneId) {
        Optional<Contact> contactOpt = contactRepository.findById(email);
        Optional<Personne> personneOpt = personneRepository.findById(personneId);
        
        if (contactOpt.isPresent() && personneOpt.isPresent()){
            Contact c = contactOpt.get();
            c.setNom(nom);
            c.setNote(note);
            c.setPersonne(personneOpt.get());
            contactRepository.save(c);
        }
        return "redirect:/contacts";
    }
    
    // A modif
    @RequestMapping("/deleteContact")
    public String deleteContact() {
        return "delete";
    }

    /*
    @RequestMapping("/modifyForm")
    public String modifyForm(Contact contact, Model model) throws InterruptedException {
            model.addAttribute("contact", contact);
            return "modifyForm";
        
    }
    */

    @RequestMapping("/personnes")
    public String personnes(Model model) {
        model.addAttribute("personnes", personneRepository.findAll());
        return "personnesList";
    }

    @RequestMapping("/savePersonne")
    public String savePersonne(@RequestParam String nom, @RequestParam String prenom,@RequestParam String adresse) {
        personneRepository.save(new Personne(nom, prenom, adresse));
        return "redirect:/personnes";
    }

    @RequestMapping("/deletePersonne")
    public String deletePersonne(@RequestParam Long id) {
        personneRepository.deleteById(id);
        return "redirect:/personnes";
    }

    // 1. Affichage des informations de la personne possédant un contact donné
    @RequestMapping("/personneByContact")
    public String personneByContact(@RequestParam String email, Model model) {
        Optional<Contact> contact = contactRepository.findById(email);
        if (contact.isPresent() && contact.get().getPersonne() != null) {
            model.addAttribute("personneTrouvee", contact.get().getPersonne());
        }
        model.addAttribute("contacts", contactRepository.findAll());
        return "list"; // Ou une page dédiée
    }

    // 2. Recherche des personnes ne possédant aucun contact
    @RequestMapping("/personnesSansContact")
    public String personnesSansContact(Model model) {
        List<Personne> personnes = personneRepository.findByContactsIsEmpty();
        model.addAttribute("personnes", personnes);
        return "personnesList";
    }

    // 3. Recherche de la personne ayant la moyenne des notes de ses contacts la plus élevée
    @RequestMapping("/meilleureMoyenne")
    public String meilleureMoyenne(Model model) {
        List<Personne> result = personneRepository.findPersonneWithHighestAverageContactNote();
        if (!result.isEmpty()) {
            model.addAttribute("meilleurePersonne", result.get(0));
        }
        model.addAttribute("personnes", personneRepository.findAll());
        return "personnesList";
    }

    @RequestMapping("/ajoutContact")
    public String ajoutContact(Model model) {
        model.addAttribute("personnes", personneRepository.findAll());
        return "add";
    }

    @RequestMapping("/deleteSave")
    public String deleteSave(@RequestParam String email) {
        Optional<Contact> contact = contactRepository.findById(email);
        if (contact.isPresent()){
            contactRepository.deleteById(email);
        }
        return "list";
    }

}
