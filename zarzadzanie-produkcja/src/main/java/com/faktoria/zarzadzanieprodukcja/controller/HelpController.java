package com.faktoria.zarzadzanieprodukcja.controller;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;

import java.awt.*;

@Component
public class HelpController {

    public void showHelpDialog() {
        // Przygotowanie treści pomocy z formatowaniem

        Text headerText = new Text("Tworzenie konta:\n");
        headerText.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Image image = new Image(getClass().getResourceAsStream("/images/helptest.png"));ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        Text infoText1 = new Text("\n Aby utworzyć konto, należy kliknąć przycisk 'Utwórz nowe konto' na głównym ekranie.\n Następnie należy wpisać swój login oraz hasło. \n Jeśli wszystko jest gotowe, kliknij przycisk 'Wyślij'. \n \n");
        infoText1.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        Text headerText2 = new Text("Logowanie:\n");
        headerText2.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Image image2 = new Image(getClass().getResourceAsStream("/images/helptest2.png"));ImageView imageView2 = new ImageView(image2);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        Text infoText2 = new Text("\n Po utworzeniu konta, kliknij przycisk 'Logowanie' na głównym ekranie. \n Następnie wpisz swój login i hasło i kliknij 'Zaloguj'.");
        infoText2.setFont(Font.font("Arial", FontWeight.NORMAL, 14));

        // Użycie TextFlow dla skomplikowanego formatowania
        TextFlow textFlow = new TextFlow(headerText,  imageView, infoText1, headerText2, imageView2, infoText2);
        textFlow.setLineSpacing(5); // Ustawienie odstępu między liniami

        // Umieszczenie TextFlow w ScrollPane
        ScrollPane scrollPane = new ScrollPane(textFlow);
        scrollPane.setFitToWidth(true);

        // Tworzenie i wyświetlanie sceny
        Stage helpStage = new Stage();
        helpStage.setTitle("Pomoc");
        helpStage.setScene(new Scene(scrollPane, 800, 600));
        helpStage.show();
    }
}
