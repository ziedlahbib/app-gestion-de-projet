package com.example.appgestiondeprojet.controllers;

import com.example.appgestiondeprojet.entity.Projet;
import com.example.appgestiondeprojet.entity.Tache;
import com.example.appgestiondeprojet.services.ProjetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*",exposedHeaders="Access-Control-Allow-Origin" )
@RestController
@RequestMapping("/projet")
public class ProjetController {
    @Autowired
    ProjetServiceImpl projetserv;
    @PostMapping("/add-projet")
    @ResponseBody
    public Projet ajoutprojet(@RequestBody Projet p) {
        return projetserv.ajout_projet(p);

    }
    @GetMapping("/get-projet/{id-projet}")
    @ResponseBody
    public Projet getprojetbyid(@PathVariable("id-projet") Long idprojet) {
        return projetserv.affich_projet(idprojet);

    }
    @GetMapping("/get-projets")
    @ResponseBody
    public List<Projet> getprojetbyid() {
        return projetserv.affich_projets();

    }
    @PutMapping("/update-projet/{id-projet}")
    @ResponseBody
    public Projet upadateprojet(@RequestBody Projet t, @PathVariable("id-projet") Long idprojet) {
        return projetserv.update_projet(t, idprojet);

    }
    @DeleteMapping("/delete-projet/{id-projet}")
    @ResponseBody
    public void deleteprojet(@PathVariable("id-projet") Long idprojet) {
        projetserv.delete_projet(idprojet);

    }
}
