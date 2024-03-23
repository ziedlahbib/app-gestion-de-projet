package com.example.appgestiondeprojet.repository;

import com.example.appgestiondeprojet.entity.Projet;
import com.example.appgestiondeprojet.entity.Tache;
import com.example.appgestiondeprojet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetRepository extends JpaRepository<Projet, Long> {
    List<Projet> findBychefDeProjetId(Long userId);
    @Query(value = "SELECT p.* FROM projet p " +
            "JOIN projet_taches pt ON p.id = pt.projet_id " +
            "WHERE pt.taches_id = :idtache", nativeQuery = true)
    Projet getProjectByTacheId(@Param("idtache") Long idtache);
}
