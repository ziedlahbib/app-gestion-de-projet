package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.Competence;
import com.example.appgestiondeprojet.entity.Projet;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICompetenceService {
    public Competence ajoutCompetence(Competence c);
    public ResponseEntity<?> delete_competnece(Long idcomp);
    public List<Competence> affich_competneces();
    public Competence affich_competnece(Long idcomp);

}
