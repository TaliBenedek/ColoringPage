module com.example.coloringpage {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.coloringpage to javafx.fxml;
    exports com.example.coloringpage;
}