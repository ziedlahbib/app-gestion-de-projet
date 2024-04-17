package com.example.appgestiondeprojet.repository;

import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.entity.UserTache;
import com.example.appgestiondeprojet.entity.UserTacheId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTacheRepository extends JpaRepository<UserTache, UserTacheId> {
    @Query("SELECT ut FROM UserTache ut WHERE ut.id.userId = :userId")
    List<UserTache> findAllByUserId(@Param("userId") Long userId);
}
