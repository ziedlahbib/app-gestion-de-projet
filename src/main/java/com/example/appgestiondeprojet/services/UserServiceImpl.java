package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.ERole;
import com.example.appgestiondeprojet.entity.Role;
import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.payload.request.SignupRequest;
import com.example.appgestiondeprojet.repository.RoleRepository;
import com.example.appgestiondeprojet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements IUserservice{
    @Autowired
    UserRepository userRepo;
    @Autowired
    RoleRepository roleRepository;
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
        return userRepo.save(u);
    }

    @Override
    public void deleteUser(Long idUser) {
        User u =userRepo.findById(idUser).orElse(null);
        u.setRoles(null);
        userRepo.deleteById(idUser);
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
