package com.example.appgestiondeprojet.controllers;

import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.payload.request.SignupRequest;
import com.example.appgestiondeprojet.payload.response.UpdateProfileResponse;
import com.example.appgestiondeprojet.services.IUserservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*",exposedHeaders="Access-Control-Allow-Origin" )
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserservice userServ;
    @PutMapping("/update-user/{id-user}")
    @ResponseBody
    public ResponseEntity<UpdateProfileResponse> upadateuser(@RequestBody SignupRequest u, @PathVariable("id-user") Long iduser) {
        return userServ.updateUser(u, iduser);

    }
    @PutMapping("/update-profile/{id-user}")
    @ResponseBody
    public ResponseEntity<UpdateProfileResponse> upadateProfile(@RequestBody SignupRequest u, @PathVariable("id-user") Long iduser) {
        return userServ.updateProfile(u, iduser);

    }

    @DeleteMapping("/delete-user/{id-user}")
    @ResponseBody
    public ResponseEntity<?> deleteuser(@PathVariable("id-user") Long iduser) {
        return userServ.deleteUser(iduser);

    }
    @GetMapping("/get-user/{id-user}")
    @ResponseBody
    public User getuserbyid(@PathVariable("id-user") Long iduser) {
        return userServ.affichDetailUser(iduser);

    }
    @GetMapping("/exist-userbyusername/{username}")
    @ResponseBody
    public Boolean existbyusername(@PathVariable("username") String username) {
        return userServ.exitbyusernape(username);

    }
    @GetMapping("/exist-userbyemail/{email}")
    @ResponseBody
    public Boolean existbyemail(@PathVariable("email") String email) {
        return userServ.existbyemail(email);

    }
    @GetMapping("/get-users")
    @ResponseBody
    public List<User> getusers() {
        return userServ.affichUser();

    }
    @PutMapping("/activer-user/{id-user}")
    @ResponseBody
    public void activeruser(@PathVariable("id-user") Long iduser) {
        userServ.activer( iduser);

    }
    @PutMapping("/desactiver-user/{id-user}")
    @ResponseBody
    public void desactiveruser(@PathVariable("id-user") Long iduser) {
        userServ.desactiver( iduser);

    }
}
