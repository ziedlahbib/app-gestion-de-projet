package com.example.appgestiondeprojet.services;

import com.example.appgestiondeprojet.entity.User;
import com.example.appgestiondeprojet.repository.ProjetRepository;
import com.example.appgestiondeprojet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
@Service
public class StatisticService {
    @Autowired
    ProjetRepository projetrepo;
    @Autowired
    UserRepository userrepo;
    public User getUserWithMostTasks() {
        List<User> users = userrepo.findUsersOrderedByTaskCountDesc(PageRequest.of(0, 1));
        return users.isEmpty() ? null : users.get(0);
}

        public List<String> getProjectsPerMonth(int year) {
        List<Object[]> results = projetrepo.countProjectsPerMonth(year);
        return results.stream()
                .map(result -> "Month: " + result[0] + ", Projects: " + result[1])
                .collect(Collectors.toList());
    }
}
