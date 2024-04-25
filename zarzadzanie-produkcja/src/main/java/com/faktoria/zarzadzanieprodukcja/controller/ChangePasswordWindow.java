package com.faktoria.zarzadzanieprodukcja.controller;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import org.springframework.beans.factory.annotation.Autowired;
import com.faktoria.zarzadzanieprodukcja.repository.UserRepository;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ChangePasswordWindow {

    private ConfigurableApplicationContext springContext;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public ChangePasswordWindow(ConfigurableApplicationContext springContext) {
        this.springContext = springContext;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public void display(Stage primaryStage, String userEmail) {
        // GUI logic remains the same
        primaryStage.setTitle("Zmiana Hasła");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Wprowadź nowe hasło");

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Potwierdź nowe hasło");

        Button submitButton = new Button("Wyślij");
        submitButton.setOnAction(e -> {
            if (newPasswordField.getText().equals(confirmPasswordField.getText())) {
                updatePassword(userEmail, newPasswordField.getText()); // Aktualizacja hasła
                sendEmail(userEmail, "Twoje hasło zostało zmienione. Nowe hasło to: " + newPasswordField.getText()); // Wysłanie e-maila
            } else {
                showAlert("Błąd", "Hasła nie są identyczne!", Alert.AlertType.ERROR);
            }
        });

        layout.getChildren().addAll(newPasswordField, confirmPasswordField, submitButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updatePassword(String userEmail, String newPassword) {
        userRepository.findByEmail(userEmail).ifPresent(user -> {
            String encodedPassword = passwordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
            showAlert("Potwierdzenie", "Twoje hasło zostało zmienione.", Alert.AlertType.INFORMATION);
        });
    }

    private void sendEmail(String emailAddress, String message) {
        // Wykorzystaj EmailService, który jest już skonfigurowany i gotowy do użycia
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}


