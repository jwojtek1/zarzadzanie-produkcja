package com.faktoria.zarzadzanieprodukcja.controller;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import org.springframework.context.ConfigurableApplicationContext;

public class UserAccountWindow {

    private ConfigurableApplicationContext springContext;

    public UserAccountWindow(ConfigurableApplicationContext springContext) {
        this.springContext = springContext;
    }

    public void display(Stage primaryStage) {
        primaryStage.setTitle("Konto użytkownika");

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Button changePasswordButton = new Button("Zmień hasło");
        changePasswordButton.setOnAction(e -> {
            ChangePasswordWindow changePasswordWindow = new ChangePasswordWindow(springContext);
            Stage newStage = new Stage();  // Tworzenie nowego Stage
            changePasswordWindow.display(newStage, "email@example.com");  // Przekazanie nowego Stage do metody display
        });

        layout.getChildren().add(changePasswordButton);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
