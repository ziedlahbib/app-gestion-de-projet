package com.example.appgestiondeprojet.repository;

import com.example.appgestiondeprojet.entity.Competence;
import com.example.appgestiondeprojet.entity.UserCompetence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetenceRepository extends JpaRepository<Competence, Long> {
}
