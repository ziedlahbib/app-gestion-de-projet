package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.*;
import com.example.appgestiondeprojet.repository.ProjetRepository;
import com.example.appgestiondeprojet.repository.TAcheRepository;
import com.example.appgestiondeprojet.repository.UserRepository;
import com.example.appgestiondeprojet.repository.UserTacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TacheServiceImpl implements ITacheservice{
    @Autowired
    TAcheRepository tacherepo;
    @Autowired
    UserRepository userrepo;
    @Autowired
    ProjetRepository projetrepo;
    @Autowired
    UserTacheRepository usertacherepo;
    @Override
    public Tache ajout_tache(Tache tache) {
        return tacherepo.save(tache);
    }

    @Override
    public Tache affich_tache(Long idtache) {
        return tacherepo.findById(idtache).orElse(null);
    }

    @Override
    public List<Tache> affich_taches() {
        return tacherepo.findAll();
    }

    @Override
    public Tache update_tache(Tache tache,Long idtache) {
        Tache t=tacherepo.findById(idtache).orElse(null);
        t.setDescription(tache.getDescription());
        t.setDate_debut(tache.getDate_debut());
        t.setDate_fin(tache.getDate_fin());
        return tacherepo.save(t);
    }

    @Override
    public ResponseEntity<String> delete_tache(Long idtache) {


        Optional<Tache> userOptional = tacherepo.findById(idtache);
        if (userOptional.isPresent()) {
            tacherepo.deleteById(idtache);
            return ResponseEntity.ok("Tache supprimé avec succès");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tache non trouvé");
        }
    }

    @Override
    public UserTache affecter_tache_dev(Long iduser, Long idtache) {
        User u =userrepo.findById(iduser).orElse(null);
        Tache t=tacherepo.findById(idtache).orElse(null);
        // Create an instance of UserTache
        UserTacheId userTacheId = new UserTacheId();

        // Set user and tache IDs in the composite primary key
        userTacheId.setUserId(u.getId());
        userTacheId.setTacheId(t.getId());

        // Create an instance of UserTache with the composite primary key
        UserTache userTache = new UserTache();
        userTache.setId(userTacheId);
        return usertacherepo.save(userTache);

    }

    @Override
    public Tache affecter_tache_projet(Long idtache, Long idprojet) {
        Tache t=tacherepo.findById(idtache).orElse(null);
        Projet p =projetrepo.findById(idprojet).orElse(null);
        p.getTaches().add(t);
        return tacherepo.save(t);

    }

    @Override
    public UserTache rate_user_tache(UserTache ut,Long iduser, Long idTache) {
        // Find the user and tache entities
        User u = userrepo.findById(iduser).orElse(null);
        List<UserTache> lut=new ArrayList<>();
        System.out.println(""+lut.size());
        // Find the UserTache entity based on the composite key
        UserTacheId userTacheId = new UserTacheId(iduser, idTache);
        UserTache userTache = usertacherepo.findById(userTacheId).orElse(null);
        userTache.setRating(ut.getRating());
        usertacherepo.save(userTache);
        for(Tache t:u.getTaches()){
            UserTacheId userTId = new UserTacheId(u.getId(), t.getId());
            lut.add(usertacherepo.findById(userTId).orElse(null));
        }
        double sum = 0;
        int count = 0;
        for (UserTache ust : lut) {
            sum += ust.getRating();
            count++;
        }
        double averageRating = sum / count;
        u.setRating(averageRating);
        userrepo.save(u);
        return userTache;
    }
}
