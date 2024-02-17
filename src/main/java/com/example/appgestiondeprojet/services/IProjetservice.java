package com.example.appgestiondeprojet.services;


import com.example.appgestiondeprojet.entity.Projet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProjetservice {
    public Projet ajout_projet(Projet projet );
    public Projet affich_projet(Long idprojet);
    public List<Projet> affich_projets();
    public Projet update_projet(Projet projet,Long idprojet);
    public ResponseEntity<String> delete_projet(Long idprojet);
    public Projet affecter_projet_cdp(Long iduser,Long idprojet);
}
