package com.faktoria.zarzadzanieprodukcja;

import com.faktoria.zarzadzanieprodukcja.controller.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;

public class MainApp extends Application {

    private ConfigurableApplicationContext context;

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);
        this.context = SpringApp.run(args); // Uruchomienie Springa i przechowanie kontekstu
        super.init();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Zarządzanie Produkcją");

        // Przyciski
        Button btnCreateAccount = new Button("Utwórz nowe konto");
        Button btnLogin = new Button("Zaloguj");
        Button btnHelp = new Button("Pomoc");

        // Utwórz instancję kontrolera bezpośrednio lub użyj wstrzykiwania Springa
        LoginController loginController = new LoginController();

        // Przypisanie akcji do przycisku btnLogin
        btnLogin.setOnAction(e -> loginController.login());

        // Layout dla przycisków
        VBox layout = new VBox(10);
        layout.getChildren().addAll(btnCreateAccount, btnLogin, btnHelp);

        Scene scene = new Scene(layout, 1024, 768);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        this.context.close(); // Zamknięcie kontekstu Springa
        super.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
