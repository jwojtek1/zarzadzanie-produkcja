package com.faktoria.zarzadzanieprodukcja;

import com.faktoria.zarzadzanieprodukcja.controller.HelpController;
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

    private static Scene mainScene;
    private static ConfigurableApplicationContext springContext;

    public static Scene getMainScene() {
        return mainScene;
    }

    public static void main(String[] args) {
        springContext = SpringApplication.run(SpringApp.class, args);
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Faktoria - Zarządzanie Produkcją"); //nazwa wyświetlana w oknie aplikacji

        //button rejestracji użytkownika
        RegistrationController registrationController = new RegistrationController(primaryStage, springContext);
        Button btnCreateAccount = new Button("Utwórz nowe konto");
        btnCreateAccount.setOnAction(e -> primaryStage.setScene(registrationController.createRegistrationScene()));

        //button logowania
        Button btnLogin = new Button("Zaloguj");
        LoginController loginController = springContext.getBean(LoginController.class);
        btnLogin.setOnAction(e -> loginController.login(primaryStage));

        //button pomoc
        // W klasie MainApp, w metodzie start()
        Button btnHelp = new Button("Pomoc");
        btnHelp.setOnAction(e -> {
            new HelpController().showHelpDialog(); // Bezpośrednie wywołanie okna dialogowego
        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(btnCreateAccount, btnLogin, btnHelp);

        mainScene = new Scene(layout, 1024, 768); // Inicjalizacja głównej sceny
        primaryStage.setScene(mainScene);
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
