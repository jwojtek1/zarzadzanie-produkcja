package com.faktoria.zarzadzanieprodukcja.controller;

import com.faktoria.zarzadzanieprodukcja.MainApp;
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

    private boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z0-9]+$");
    }

    public Scene createRegistrationScene() {
        VBox layout = new VBox(10);
        TextField usernameField = new TextField();
        usernameField.setPromptText("Login");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Hasło");
        Button submitButton = new Button("Wyślij");
        Button returnButton = new Button("Powrót");

        submitButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (!isValidUsername(username)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nieprawidłowa nazwa użytkownika");
                alert.setHeaderText("Błąd w nazwie użytkownika");
                alert.setContentText("Nazwa użytkownika może zawierać tylko litery i cyfry, bez spacji i znaków specjalnych.");
                alert.showAndWait();
                return; // Zatrzymaj dalsze przetwarzanie
            }

            AccountController accountController = springContext.getBean(AccountController.class);
            boolean isCreated = accountController.createUser(username, password);

            if (isCreated) {
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Rejestracja zakończona sukcesem");
                successAlert.setHeaderText(null);
                successAlert.setContentText("Konto zostało poprawnie założone.");
                successAlert.showAndWait();
                primaryStage.setScene(MainApp.getMainScene());
            } else {
                Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                errorAlert.setTitle("Błąd rejestracji");
                errorAlert.setHeaderText(null);
                errorAlert.setContentText("Użytkownik o podanej nazwie użytkownika już istnieje.");
                errorAlert.showAndWait();
            }
        });


        returnButton.setOnAction(f -> {
            primaryStage.setScene(MainApp.getMainScene()); // Powrót do głównej sceny
        });

        layout.getChildren().addAll(usernameField, passwordField, submitButton, returnButton);
        return new Scene(layout, 1024, 768);
    }
}
