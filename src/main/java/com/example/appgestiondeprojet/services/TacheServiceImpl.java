package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.Tache;
import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.repository.TAcheRepository;
import com.example.appgestiondeprojet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TacheServiceImpl implements ITacheservice{
    @Autowired
    TAcheRepository tacherepo;
    @Autowired
    UserRepository userrepo;
    @Override
    public Tache ajout_tache(Tache tache) {
        return tacherepo.save(tache);
    }

    @Override
    public Tache affich_tache(Long idtache) {
        return tacherepo.findById(idtache).orElse(null);
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
    public Tache affecter_tache_dev(Long iduser, Long idtache) {
        User u =userrepo.findById(iduser).orElse(null);
        Tache t=tacherepo.findById(idtache).orElse(null);
        u.getTaches().add(t);
        return tacherepo.save(t);

    }
}
