package com.example.appgestiondeprojet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Projet implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom_projet;
    @Temporal(TemporalType.DATE)
    private Date date_limite;
    @ManyToOne(fetch =FetchType.LAZY)
    private User chefDeProjet;
    @OneToMany(fetch =FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Tache> taches;
}
