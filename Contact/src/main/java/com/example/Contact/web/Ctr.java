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
        model.addAttribute("personnes", personneRepository.findAll())
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
    @RequestMapping("/modify")
    public String modify(@RequestParam Long id, Model model) throws InterruptedException {
        Optional<Contact> contact=contactRepository.findById(id);
        if (contact.isPresent()){
             return modifyForm(contact.get(),model);
        }
        else{
            return "index";
        }
    }

    // A modif
    @RequestMapping("/doModify")
    public String doModify(@RequestParam Long id,@RequestParam String nom,@RequestParam String email,@RequestParam int note,Model model) throws InterruptedException {
        Optional<Contact> contact=contactRepository.findById(id);
        if (contact.isPresent()){
            Contact c = contact.get();
            c.setEmail(email);
            c.setNom(nom);
            c.setNote(note);
            contactRepository.save(c);
        }
        
        return "redirect:/contacts";
    }
    
    // A modif
    @RequestMapping("/delete")
    public String delete() {
        return "delete";
    }

    @RequestMapping("/modifyForm")
    public String modifyForm(Contact contact, Model model) throws InterruptedException {
            model.addAttribute("contact", contact);
            return "modifyForm";
        
    }

    @RequestMapping("/ajout")
    public String ajout() {
        return "add";
    }

    @RequestMapping("/deleteSave")
    public String deleteSave(@RequestParam Long id) {
        Optional<Contact> contact=contactRepository.findById(id);
        if (contact.isPresent()){
            contactRepository.deleteById(id);
        }
        return "delete";
    }

}
