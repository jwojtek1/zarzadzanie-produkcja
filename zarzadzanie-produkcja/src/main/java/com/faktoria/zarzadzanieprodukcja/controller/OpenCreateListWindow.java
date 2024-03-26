package com.faktoria.zarzadzanieprodukcja.controller;

import com.faktoria.zarzadzanieprodukcja.model.UserSession;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class OpenCreateListWindow {

    private UserSession userSession;
    @Autowired
    public OpenCreateListWindow(UserSession userSession) {
        this.userSession = userSession;
    }

    ListView<File> fileList = new ListView<>();

    public void openCreateListWindow() {
        Stage listStage = new Stage();
        fileList = new ListView<>();

        Button addButton = new Button("Dodaj plik");
        addButton.setOnAction(e -> addFile());

        Button removeButton = new Button("Usuń plik");
        removeButton.setOnAction(e -> removeSelectedFile());

        Button printButton = new Button("Drukuj");
        printButton.setOnAction(e -> printFileList());

        HBox buttonLayout = new HBox(10);
        buttonLayout.getChildren().addAll(addButton, removeButton, printButton);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(fileList, buttonLayout);

        Scene scene = new Scene(layout, 400, 300);
        listStage.setScene(scene);
        listStage.setTitle("Tworzenie listy");
        listStage.show();
    }

    private void addFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);
        if (selectedFiles != null) {
            fileList.getItems().addAll(selectedFiles);
        }
    }

    private void removeSelectedFile() {
        int selectedIndex = fileList.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            fileList.getItems().remove(selectedIndex);
        }
    }

    private void printFileList() {
        Printer printer = Printer.getDefaultPrinter();
        if (printer != null) {
            PrinterJob job = PrinterJob.createPrinterJob();
            if (job != null && job.showPrintDialog(null)) {
                Canvas canvas = new Canvas(600, 800);
                GraphicsContext gc = canvas.getGraphicsContext2D();

                gc.setFont(Font.font("Arial", 12));
                double y = 20;
                double lineSpacing = 15;

                for (File file : fileList.getItems()) {
                    gc.fillText(file.getPath(), 20, y);
                    y += lineSpacing;
                }

                // Dodanie podpisu na końcu listy
                String signature = "Pozdrawiam";
                if (userSession.isUserLoggedIn()) {
                    signature += ", " + userSession.getUsername();
                }
                gc.fillText(signature, 20, y); // Dodaj podpis

                if (job.printPage(canvas)) {
                    job.endJob();
                }
            }
        } else {
            System.out.println("Nie znaleziono drukarki.");
        }
    }


}
