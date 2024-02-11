package com.example.appgestiondeprojet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTacheId implements Serializable {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "tache_id")
    private Long tacheId;

    // Getters and setters, equals, hashCode
}

