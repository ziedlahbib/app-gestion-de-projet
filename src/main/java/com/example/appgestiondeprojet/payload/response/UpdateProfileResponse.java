package com.example.appgestiondeprojet.payload.response;

import com.example.appgestiondeprojet.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileResponse {
    private String message;
    private User user;


    // Getters and setters for message and user

    // Optional: You can include any additional fields or methods as needed
}

