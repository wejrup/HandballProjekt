package com.wejrup.handballprojekt;

import com.wejrup.handballprojekt.controller.data.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException {

        if (!Database.openConnection("HåndboldDB")) {
            System.out.println("Kunne ikke oprette forbindelse til databasen");
            System.exit(1);
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("scenes/Menu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 800);

        // Set the application icon
        Image icon = new Image(getClass().getResourceAsStream("/com/wejrup/handballprojekt/Icons/handballAppIcon.png"));
        stage.getIcons().add(icon);

        stage.setTitle("Handball Match System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}