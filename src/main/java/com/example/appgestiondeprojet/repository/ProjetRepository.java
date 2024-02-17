package com.example.appgestiondeprojet.repository;

import com.example.appgestiondeprojet.entity.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    List<Projet> findBychefDeProjetId(Long userId);
}
