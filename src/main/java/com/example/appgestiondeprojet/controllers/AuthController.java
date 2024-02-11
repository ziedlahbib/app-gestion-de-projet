package com.example.appgestiondeprojet.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.appgestiondeprojet.entity.ERole;
import com.example.appgestiondeprojet.entity.Role;
import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.payload.request.LoginRequest;
import com.example.appgestiondeprojet.payload.request.SignupRequest;
import com.example.appgestiondeprojet.payload.response.MessageResponse;
import com.example.appgestiondeprojet.payload.response.UserInfoResponse;
import com.example.appgestiondeprojet.repository.RoleRepository;
import com.example.appgestiondeprojet.repository.UserRepository;
import com.example.appgestiondeprojet.services.UserDetailsImpl;
import com.example.appgestiondeprojet.jwt.JwtUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
  JwtUtils jwtUtils;

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

    String strRoles = signUpRequest.getRole();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_DEVELOPPEUR)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      user.setRoles(userRole);
    } else {
      switch (strRoles) {
        case "responsable":
          Role responsableRole = roleRepository.findByName(ERole.ROLE_RESPONSABLE)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          user.setRoles(responsableRole);

          break;
        case "chef de projet":
          Role ROLE_CHEF_DE_PROJET = roleRepository.findByName(ERole.ROLE_CHEF_DE_PROJET)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          user.setRoles(ROLE_CHEF_DE_PROJET);

          break;
        default:
          Role ROLE_DEVELOPPEUR = roleRepository.findByName(ERole.ROLE_DEVELOPPEUR)
                  .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          user.setRoles(ROLE_DEVELOPPEUR);;
        }
    }

    user.setActive(true);
    user.setNom(signUpRequest.getNom());
    user.setPrenom(signUpRequest.getPrenom());
    user.setCompetence(signUpRequest.getCompetence());
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }


}
