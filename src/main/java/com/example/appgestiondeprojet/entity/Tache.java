package com.example.appgestiondeprojet.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tache implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @ElementCollection(targetClass = Technologies.class)
    @JoinTable(name = "technologie", joinColumns = @JoinColumn(name = "tacheid"))
    @Column(name = "Technologies", nullable = false)
    @Enumerated(EnumType.STRING)
    Collection<Technologies> technologies;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "user_tache",
            joinColumns = @JoinColumn(name = "tache_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;
    @Temporal(TemporalType.DATE)
    private Date date_debut;
    @Temporal(TemporalType.DATE)
    private Date date_fin;
}
