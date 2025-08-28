module com.example.prattparsergui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    opens com.example.prattparsergui to javafx.fxml;
    exports com.example.prattparsergui;
}