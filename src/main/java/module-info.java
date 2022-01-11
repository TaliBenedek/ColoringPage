module com.example.coloringpage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.swing;
    requires java.desktop;
    requires org.junit.jupiter.api;


    opens com.example.coloringpage to javafx.fxml;
    exports com.example.coloringpage;
}