package com.example.appgestiondeprojet.controllers;

import com.example.appgestiondeprojet.entity.Competence;
import com.example.appgestiondeprojet.entity.Projet;
import com.example.appgestiondeprojet.services.CompetnceServiceImpl;
import com.example.appgestiondeprojet.services.ProjetServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*",exposedHeaders="Access-Control-Allow-Origin" )
@RestController
@RequestMapping("/competence")
public class CompetenceController {
    @Autowired
    CompetnceServiceImpl compserv;
    @PostMapping("/add-competence")
    @ResponseBody
    public Competence ajoutprojet(@RequestBody Competence c) {
        return compserv.ajoutCompetence(c);

    }
    @GetMapping("/get-competence/{id-competence}")
    @ResponseBody
    public Competence getprojetbyid(@PathVariable("id-competence") Long idcom) {
        return compserv.affich_competnece(idcom);

    }
    @GetMapping("/get-competences")
    @ResponseBody
    public List<Competence> getprojetbyid() {
        return compserv.affich_competneces();

    }
    @DeleteMapping("/delete-competence/{id-competence}")
    @ResponseBody
    public ResponseEntity<?> deleteprojet(@PathVariable("id-competence") Long idcomp) {
        return compserv.delete_competnece(idcomp);

    }
}
