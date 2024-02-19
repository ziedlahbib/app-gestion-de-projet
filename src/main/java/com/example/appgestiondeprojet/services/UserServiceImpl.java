package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.*;
import com.example.appgestiondeprojet.payload.request.SignupRequest;
import com.example.appgestiondeprojet.payload.response.MessageResponse;
import com.example.appgestiondeprojet.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements IUserservice{
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
    @Override
    public User resetpassword(User user) {
        return userRepo.save(user);
    }

    @Override
    public User updateUser(SignupRequest signUpRequest, Long idUser) {
        User u = userRepo.findById(idUser).orElse(null);
        u.setEmail(signUpRequest.getEmail());
        u.setNom(signUpRequest.getNom());
        u.setPrenom(signUpRequest.getPrenom());
        u.setUsername(signUpRequest.getUsername());
        String strRoles = signUpRequest.getRole();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_DEVELOPPEUR)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            u.setRoles(userRole);
        } else {
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
                default:
                    Role ROLE_DEVELOPPEUR = roleRepository.findByName(ERole.ROLE_DEVELOPPEUR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    u.setRoles(ROLE_DEVELOPPEUR);;
            }
        }
        UserCompetence usercomp =usercomprrpo.findByUserId(u.getId());
        Competence competence = usercomp.getCompetence();
        competence.setTechnologies(signUpRequest.getCompetence());
        comprrpo.save(competence);
        usercomp.setUser(u);
        usercomp.setCompetence(competence);
        usercomp.setLvl(signUpRequest.getLvl()); // Définir le niveau de compétence si nécessaire

        // Ajouter UserCompetence à la liste userCompetences de l'utilisateur

        u.getUserCompetences().add(usercomp);
        usercomprrpo.save(usercomp);
        return userRepo.save(u);
    }

    @Override
    public User updateProfile(SignupRequest signUpRequest, Long idUser) {
        return null;
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
