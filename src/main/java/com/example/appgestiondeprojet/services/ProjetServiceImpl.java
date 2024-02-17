package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.Projet;
import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.repository.ProjetRepository;
import com.example.appgestiondeprojet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        p.setDate_limite(projet.getDate_limite());
        return projetrepo.save(p);
    }

    @Override
    public ResponseEntity<String> delete_projet(Long idprojet) {
        Optional<Projet> userOptional = projetrepo.findById(idprojet);
        if (userOptional.isPresent()) {
            projetrepo.deleteById(idprojet);
            return ResponseEntity.ok("Projet supprimé avec succès");
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
}
