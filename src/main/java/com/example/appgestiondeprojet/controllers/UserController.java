package com.example.appgestiondeprojet.controllers;

import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.payload.request.SignupRequest;
import com.example.appgestiondeprojet.services.IUserservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*",exposedHeaders="Access-Control-Allow-Origin" )
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserservice userServ;
    @PutMapping("/update-user/{id-user}")
    @ResponseBody
    public User upadateProduit(@RequestBody SignupRequest u, @PathVariable("id-user") Long iduser) {
        return userServ.updateUser(u, iduser);

    }
    @DeleteMapping("/delete-user/{id-user}")
    @ResponseBody
    public void deleteuser(@PathVariable("id-user") Long iduser) {
        userServ.deleteUser(iduser);

    }
    @GetMapping("/get-user/{id-user}")
    @ResponseBody
    public User getuserbyid(@PathVariable("id-user") Long iduser) {
        return userServ.affichDetailUser(iduser);

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
