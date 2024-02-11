package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.*;
import com.example.appgestiondeprojet.repository.ProjetRepository;
import com.example.appgestiondeprojet.repository.TAcheRepository;
import com.example.appgestiondeprojet.repository.UserRepository;
import com.example.appgestiondeprojet.repository.UserTacheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void delete_tache(Long idtache) {
    tacherepo.deleteById(idtache);
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
    public UserTache rate_user_tache(UserTache usertache,Long iduser, Long idTache) {
        User u =userrepo.findById(iduser).orElse(null);
        Tache t=tacherepo.findById(idTache).orElse(null);
        float r=u.getRating();
        float rn=(usertache.getRating()+r)/2;
        u.setRating(rn);
        return usertacherepo.save(usertache);
    }
}
