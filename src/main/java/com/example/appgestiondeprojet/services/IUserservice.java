package com.example.appgestiondeprojet.services;


import com.example.appgestiondeprojet.payload.request.SignupRequest;
import com.example.appgestiondeprojet.payload.response.UpdateProfileResponse;
import org.springframework.http.ResponseEntity;
//import request.ChangePasswordRequest;
//import request.SignupRequest;
import com.example.appgestiondeprojet.entity.User;

import java.util.List;
import java.util.Optional;


public interface IUserservice {

//	public String updatepassword(ChangePasswordRequest request ,Long idUser);
	public User resetpassword(User user );
	public ResponseEntity<UpdateProfileResponse> updateUser(SignupRequest signUpRequest, Long idUser);
	public ResponseEntity<UpdateProfileResponse> updateProfile(SignupRequest signUpRequest, Long idUser);
	public ResponseEntity<?> deleteUser(Long idUser);
	public User affichDetailUser(Long idUser);
	public List<User> affichUser();
	public Optional<User> findbyusername(String username);
	Optional<User> findUserByEmail(String email);
	Optional<User> findUserByResetToken(String resetToken);
	Boolean exitbyusernape(String username);
	Boolean existbyemail(String email);
	public void activer(Long iduser);
	public void desactiver(Long iduser);
}
