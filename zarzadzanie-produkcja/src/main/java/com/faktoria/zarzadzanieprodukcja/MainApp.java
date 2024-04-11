package com.faktoria.zarzadzanieprodukcja;

import com.faktoria.zarzadzanieprodukcja.controller.HelpController;
import com.faktoria.zarzadzanieprodukcja.controller.LoginController;
import com.faktoria.zarzadzanieprodukcja.controller.RegistrationController;
import com.faktoria.zarzadzanieprodukcja.controller.OpenCreateListWindow;
import com.faktoria.zarzadzanieprodukcja.model.UserSession;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.text.html.ListView;
import java.io.File;

public class MainApp extends Application {


    private static ConfigurableApplicationContext springContext;
    private static Scene mainScene;

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
            HelpController helpController = springContext.getBean(HelpController.class);
            helpController.showHelpDialog();
        });

        Button createListButton = new Button("Twórz listę");
        OpenCreateListWindow openCreateListWindow = springContext.getBean(OpenCreateListWindow.class);
        createListButton.setOnAction(e -> openCreateListWindow.openCreateListWindow());

        Button btnLogout = new Button("Wyloguj");
        btnLogout.setOnAction(e -> logoutUser(primaryStage));

        VBox layout = new VBox(10);
        layout.getChildren().addAll(btnCreateAccount, btnLogin, btnHelp, createListButton, btnLogout);

        mainScene = new Scene(layout, 1024, 768); // Inicjalizacja głównej sceny
        primaryStage.setScene(mainScene);
        primaryStage.show();


    }

    private void logoutUser(Stage primaryStage) {
        // Zakładamy, że UserSession jest komponentem Springa zarządzającym sesją użytkownika
        UserSession userSession = springContext.getBean(UserSession.class);

        // Reset stanu sesji użytkownika
        userSession.logoutUser();

        // Aktualizacja UI (opcjonalnie, w zależności od Twojej aplikacji)
        // Na przykład, możesz ukryć lub dezaktywować przyciski dostępne tylko dla zalogowanych użytkowników

        // Przekierowanie do ekranu logowania
        // Zakładamy, że masz metodę createLoginScene() tworzącą scenę logowania
        Scene loginScene = createLoginScene(primaryStage);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Faktoria - Zarządzanie Produkcją");
        Alert failAlert = new Alert(Alert.AlertType.INFORMATION);
        failAlert.setTitle("Wylogowano użytkownika");
        failAlert.setHeaderText(null);
        failAlert.setContentText("Poprawnie wylogowano użytkownika ");
        failAlert.showAndWait();
    }

    private Scene createLoginScene(Stage primaryStage) {
        // Tutaj tworzenie UI dla ekranu logowania
        VBox layout = new VBox(10);
        Button btnLogin = new Button("Zaloguj");
        // Dodanie logiki dla przycisku logowania, etc.

        return new Scene(layout, 400, 300);
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
