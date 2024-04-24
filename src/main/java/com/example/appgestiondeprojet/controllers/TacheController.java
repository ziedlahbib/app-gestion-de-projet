package com.example.appgestiondeprojet.controllers;

import com.example.appgestiondeprojet.entity.*;
import com.example.appgestiondeprojet.payload.request.SignupRequest;
import com.example.appgestiondeprojet.services.TacheServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*",exposedHeaders="Access-Control-Allow-Origin" )
@RestController
@RequestMapping("/tache")
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
    @GetMapping("/get-tache-byprojet/{id-proj}")
    @ResponseBody
    public List<Tache> affichtachbypojet(@PathVariable("id-proj") Long idproj) {
        return tacheserv.affich_tach_by_project(idproj);

    }
    @GetMapping("/get-taches")
    @ResponseBody
    public List<Tache> gettaches() {
        return tacheserv.affich_taches();

    }
    @GetMapping("/get-tachesByUserId/{id-userid}")
    @ResponseBody
    public List<UserTache> gettachesbyUserId(@PathVariable("id-userid") Long iduser) {
        return tacheserv.affich_taches_byuserId(iduser);

    }
    @PutMapping("/update-tache/{id-tache}")
    @ResponseBody
    public Tache upadateTache(@RequestBody Tache t, @PathVariable("id-tache") Long idtache) {
        return tacheserv.update_tache(t, idtache);

    }
    @DeleteMapping("/delete-tache/{id-tache}")
    @ResponseBody
    public ResponseEntity<?> deletetache(@PathVariable("id-tache") Long idtache) {
        return tacheserv.delete_tache(idtache);

    }
    @PutMapping("/affecter-tache-dev/{id-user}/{id-tache}")
    @ResponseBody
    public UserTache affectertachedev(@PathVariable("id-user") Long iduser, @PathVariable("id-tache") Long idtache) {
        return tacheserv.affecter_tache_dev(iduser, idtache);

    }
    @PutMapping("/todo-tache-dev/{id-user}/{id-tache}")
    @ResponseBody
    public UserTache todotachedev(@PathVariable("id-user") Long iduser, @PathVariable("id-tache") Long idtache) {
        return tacheserv.todo_tache_dev(iduser, idtache);

    }
    @PutMapping("/desaffecter-tache-dev/{id-user}/{id-tache}")
    @ResponseBody
    public Tache desaffectertachedev(@PathVariable("id-user") Long iduser, @PathVariable("id-tache") Long idtache) {
        return tacheserv.desaffecter_tache_dev(idtache, iduser);

    }
    @PutMapping("/affecter-tache-projet/{id-projet}/{id-tache}")
    @ResponseBody
    public Tache affectertacheprojet(@PathVariable("id-projet") Long idprojet,@PathVariable("id-tache") Long idtache) {
        return tacheserv.affecter_tache_projet(idtache, idprojet);

    }

    @PutMapping("/rate-user-tache/{id-tache}/{id-user}")
    @ResponseBody
    public UserTache rateusertache(@RequestBody double rating, @PathVariable("id-user") Long iduser,@PathVariable("id-tache") Long idtache) {
        return tacheserv.rate_user_tache(rating,iduser,idtache);

    }
    @GetMapping("/rate-user-tache-number/{id-tache}/{id-user}")
    @ResponseBody
    public double rateusertachenumber( @PathVariable("id-user") Long iduser,@PathVariable("id-tache") Long idtache) {
        return tacheserv.rate_use_tache_number(iduser,idtache);

    }
    @PutMapping("/affecter-tache-compenence/{id-tache}/{id-comp}")
    @ResponseBody
    public void affecter_user_competence( @PathVariable("id-tache") Long idtache, @PathVariable("id-comp") Long idcomp) {
         tacheserv.affecter_tacheCompetence( idtache, idcomp);

    }

    @PutMapping("/desaffecter-tache-compenence/{id-tache}/{id-comp}")
    @ResponseBody
    public void desaffecter_user_competence(@PathVariable("id-tache") Long idtache, @PathVariable("id-comp") Long idcomp) {
         tacheserv.desaffecter_tacheCompetence(idtache, idcomp);
    }
    @GetMapping("/get-users-by-tache/{id-tache}")
    @ResponseBody
    public List<User> getuserbyid(@PathVariable("id-tache") Long idtache) {
        return tacheserv.getuserdetache(idtache);

    }
}
