package com.faktoria.zarzadzanieprodukcja;

import com.faktoria.zarzadzanieprodukcja.controller.HelpController;
import com.faktoria.zarzadzanieprodukcja.controller.LoginController;
import com.faktoria.zarzadzanieprodukcja.controller.OpenCreateListWindow;
import com.faktoria.zarzadzanieprodukcja.controller.RegistrationController;
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

        centerBox.getChildren().addAll(btnCreateAccount, btnLogin, btnHelp, btnCreateList, btnLogout);
        root.setCenter(centerBox);  // Dodanie centerBox do środka root

        setupControllers();  // Konfiguracja kontrolerów i ich akcji

        mainScene = new Scene(root, 800, 600);  // Utworzenie sceny z root jako kontenerem
        return mainScene;
    }


    public static Scene getMainScene() {
        return mainScene;
    }

    private void setupControllers() {
        RegistrationController registrationController = new RegistrationController(primaryStage, springContext);
        btnCreateAccount.setOnAction(e -> primaryStage.setScene(registrationController.createRegistrationScene()));

        LoginController loginController = springContext.getBean(LoginController.class);
        btnLogin.setOnAction(e -> loginController.login(primaryStage));

        HelpController helpController = springContext.getBean(HelpController.class);
        btnHelp.setOnAction(e -> helpController.showHelpDialog());

        OpenCreateListWindow createListWindow = springContext.getBean(OpenCreateListWindow.class);
        btnCreateList.setOnAction(e -> createListWindow.openCreateListWindow());

        UserSession userSession = springContext.getBean(UserSession.class);
        btnLogout.setOnAction(e -> logoutUser(primaryStage));
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


