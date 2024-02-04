package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements IUserservice{
    @Autowired
    UserRepository userRepo;
    @Override
    public User resetpassword(User user) {
        return userRepo.save(user);
    }

    @Override
    public void deleteUser(Long idUser) {

    }

    @Override
    public User affichDetailUser(Long idUser) {
        return null;
    }

    @Override
    public List<User> affichUser() {
        return null;
    }

    @Override
    public Optional<User> findbyusername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public Optional<User> findUserByResetToken(String resetToken) {
        return userRepo.findByResetToken(resetToken);
    }

    @Override
    public void activer(Long iduser) {

    }

    @Override
    public void desactiver(Long iduser) {

    }
}
