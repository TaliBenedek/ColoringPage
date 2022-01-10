module com.example.coloringpage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;


    opens com.example.coloringpage to javafx.fxml;
    exports com.example.coloringpage;
}