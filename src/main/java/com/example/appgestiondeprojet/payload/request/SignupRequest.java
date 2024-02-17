package com.example.appgestiondeprojet.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    private String username;

    private String nom;
    private String prenom;
    private String competence;
    @NotBlank
    @Email
    private String email;

    private String role;
    
    @NotBlank
    private String password;

}
