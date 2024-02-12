package com.example.appgestiondeprojet.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user_tache")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTache {
    @EmbeddedId
    private UserTacheId id;
    private double rating;
}
