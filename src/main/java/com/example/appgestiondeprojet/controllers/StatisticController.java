package com.example.appgestiondeprojet.controllers;

import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.services.IUserservice;
import com.example.appgestiondeprojet.services.StatisticService;
import com.example.appgestiondeprojet.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*",exposedHeaders="Access-Control-Allow-Origin" )
@RestController
@RequestMapping("/statistic")
public class StatisticController {
    @Autowired
    StatisticService statserv;
    @GetMapping("/most-tasks")
    public User getUserWithMostTasks() {
        return statserv.getUserWithMostTasks();
    }

    @GetMapping("/projects-per-month")
    public List<String> getProjectsPerMonth(@RequestParam int year) {
        return statserv.getProjectsPerMonth(year);
    }

}
