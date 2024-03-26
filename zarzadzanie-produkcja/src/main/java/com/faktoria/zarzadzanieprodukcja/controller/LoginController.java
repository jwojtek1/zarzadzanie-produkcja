package com.faktoria.zarzadzanieprodukcja.controller;

import com.faktoria.zarzadzanieprodukcja.model.UserSession;
import com.faktoria.zarzadzanieprodukcja.repository.UserRepository;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class LoginController {

    private final ConfigurableApplicationContext springContext;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserSession userSession;

    // Usunięto adnotacje @Autowired z pól klasy, ponieważ będą one wstrzyknięte przez konstruktor
    @Autowired // Konstruktor z wstrzykniętym UserSession
    public LoginController(ConfigurableApplicationContext springContext, UserRepository userRepository, PasswordEncoder passwordEncoder, UserSession userSession) {
        this.springContext = springContext;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userSession = userSession; // Przypisanie wstrzykniętego UserSession
    }

    private boolean authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
                    if (passwordMatches) {
                        // Aktualizacja stanu sesji po pomyślnym zalogowaniu
                        userSession.setUserLoggedIn(true);
                        userSession.setUsername(username);
                        // Możesz również ustawić userId, jeśli jest potrzebne
                        userSession.setUserId(user.getId());
                        return true; // Pomyślne uwierzytelnienie
                    }
                    return false; // Nieudane uwierzytelnienie
                })
                .orElse(false);
    }


    // W klasie LoginController
    public void login(Stage primaryStage) {
        showLoginWindow(primaryStage);
    }


    public void showLoginWindow(Stage primaryStage) {
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.WINDOW_MODAL);
        loginStage.initOwner(primaryStage);
        VBox loginPane = new VBox(10);
        loginPane.setAlignment(Pos.CENTER);

        TextField usernameField = new TextField();
        usernameField.setPromptText("Login");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Hasło");
        Button loginButton = new Button("Zaloguj");

        loginButton.setOnAction(e -> {
            if (authenticate(usernameField.getText(), passwordField.getText())) {
                // Logika po pomyślnym zalogowaniu
                primaryStage.setTitle("Zalogowany jako: " + usernameField.getText());
                loginStage.close();
            } else {
                // Informacja o błędzie logowania
                System.out.println("Niepoprawny login lub hasło.");
            }
        });

        loginPane.getChildren().addAll(new Label("Login:"), usernameField, new Label("Hasło:"), passwordField, loginButton);

        Scene scene = new Scene(loginPane, 300, 200);
        loginStage.setScene(scene);
        loginStage.showAndWait();
    }

}

