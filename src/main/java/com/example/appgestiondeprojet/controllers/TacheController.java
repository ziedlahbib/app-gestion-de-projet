package com.example.appgestiondeprojet.controllers;

import com.example.appgestiondeprojet.entity.Tache;
import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.payload.request.SignupRequest;
import com.example.appgestiondeprojet.services.TacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",exposedHeaders="Access-Control-Allow-Origin" )
@RestController
@RequestMapping("/api/tache")
public class TacheController {
    @Autowired
    TacheServiceImpl tacheserv;
    @PostMapping ("/add-tache")
    @ResponseBody
    public Tache ajouttache(@RequestBody Tache t) {
        return tacheserv.ajout_tache(t);

    }
    @GetMapping("/get-tache/{id-tache}")
    @ResponseBody
    public Tache gettachebyid(@PathVariable("id-tache") Long idtache) {
        return tacheserv.affich_tache(idtache);

    }
    @PutMapping("/update-tache/{id-tache}")
    @ResponseBody
    public Tache upadateTache(@RequestBody Tache t, @PathVariable("id-tache") Long idtache) {
        return tacheserv.update_tache(t, idtache);

    }
    @DeleteMapping("/delete-tache/{id-tache}")
    @ResponseBody
    public void deletetache(@PathVariable("id-tache") Long idtache) {
        tacheserv.delete_tache(idtache);

    }
    @PutMapping("/affecter-tache-dev/{id-user}/{id-tache}")
    @ResponseBody
    public Tache affecterfileutilisateur(@PathVariable("id-user") Long iduser,@PathVariable("id-tache") Long idtache) {
        return tacheserv.affecter_tache_dev(iduser, idtache);

    }
}
