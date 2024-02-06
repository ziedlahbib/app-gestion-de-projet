package com.example.appgestiondeprojet.repository;

import com.example.appgestiondeprojet.entity.Tache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TAcheRepository extends JpaRepository<Tache, Long> {
}
