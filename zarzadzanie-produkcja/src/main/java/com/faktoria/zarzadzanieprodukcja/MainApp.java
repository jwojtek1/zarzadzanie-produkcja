package com.faktoria.zarzadzanieprodukcja;

import com.faktoria.zarzadzanieprodukcja.controller.*;
import com.faktoria.zarzadzanieprodukcja.model.UserSession;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApp extends Application {

    private static final Logger logger = LoggerFactory.getLogger(MainApp.class);

    private static ConfigurableApplicationContext springContext;
    private Stage primaryStage;
    private static Scene mainScene;

    private Button btnCreateAccount;
    private Button btnLogin;
    private Button btnHelp;
    private Button btnCreateList;
    private Button btnLogout;
    private Button btnUserAccount;

    public static void main(String[] args) {
        springContext = SpringApplication.run(SpringApp.class, args);
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            logger.error("Nieoczekiwany błąd w wątku: " + thread.getName(), throwable);
            // Możemy tu dodać np. Alert, ale trzeba to robić ostrożnie, aby nie zakłócić pracy aplikacji
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Nieoczekiwany błąd");
                alert.setHeaderText("Wystąpił nieoczekiwany błąd");
                alert.setContentText("Zgłoś problem do administracji systemu.");
                alert.showAndWait();
            });
        });
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Faktoria - Zarządzanie Produkcją");
        primaryStage.setScene(createMainScene());
        primaryStage.show();
    }

    public Scene createMainScene() {
        BorderPane root = new BorderPane();
        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);

        btnCreateAccount = new Button("Utwórz nowe konto");
        btnLogin = new Button("Zaloguj");
        btnHelp = new Button("Pomoc");
        btnCreateList = new Button("Twórz listę");
        btnLogout = new Button("Wyloguj");
        btnUserAccount = new Button("Konto użytkownika"); // Poprawna inicjalizacja przycisku


        centerBox.getChildren().addAll(btnCreateAccount, btnLogin, btnHelp, btnCreateList, btnLogout, btnUserAccount);
        root.setCenter(centerBox);  // Dodanie centerBox do środka root

        setupControllers();  // Konfiguracja kontrolerów i ich akcji

        mainScene = new Scene(root, 1024, 768);  // Utworzenie sceny z root jako kontenerem
        return mainScene;
    }


    public static Scene getMainScene() {
        return mainScene;
    }

    private void setupControllers() {
        EmailService emailService = springContext.getBean(EmailService.class);
        RegistrationController registrationController = new RegistrationController(primaryStage, springContext, emailService);
        btnCreateAccount.setOnAction(e -> primaryStage.setScene(registrationController.createRegistrationScene()));

        LoginController loginController = springContext.getBean(LoginController.class);
        btnLogin.setOnAction(e -> loginController.login(primaryStage));

        HelpController helpController = springContext.getBean(HelpController.class);
        btnHelp.setOnAction(e -> helpController.showHelpDialog());

        OpenCreateListWindow createListWindow = springContext.getBean(OpenCreateListWindow.class);
        btnCreateList.setOnAction(e -> createListWindow.openCreateListWindow());

        UserSession userSession = springContext.getBean(UserSession.class);
        btnLogout.setOnAction(e -> logoutUser(primaryStage));

        btnUserAccount.setOnAction(e -> openUserAccountWindow());
    }

    private void openUserAccountWindow() {
        // Użyj nazwy klasy, gdy odwołujesz się do statycznej zmiennej z metody niestatycznej
        UserAccountWindow userAccountWindow = new UserAccountWindow(MainApp.springContext);
        userAccountWindow.display(primaryStage);
    }


    private void logoutUser(Stage primaryStage) {
        UserSession userSession = springContext.getBean(UserSession.class);
        userSession.logoutUser();  // Resetuje stan sesji

        primaryStage.setScene(createMainScene());  // Przeładowanie głównej sceny
        primaryStage.setTitle("Faktoria - Zarządzanie Produkcją");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Wylogowano");
        alert.setHeaderText(null);
        alert.setContentText("Zostałeś wylogowany.");
        alert.showAndWait();
    }




    @Override
    public void stop() throws Exception {
        if (springContext != null) {
            springContext.close();
        }
        super.stop();
    }

    public static ConfigurableApplicationContext getSpringContext() {
        return springContext;
    }
}


