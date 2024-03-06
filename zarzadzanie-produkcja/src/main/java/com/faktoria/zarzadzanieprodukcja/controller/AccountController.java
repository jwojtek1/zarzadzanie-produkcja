package com.faktoria.zarzadzanieprodukcja.controller;

import com.faktoria.zarzadzanieprodukcja.model.User;
import com.faktoria.zarzadzanieprodukcja.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // W klasie AccountController

    public boolean createUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false; // Użytkownik o podanym loginie już istnieje
        } else {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPassword(passwordEncoder.encode(password)); // Załóżmy, że hasło jest hashowane
            userRepository.save(newUser);
            return true;
        }
    }
}

