package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.Competence;
import com.example.appgestiondeprojet.entity.Projet;
import com.example.appgestiondeprojet.payload.response.MessageResponse;
import com.example.appgestiondeprojet.repository.CompetenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetnceServiceImpl implements ICompetenceService {
    @Autowired
    CompetenceRepository comprepo;
    @Override
    public Competence ajoutCompetence(Competence c) {
        return comprepo.save(c);
    }

    @Override
    public ResponseEntity<?> delete_competnece(Long idcomp) {
        Competence c = comprepo.findById(idcomp).orElse(null);
        if (c!=null) {
            comprepo.deleteById(idcomp);
            return ResponseEntity.ok(new MessageResponse("Projet supprimé avec succès"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Projet non trouvé");

        }
    }

    @Override
    public List<Competence> affich_competneces() {
        return comprepo.findAll();
    }

    @Override
    public Competence affich_competnece(Long idcomp) {
        return comprepo.findById(idcomp).orElse(null);
    }
}
