package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.Tache;

public interface ITacheservice {
    public Tache ajout_tache(Tache tache );
    public Tache affich_tache(Long idtache);
    public Tache update_tache(Tache tache,Long idtache);
    public void delete_tache(Long idtache);
}
