package com.example.prattparsergui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StartApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("start-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("stylesheet.css")).toExternalForm());

        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("image.jpg"))));
        primaryStage.setTitle("Visualisation of Pratt Parsing");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}