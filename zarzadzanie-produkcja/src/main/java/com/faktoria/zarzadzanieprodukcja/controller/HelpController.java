package com.faktoria.zarzadzanieprodukcja.controller;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

public class HelpController {

    public void showHelpDialog() {
        Alert helpAlert = new Alert(Alert.AlertType.INFORMATION);
        helpAlert.setTitle("Pomoc");
        helpAlert.setHeaderText(null);
        helpAlert.setContentText("Jakieś informacje o pomocy.");
        helpAlert.showAndWait();
    }


}
