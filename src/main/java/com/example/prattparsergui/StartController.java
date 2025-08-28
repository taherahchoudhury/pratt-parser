package com.example.prattparsergui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class StartController {

    @FXML
    private Button btnStart;

    @FXML
    private void onStartButtonClick(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(StartApplication.class.getResource("pratt-view.fxml"));
        Scene parserScene = new Scene(fxmlLoader.load(), 800, 600);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(parserScene);
        window.setResizable(false);
        window.show();
    }
}
