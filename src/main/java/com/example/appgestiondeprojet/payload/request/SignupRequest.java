package com.example.appgestiondeprojet.payload.request;

import com.example.appgestiondeprojet.entity.Technologies;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    @NotBlank
    @Email
    private String email;

    private String role;
    
    @NotBlank
    private String password;

}
