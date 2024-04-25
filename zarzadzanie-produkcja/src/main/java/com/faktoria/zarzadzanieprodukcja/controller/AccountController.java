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

    public boolean createUser(String username, String password, String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            return false; // Użytkownik o podanym loginie już istnieje
        }
        if (userRepository.findByEmail(email).isPresent()) {
            return false; // Użytkownik o podanym emailu już istnieje
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // Hasłowanie hasła
        newUser.setEmail(email); // Dodanie adresu email
        userRepository.save(newUser);
        return true;
    }

    public boolean changeUserPassword(String username, String newPassword) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    String encodedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encodedPassword);
                    userRepository.save(user);
                    return true;  // Zmiana hasła udana
                }).orElse(false);  // Nie znaleziono użytkownika
    }
}
