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
public interface TAcheRepository extends JpaRepository<Tache, Long> {
    List<Tache> findByUserId(Long userId);

//    @Query("SELECT u FROM User u JOIN u.taches t WHERE t.id = :tacheId  ")
//    List<User> userdetache(@Param("tacheId") Long tacheid);
@Query("SELECT u FROM User u JOIN u.taches t JOIN UserTache ut ON u.id = ut.id.userId " +
        "WHERE t.id = :tacheId AND ut.status = 'en cours'")
List<User> userdetache(@Param("tacheId") Long tacheId);
    @Query("SELECT u FROM User u JOIN u.taches t JOIN UserTache ut ON u.id = ut.id.userId " +
            "WHERE t.id = :tacheId")
    List<User> userdetacheall(@Param("tacheId") Long tacheId);


}
