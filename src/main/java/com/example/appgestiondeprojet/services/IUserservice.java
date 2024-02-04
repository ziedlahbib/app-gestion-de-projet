package com.example.appgestiondeprojet.services;


import org.springframework.stereotype.Service;
//import request.ChangePasswordRequest;
//import request.SignupRequest;
import com.example.appgestiondeprojet.entity.User;

import java.util.List;
import java.util.Optional;


public interface IUserservice {

//	public String updatepassword(ChangePasswordRequest request ,Long idUser);
	public User resetpassword(User user );
//	public User updateUser(SignupRequest signUpRequest, Long idUser);
	public void deleteUser(Long idUser);
	public User affichDetailUser(Long idUser);
	public List<User> affichUser();
	public Optional<User> findbyusername(String username);
	Optional<User> findUserByEmail(String email);
	Optional<User> findUserByResetToken(String resetToken);
	public void activer(Long iduser);
	public void desactiver(Long iduser);
}
