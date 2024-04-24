package com.example.appgestiondeprojet.controllers;

import com.example.appgestiondeprojet.entity.*;
import com.example.appgestiondeprojet.repository.CompetenceRepository;
import com.example.appgestiondeprojet.repository.UserCompetenceReposirory;
import com.example.appgestiondeprojet.services.EmailService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.appgestiondeprojet.payload.request.LoginRequest;
import com.example.appgestiondeprojet.payload.request.SignupRequest;
import com.example.appgestiondeprojet.payload.response.MessageResponse;
import com.example.appgestiondeprojet.payload.response.UserInfoResponse;
import com.example.appgestiondeprojet.repository.RoleRepository;
import com.example.appgestiondeprojet.repository.UserRepository;
import com.example.appgestiondeprojet.services.UserDetailsImpl;
import com.example.appgestiondeprojet.jwt.JwtUtils;

import java.util.List;
// other imports here

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  AuthenticationManager authenticationManager;


  @Autowired
  UserRepository userRepository;


  @Autowired
  RoleRepository roleRepository;


  @Autowired
  PasswordEncoder encoder;

  @Autowired
  private EmailService emailService;
  @Autowired
  JwtUtils jwtUtils;
  @Autowired
  CompetenceRepository comprrpo;
  @Autowired
  UserCompetenceReposirory usercomprrpo;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    String jwt = jwtUtils.generateJwtToken(authentication);
    MessageResponse customResponse = new MessageResponse("Ce compte est désactivé");
    User u=userRepository.findById(userDetails.getId()).orElse(null);
    if(u.getActive()==false) {
      return ResponseEntity.ok(customResponse);
    }else {
      String roles = userDetails.getAuthorities().stream()
              .map(GrantedAuthority::getAuthority)
              .findFirst()
              .orElse(null);


      return ResponseEntity.ok(new UserInfoResponse(jwt,
              userDetails.getId(),
              userDetails.getUsername(),
              userDetails.getEmail(),
              roles));
    }
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

//    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
//    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
                         signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()));



    // Enregistrer les modifications dans la base de données
    String strRoles = signUpRequest.getRole();

      switch (strRoles) {
        case "responsable":
          Role responsableRole = roleRepository.findByName(ERole.ROLE_RESPONSABLE)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          user.setRoles(responsableRole);

          break;
        case "developpeur":
          user.setStatus("disponible");
          Role ROLE_DEVELOPPEUR = roleRepository.findByName(ERole.ROLE_DEVELOPPEUR)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          user.setRoles(ROLE_DEVELOPPEUR);

          break;
        case "superadmin":
          Role ROLE_SUPERADMIN = roleRepository.findByName(ERole.ROLE_SUPERADMIN)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          user.setRoles(ROLE_SUPERADMIN);

          break;
        case "chef de projet":
          Role ROLE_CHEF_DE_PROJET = roleRepository.findByName(ERole.ROLE_CHEF_DE_PROJET)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          user.setRoles(ROLE_CHEF_DE_PROJET);

          break;
        }


    user.setActive(true);
    user.setNom(signUpRequest.getNom());
    user.setPrenom(signUpRequest.getPrenom());
    userRepository.save(user);
    // Email message
    SimpleMailMessage mailinscription = new SimpleMailMessage();
    mailinscription.setFrom("support@pfe.com");
    mailinscription.setTo(user.getEmail());
    mailinscription.setSubject("information compte");
    mailinscription.setText("Bienvenue\n"+"Vous avez inscrit dans notre application avec d'identifiant "+signUpRequest.getUsername() +" et mot de passe : "
    +signUpRequest.getPassword());

    emailService.sendEmail(mailinscription);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  @PostMapping("/signup-superadmin")
  public ResponseEntity<?> registersuperadmin(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
    }

//    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
//      return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
//    }

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));
    Role ROLE_SUPERADMIN = roleRepository.findByName(ERole.ROLE_SUPERADMIN)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
    user.setRoles(ROLE_SUPERADMIN);
    user.setActive(true);
    user.setNom(signUpRequest.getNom());
    user.setPrenom(signUpRequest.getPrenom());
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }


}
