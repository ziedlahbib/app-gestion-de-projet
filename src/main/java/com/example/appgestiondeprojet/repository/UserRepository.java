package com.example.appgestiondeprojet.repository;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.example.appgestiondeprojet.entity.User;


import java.util.List;
import java.util.Optional;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Optional<User> findByResetToken(String resetToken);
	Optional<User> findByEmail(String email);
	@Query("SELECT u FROM User u JOIN u.taches t GROUP BY u ORDER BY COUNT(t) DESC")
	List<User> findUsersOrderedByTaskCountDesc(Pageable pageable);




}
