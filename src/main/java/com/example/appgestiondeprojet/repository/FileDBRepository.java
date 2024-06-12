package com.example.appgestiondeprojet.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.appgestiondeprojet.entity.FileDB;


public interface FileDBRepository extends JpaRepository<FileDB, Long> {
	

}
