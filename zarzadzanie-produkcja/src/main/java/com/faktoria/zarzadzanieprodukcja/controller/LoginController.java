package com.faktoria.zarzadzanieprodukcja.controller;

import com.faktoria.zarzadzanieprodukcja.model.UserSession;
import com.faktoria.zarzadzanieprodukcja.repository.UserRepository;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

    // Usunięto zbędne adnotacje @Autowired z pól klasy
    @Autowired // Jeden konstruktor z wstrzykniętymi zależnościami
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
                        userSession.loginUser(user.getId(), username); // Użyj metody loginUser zamiast setUserLoggedIn i setUsername
                        return true; // Pomyślne uwierzytelnienie
                    }
                    return false; // Nieudane uwierzytelnienie
                })
                .orElse(false);
    }

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
                // Pobierz ID użytkownika (jeśli potrzebne)
                Long userId = userRepository.findByUsername(usernameField.getText()).get().getId();
                userSession.loginUser(userId, usernameField.getText());
                primaryStage.setTitle("Zalogowany jako: " + usernameField.getText());
                Alert correctAlert = new Alert(Alert.AlertType.INFORMATION);
                correctAlert.setTitle("Poprawnie zalogowano");
                correctAlert.setHeaderText(null);
                correctAlert.setContentText("Witaj w programie " +userSession.getUsername()+" !");
                correctAlert.showAndWait();
                loginStage.close();
            }
            else {
                // Informacja o błędzie logowania
                Alert failAlert = new Alert(Alert.AlertType.INFORMATION);
                failAlert.setTitle("Login lub hasło niepoprawne");
                failAlert.setHeaderText(null);
                failAlert.setContentText("Podaj poprawny login lub hasło!");
                failAlert.showAndWait();
            }
        });

        loginPane.getChildren().addAll(new Label("Login:"), usernameField, new Label("Hasło:"), passwordField, loginButton);

        Scene scene = new Scene(loginPane, 300, 200);
        loginStage.setScene(scene);
        loginStage.showAndWait();
    }
}
