module com.example.coloringpage {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.coloringpage to javafx.fxml;
    exports com.example.coloringpage;
}