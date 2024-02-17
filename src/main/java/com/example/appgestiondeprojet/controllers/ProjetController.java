package com.example.appgestiondeprojet.controllers;

import com.example.appgestiondeprojet.entity.Projet;
import com.example.appgestiondeprojet.entity.Tache;
import com.example.appgestiondeprojet.services.ProjetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping("/get-projet-buuser/{id-user}")
    @ResponseBody
    public List<Projet> upadateprojet( @PathVariable("id-user") Long iduser) {
        return projetserv.affich_projets_byUser( iduser);

    }
    @DeleteMapping("/delete-projet/{id-projet}")
    @ResponseBody
    public ResponseEntity<String> deleteprojet(@PathVariable("id-projet") Long idprojet) {
       return projetserv.delete_projet(idprojet);

    }
    @PutMapping("/affecter-projet-cdp/{id-user}/{id-projet}")
    @ResponseBody
    public Projet affectertacheprojet(@PathVariable("id-user") Long iduser,@PathVariable("id-projet") Long idprojet) {
        return projetserv.affecter_projet_cdp(iduser, idprojet);

    }
}
