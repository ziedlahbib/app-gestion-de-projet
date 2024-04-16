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

import java.util.ArrayList;
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
            if(c!=null){
                c.setCompetence(null);
                c.setUser(null);
                System.out.println("id"+c.getId());
                usercomprepo.deleteById(c.getId());
            }

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

    @Override
    public User affecter_userCompetence(UserCompetence userc,Long idUser, Long idComp) {
        User user =userRepo.findById(idUser).orElse(null);
        Competence c =comprrpo.findById(idComp).orElse(null);

            UserCompetence userCompetence = new UserCompetence();
            userCompetence.setUser(user);
            userCompetence.setCompetence(c);
            userCompetence.setLvl(userc.getLvl());
            usercomprepo.save(userCompetence);
            return user;

    }

    @Override
    public User desaffecter_userCompetence( Long idUser, Long idComp) {
        User user =userRepo.findById(idUser).orElse(null);
        Competence c =comprrpo.findById(idComp).orElse(null);
        UserCompetence userCompetence = usercomprepo.findByUserIdAndCompetenceId(user.getId(),c.getId());
        usercomprepo.deleteById(userCompetence.getId());
        return user;
    }

    @Override
    public List<User> affichcdp() {
        List<User> lcdp =new ArrayList<User>();
        for(User u :this.affichUser()){
            if(u.getRoles().getName().equals(ERole.ROLE_CHEF_DE_PROJET)){
                lcdp.add(u);
            }
        }
        return lcdp;
    }
}
