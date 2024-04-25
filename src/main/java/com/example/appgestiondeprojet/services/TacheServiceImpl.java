package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.*;
import com.example.appgestiondeprojet.payload.response.MessageResponse;
import com.example.appgestiondeprojet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class TacheServiceImpl implements ITacheservice{
    @Autowired
    TAcheRepository tacherepo;
    @Autowired
    UserRepository userrepo;
    @Autowired
    ProjetRepository projetrepo;
    @Autowired
    UserTacheRepository usertacherepo;
    @Autowired
    CompetenceRepository comprrpo;
    @Override
    public Tache ajout_tache(Tache tache) {
        return tacherepo.save(tache);
    }

    @Override
    public Tache affich_tache(Long idtache) {
        return tacherepo.findById(idtache).orElse(null);
    }

    @Override
    public List<Tache> affich_tach_by_project(Long idproj) {
        Projet p =projetrepo.findById(idproj).orElse(null);
        return p.getTaches();
    }

    @Override
    public List<Tache> affich_taches() {
        return tacherepo.findAll();
    }

    @Override
    public List<UserTache> affich_taches_byuserId(Long userId) {
        return usertacherepo.findAllByUserId(userId);
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
    public ResponseEntity<?> delete_tache(Long idtache) {
        Tache tache = tacherepo.findById(idtache).orElse(null);
        if (tache != null) {
            tache.setUser(null);

            // Create an iterator to safely remove elements
            Iterator<Competence> iterator = tache.getCompetences().iterator();
            while (iterator.hasNext()) {
                Competence c = iterator.next();
                System.out.println("cid" + c.getId());
                iterator.remove(); // Remove the element using iterator
            }

            Projet p = projetrepo.getProjectByTacheId(idtache);
            Iterator<Tache> iteratort = p.getTaches().iterator();
            while (iteratort.hasNext()) {
                Tache t = iteratort.next();
                if (t.getId() == idtache) {
                    iteratort.remove(); // Remove the element using iterator
                    projetrepo.save(p);
                    break; // Exit the loop since the element is removed
                }
            }


            tacherepo.deleteById(idtache);
            return ResponseEntity.ok(new MessageResponse("tache supprimé avec succès"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tache non trouvé");
        }
    }

    @Override
    public void voirnotf(Long iduser, Long idtache) {
        User u = userrepo.findById(iduser).orElse(null);
        Tache t = tacherepo.findById(idtache).orElse(null);

        // Check if the association already exists
        UserTache existingUserTache = usertacherepo.findById(new UserTacheId(u.getId(), t.getId())).orElse(null);

            // Association already exists, return the existing entry
            existingUserTache.setEtat("lu");
            usertacherepo.save(existingUserTache);
    }

    @Override
    public UserTache todo_tache_dev(Long iduser, Long idtache) {
        User u = userrepo.findById(iduser).orElse(null);
        Tache t = tacherepo.findById(idtache).orElse(null);

        // Check if the association already exists
        UserTache existingUserTache = usertacherepo.findById(new UserTacheId(u.getId(), t.getId())).orElse(null);
        if (existingUserTache != null) {
            // Association already exists, return the existing entry
            existingUserTache.setStatus("à faire");
            existingUserTache.setEtat("non lu");
            return usertacherepo.save(existingUserTache);
        }

        // Create an instance of UserTache
        UserTacheId userTacheId = new UserTacheId();

        // Set user and tache IDs in the composite primary key
        userTacheId.setUserId(u.getId());
        userTacheId.setTacheId(t.getId());

        // Create an instance of UserTache with the composite primary key
        UserTache userTache = new UserTache();
        userTache.setId(userTacheId);
        userTache.setStatus("à faire");
        userTache.setEtat("non lu");
        // Save the new association
        return usertacherepo.save(userTache);
    }


    @Override
    public UserTache affecter_tache_dev(Long iduser, Long idtache) {
        User u = userrepo.findById(iduser).orElse(null);
        Tache t = tacherepo.findById(idtache).orElse(null);

        // Check if the association already exists
        UserTache existingUserTache = usertacherepo.findById(new UserTacheId(u.getId(), t.getId())).orElse(null);
        if (existingUserTache != null) {
            // Association already exists, return the existing entry
            existingUserTache.setStatus("en cours");
            existingUserTache.setEtat("non lu");
            u.setStatus("non disponible");
            return usertacherepo.save(existingUserTache);
        }

        // Create an instance of UserTache
        UserTacheId userTacheId = new UserTacheId();

        // Set user and tache IDs in the composite primary key
        userTacheId.setUserId(u.getId());
        userTacheId.setTacheId(t.getId());

        // Create an instance of UserTache with the composite primary key
        UserTache userTache = new UserTache();
        userTache.setId(userTacheId);
        userTache.setStatus("en cours");
        userTache.setEtat("non lu");
        u.setStatus("non disponible");
        // Save the new association
        return usertacherepo.save(userTache);
    }

    @Override
    public Tache desaffecter_tache_dev(Long idtache, Long iduser) {
        User u = userrepo.findById(iduser).orElse(null);
        Tache t = tacherepo.findById(idtache).orElse(null);
        if(t.getUser()!=null){

            // Check if the association already exists
            UserTache existingUserTache = usertacherepo.findById(new UserTacheId(u.getId(), t.getId())).orElse(null);
            existingUserTache.setStatus("terminer");
            u.setStatus("disponible");
            usertacherepo.save(existingUserTache);
        }
        return t;
    }


    @Override
    public Tache affecter_tache_projet(Long idtache, Long idprojet) {
        Tache t=tacherepo.findById(idtache).orElse(null);
        Projet p =projetrepo.findById(idprojet).orElse(null);
        p.getTaches().add(t);
        return tacherepo.save(t);

    }

    @Override
    public Tache desaffecter_tache_projet(Long idtache, Long idprojet) {
        return null;
    }

    @Override
    public UserTache rate_user_tache(double newRating,Long idUser, Long idTache) {
        // Find the UserTache entity based on the composite key
        UserTacheId userTacheId = new UserTacheId(idUser, idTache);
        UserTache userTache = usertacherepo.findById(userTacheId).orElse(null);

        if (userTache != null) {
            // Update the rating of the UserTache entity
            userTache.setRating(newRating);
            usertacherepo.save(userTache);

            // Retrieve the User entity
            User user = userrepo.findById(idUser).orElse(null);
            if (user != null) {
                List<UserTache> userTaches = new ArrayList<>();
                double sum = 0;

                // Get all UserTache entities associated with the User
                for (Tache tache : user.getTaches()) {
                    UserTacheId userTId = new UserTacheId(user.getId(), tache.getId());
                    UserTache ut = usertacherepo.findById(userTId).orElse(null);

                    if (ut != null) {
                        userTaches.add(ut);
                        sum += ut.getRating();
                    }
                }

                // Calculate the average rating
                double averageRating = userTaches.isEmpty() ? 0 : sum / userTaches.size();

                // Update the User entity with the average rating
                user.setRating(averageRating);
                userrepo.save(user);

                return userTache;
            }
        }

        return null;
    }
    @Override
    public double rate_use_tache_number(Long iduser, Long idTache) {
        UserTacheId userTacheId = new UserTacheId(iduser, idTache);
        System.out.println("UserTacheId: " + userTacheId.toString());

        UserTache userTache = usertacherepo.findById(userTacheId).orElse(null);
        if (userTache != null) {
            System.out.println("Rating found: " + userTache.getRating());
            return userTache.getRating();
        } else {
            System.out.println("No rating found for UserTacheId: " + userTacheId.toString());
            return 0;
        }
    }

    @Override
    public void affecter_tacheCompetence(Long idtache, Long idComp) {
        Tache t=tacherepo.findById(idtache).orElse(null);
        Competence c =comprrpo.findById(idComp).orElse(null);
        if (t != null) {
            t.getCompetences().add(c);
            tacherepo.save(t);
        }
    }

    @Override
    public void desaffecter_tacheCompetence(Long idtache, Long idComp) {
        Tache t=tacherepo.findById(idtache).orElse(null);
        Competence c =comprrpo.findById(idComp).orElse(null);
        if (t != null) {
            t.getCompetences().remove(c);
            tacherepo.save(t);
        }
    }

    @Override
    public List<User> getuserdetache(Long idtache) {
        return tacherepo.userdetache(idtache);
    }
}
