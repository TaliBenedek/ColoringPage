module coloringpage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.swing;
    requires java.desktop;


    opens coloringpage to javafx.fxml;
    exports coloringpage;
}