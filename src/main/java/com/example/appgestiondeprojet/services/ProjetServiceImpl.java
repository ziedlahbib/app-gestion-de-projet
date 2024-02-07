package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.Projet;
import com.example.appgestiondeprojet.repository.ProjetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetServiceImpl implements  IProjetservice {
    @Autowired
    ProjetRepository projetrepo;
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
    public Projet update_projet(Projet projet, Long idprojet) {
        Projet p =projetrepo.findById(idprojet).orElse(null);
        p.setNom_projet(projet.getNom_projet());
        p.setDate_limite(projet.getDate_limite());
        return projetrepo.save(p);
    }

    @Override
    public void delete_projet(Long idprojet) {
        projetrepo.deleteById(idprojet);
    }

    @Override
    public Projet affecter_projet_cdp(Long iduser, Long idprojet) {
        return null;
    }
}
