package com.faktoria.zarzadzanieprodukcja.controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class HelpControllerDialog extends Application{

    @Override
    public void start(Stage primaryStage) {
        Button helpButton = new Button("Pomoc");
        helpButton.setOnAction(e -> showHelpDialog());

        VBox root = new VBox(10, helpButton);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }

    private void showHelpDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Pomoc");
        dialog.initModality(Modality.APPLICATION_MODAL);

        VBox dialogContent = new VBox(10);
        dialogContent.getChildren().add(new Label("Tutaj umieść swoją treść pomocy..."));
        // Możesz dodać więcej komponentów do VBox, np. listę FAQ

        dialog.getDialogPane().setContent(dialogContent);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
