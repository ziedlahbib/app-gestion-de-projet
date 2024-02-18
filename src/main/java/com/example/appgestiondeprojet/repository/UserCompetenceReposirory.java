package com.example.appgestiondeprojet.repository;

import com.example.appgestiondeprojet.entity.Competence;
import com.example.appgestiondeprojet.entity.UserCompetence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCompetenceReposirory extends JpaRepository<UserCompetence, Long> {
}
