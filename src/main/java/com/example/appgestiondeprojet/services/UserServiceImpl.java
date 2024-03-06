package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.*;
import com.example.appgestiondeprojet.payload.request.SignupRequest;
import com.example.appgestiondeprojet.payload.response.MessageResponse;
import com.example.appgestiondeprojet.payload.response.UpdateProfileResponse;
import com.example.appgestiondeprojet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements IUserservice {
    @Autowired
    UserRepository userRepo;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private ProjetRepository projectRepository;
    @Autowired
    TAcheRepository tacherepo;
    @Autowired
    UserCompetenceReposirory usercomprrpo;
    @Autowired
    CompetenceRepository comprrpo;
    @Autowired
    UserCompetenceReposirory usercomprepo;
    @Override
    public User resetpassword(User user) {
        return userRepo.save(user);
    }

    @Override
    public ResponseEntity<UpdateProfileResponse> updateUser(SignupRequest signUpRequest, Long idUser) {
        User u = userRepo.findById(idUser).orElse(null);
        if (u != null) {
        u.setEmail(signUpRequest.getEmail());
        u.setNom(signUpRequest.getNom());
        u.setPrenom(signUpRequest.getPrenom());
        u.setUsername(signUpRequest.getUsername());
        String strRoles = signUpRequest.getRole();

            switch (strRoles) {
                case "responsable":
                    Role responsableRole = roleRepository.findByName(ERole.ROLE_RESPONSABLE)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    u.setRoles(responsableRole);

                    break;
                case "chef de projet":
                    Role ROLE_CHEF_DE_PROJET = roleRepository.findByName(ERole.ROLE_CHEF_DE_PROJET)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    u.setRoles(ROLE_CHEF_DE_PROJET);

                    break;
                case "superadmin":
                    Role superadminrole = roleRepository.findByName(ERole.ROLE_SUPERADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    u.setRoles(superadminrole);

                    break;
                default:
                    Role ROLE_DEVELOPPEUR = roleRepository.findByName(ERole.ROLE_DEVELOPPEUR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    u.setRoles(ROLE_DEVELOPPEUR);

            }

        UserCompetence usercomp = u.getUserCompetences();
        if(usercomp!=null){
            Competence competence = usercomp.getCompetence();
            competence.setTechnologies(signUpRequest.getCompetence());
            comprrpo.save(competence);
            usercomp.setUser(u);
            usercomp.setCompetence(competence);
            usercomp.setLvl(signUpRequest.getLvl()); // Définir le niveau de compétence si nécessaire

            // Ajouter UserCompetence à la liste userCompetences de l'utilisateur

            u.setUserCompetences(usercomp);
            usercomprrpo.save(usercomp);
        }else{
            UserCompetence usercomp2=new UserCompetence();
            Competence competence = new Competence();
            competence.setTechnologies(signUpRequest.getCompetence());
            comprrpo.save(competence);
            usercomp2.setUser(u);
            usercomp2.setCompetence(competence);
            usercomp2.setLvl(signUpRequest.getLvl()); // Définir le niveau de compétence si nécessaire

            // Ajouter UserCompetence à la liste userCompetences de l'utilisateur

            u.setUserCompetences(usercomp2);
            usercomprrpo.save(usercomp2);
        }

        userRepo.save(u);
        UpdateProfileResponse response = new UpdateProfileResponse("Modifié avec succès", u);
        return ResponseEntity.ok().body(response);
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UpdateProfileResponse("Utilisateur non trouvé", null));

    }
    }

    @Override
    public ResponseEntity<UpdateProfileResponse> updateProfile(SignupRequest signUpRequest, Long idUser) {
        User u = userRepo.findById(idUser).orElse(null);
        if (u != null) {
            u.setNom(signUpRequest.getNom());
            u.setPrenom(signUpRequest.getPrenom());
            u.setEmail(signUpRequest.getEmail());
            u.setUsername(signUpRequest.getUsername());
            userRepo.save(u);
            UpdateProfileResponse response = new UpdateProfileResponse("Modifié avec succès", u);
            return ResponseEntity.ok().body(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new UpdateProfileResponse("Utilisateur non trouvé", null));

        }
    }


    @Override
    public ResponseEntity<?> deleteUser(Long idUser) {
        User u =userRepo.findById(idUser).orElse(null);
        Optional<User> userOptional = userRepo.findById(idUser);
        if (userOptional.isPresent()) {
            u.setRoles(null);
            projectRepository.findBychefDeProjetId(idUser).forEach(project -> {
                project.setChefDeProjet(null);
                projectRepository.save(project);});
            tacherepo.findByUserId(idUser).forEach(tache -> {
                tache.setUser(null);
                tacherepo.save(tache);});

            UserCompetence c= usercomprepo.findByUserId(idUser);
            c.setCompetence(null);
            c.setUser(null);
            System.out.println("id"+c.getId());
            usercomprepo.deleteById(c.getId());
            userRepo.deleteById(idUser);
            return ResponseEntity.ok(new MessageResponse("Utilisateur supprimé avec succès"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur non trouvé");
        }
    }

    @Override
    public User affichDetailUser(Long idUser) {
        return userRepo.findById(idUser).orElse(null);
    }

    @Override
    public List<User> affichUser() {
        return userRepo.findAll();
    }

    @Override
    public Optional<User> findbyusername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Optional<User> findUserByResetToken(String resetToken) {
        return userRepo.findByResetToken(resetToken);
    }

    @Override
    public Boolean exitbyusernape(String username) {
        return userRepo.existsByUsername((username));
    }

    @Override
    public Boolean existbyemail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public void activer(Long iduser) {
        User u = userRepo.findById(iduser).orElse(null);
        u.setActive(true);
        userRepo.save(u);

    }

    @Override
    public void desactiver(Long iduser) {
        User u = userRepo.findById(iduser).orElse(null);
        u.setActive(false);
        userRepo.save(u);

    }
}
