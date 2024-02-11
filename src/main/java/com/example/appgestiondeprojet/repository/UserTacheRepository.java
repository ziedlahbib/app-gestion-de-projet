package com.example.appgestiondeprojet.repository;

import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.entity.UserTache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTacheRepository extends JpaRepository<UserTache, Long> {
}
