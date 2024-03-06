package com.faktoria.zarzadzanieprodukcja.controller;

import com.faktoria.zarzadzanieprodukcja.MainApp;
import com.faktoria.zarzadzanieprodukcja.SpringApp;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

public class RegistrationController {

    private Stage primaryStage;
    private ConfigurableApplicationContext springContext;

    public RegistrationController(Stage primaryStage, ConfigurableApplicationContext springContext) {
        this.primaryStage = primaryStage;
        this.springContext = springContext;
    }

    public Scene createRegistrationScene() {
        VBox layout = new VBox(10);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Login");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Hasło");
        Button submitButton = new Button("Wyślij");
        Button returnButton = new Button("Powrót"); // Przycisk powrotu

        submitButton.setOnAction(e -> {
            AccountController accountController = springContext.getBean(AccountController.class);
            boolean isCreated = accountController.createUser(usernameField.getText(), passwordField.getText());


            returnButton.setOnAction(f -> {
                primaryStage.setScene(MainApp.getMainScene()); // Powrót do głównej sceny
            });

            if (isCreated) {
                // Wyświetlenie alertu o sukcesie
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Rejestracja zakończona sukcesem");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Konto zostało poprawnie założone.");
                successAlert.showAndWait();

                // Powrót do głównej sceny
                primaryStage.setScene(MainApp.getMainScene());
            } else {
                // Wyświetlenie alertu o błędzie
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Błąd rejestracji");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Użytkownik o podanej nazwie użytkownika już istnieje.");
                errorAlert.showAndWait();
            }

            returnButton.setOnAction(f -> {
                primaryStage.setScene(MainApp.getMainScene()); // Powrót do głównej sceny
            });
        });



        layout.getChildren().addAll(usernameField, passwordField, submitButton, returnButton); // Dodanie przycisku powrotu
        return new Scene(layout, 1024, 768);
    }
}

