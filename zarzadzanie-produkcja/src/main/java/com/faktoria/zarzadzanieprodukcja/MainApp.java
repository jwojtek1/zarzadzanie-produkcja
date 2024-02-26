package com.faktoria.zarzadzanieprodukcja;

import com.faktoria.zarzadzanieprodukcja.controller.LoginController;
import com.faktoria.zarzadzanieprodukcja.controller.RegistrationController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApp extends Application {

    private static ConfigurableApplicationContext springContext;

    public static void main(String[] args) {
        springContext = SpringApplication.run(SpringApp.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Zarządzanie Produkcją");

        RegistrationController registrationController = new RegistrationController(primaryStage, springContext);
        Button btnCreateAccount = new Button("Utwórz nowe konto");
        btnCreateAccount.setOnAction(e -> primaryStage.setScene(registrationController.createRegistrationScene()));

        Button btnLogin = new Button("Zaloguj");
        Button btnHelp = new Button("Pomoc");

//        LoginController loginController = new LoginController();
//
//        btnLogin.setOnAction(e -> loginController.login());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(btnCreateAccount, btnLogin, btnHelp);

        Scene scene = new Scene(layout, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        if (springContext != null) {
            springContext.close(); // Zamknięcie kontekstu Springa
        }
        super.stop();
    }

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
}
