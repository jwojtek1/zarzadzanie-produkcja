package com.faktoria.zarzadzanieprodukcja.controller;

import com.faktoria.zarzadzanieprodukcja.SpringApp;
import javafx.scene.Scene;
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
        PasswordField passwordField = new PasswordField();
        Button submitButton = new Button("Wyślij");

        submitButton.setOnAction(e -> {
            AccountController accountController = springContext.getBean(AccountController.class);
            accountController.createUser(usernameField.getText(), passwordField.getText());
            System.out.println("Użytkownik zarejestrowany: " + usernameField.getText());
            // Możesz tutaj dodać przejście do głównej sceny lub wyświetlić komunikat o sukcesie
        });

        layout.getChildren().addAll(usernameField, passwordField, submitButton);
        return new Scene(layout, 1024, 768);
    }
}

