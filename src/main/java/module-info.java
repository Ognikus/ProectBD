module com.example.testbd {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.testbd to javafx.fxml;
    exports com.example.testbd;
}