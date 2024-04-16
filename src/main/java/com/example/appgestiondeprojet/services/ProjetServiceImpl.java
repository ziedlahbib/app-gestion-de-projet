package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.Projet;
import com.example.appgestiondeprojet.entity.Tache;
import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.payload.response.MessageResponse;
import com.example.appgestiondeprojet.repository.ProjetRepository;
import com.example.appgestiondeprojet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjetServiceImpl implements  IProjetservice {
    @Autowired
    ProjetRepository projetrepo;
    @Autowired
    UserRepository userrepo;
    @Override
    public Projet ajout_projet(Projet projet) {
        return projetrepo.save(projet);
    }

    @Override
    public Projet affich_projet(Long idprojet) {
        return projetrepo.findById(idprojet).orElse(null);
    }

    @Override
    public List<Projet> affich_projets() {
        return projetrepo.findAll();
    }

    @Override
    public List<Projet> affich_projets_byUser(Long iduser) {
        return projetrepo.findBychefDeProjetId(iduser);
    }

    @Override
    public Projet update_projet(Projet projet, Long idprojet) {
        Projet p =projetrepo.findById(idprojet).orElse(null);
        p.setNom_projet(projet.getNom_projet());
        p.setStartDate(projet.getStartDate());
        p.setEndDate(projet.getEndDate());
        p.setColor(projet.getColor());
        return projetrepo.save(p);
    }

    @Override
    public ResponseEntity<?> delete_projet(Long idprojet) {
        Projet projet = projetrepo.findById(idprojet).orElse(null);
        if (projet!=null) {
            projet.setChefDeProjet(null);
            projetrepo.deleteById(idprojet);
            return ResponseEntity.ok(new MessageResponse("Projet supprimé avec succès"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projet non trouvé");

        }
    }

    @Override
    public Projet affecter_projet_cdp(Long iduser, Long idprojet) {
        User u =userrepo.findById(iduser).orElse(null);
        Projet p =projetrepo.findById(idprojet).orElse(null);
        p.setChefDeProjet(u);
        return projetrepo.save(p);
    }

    @Override
    public List<Projet> getprojectsbydevloppeur(Long iduser) {
        User u =userrepo.findById(iduser).orElse(null);
        List<Projet> lp =new ArrayList<>();
        if(u!=null){
            Set<Tache> lds =u.getTaches();
            for(Tache t:lds){
                lp.add(projetrepo.getProjectByTacheId(t.getId()));
            }
        }
        return lp;
    }
}
