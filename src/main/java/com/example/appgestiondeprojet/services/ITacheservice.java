package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.Tache;
import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.entity.UserCompetence;
import com.example.appgestiondeprojet.entity.UserTache;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ITacheservice {
    public Tache ajout_tache(Tache tache );
    public Tache affich_tache(Long idtache);
    public List<Tache> affich_tach_by_project(Long idproj);
    public List<Tache> affich_taches();
    public Tache update_tache(Tache tache,Long idtache);
    public ResponseEntity<?> delete_tache(Long idtache);
    public UserTache affecter_tache_dev(Long iduser,Long idtache);

    public Tache affecter_tache_projet(Long idtache,Long idprojet);
    public UserTache rate_user_tache(UserTache usertache, Long iduser, Long idTache);
    public void affecter_tacheCompetence ( Long idtache, Long idComp);
    public void desaffecter_tacheCompetence ( Long idtache, Long idComp);
}
