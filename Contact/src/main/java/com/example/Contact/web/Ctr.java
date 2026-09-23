package com.example.Contact.web;
import java.util.Optional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class Ctr {
    
    @Autowired
    ContactRepository contactRepository;
    
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/contacts")
    public String contacts(Model model) throws InterruptedException {
        List<Contact> contacts=contacRepository.findAll();
        model.addAttribute("contacts", contacts);
        return "liste"
    }

    @RequestMapping("/save")
    public String save(@RequestParam String nom, @RequestParam String email, @RequestParam String note, Model model) throw InterException {
        contactRepository.save(new Contact(nom, email, Integer.parseInt(note)));
        return contacts(model);
    }

    @RequestMapping("/ajout")
    public String ajout() {
        return "add";
    }

    @RequestMapping("/delete")
    public String delete() {
        return "delete";
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
