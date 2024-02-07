package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.Tache;

import java.util.List;

public interface ITacheservice {
    public Tache ajout_tache(Tache tache );
    public Tache affich_tache(Long idtache);
    public List<Tache> affich_taches();
    public Tache update_tache(Tache tache,Long idtache);
    public void delete_tache(Long idtache);
    public Tache affecter_tache_dev(Long iduser,Long idtache);

    public Tache affecter_tache_projet(Long idtache,Long idprojet);
}
