module com.example.picturesq {
    requires javafx.controls;
    requires javafx.fxml;
   requires javafx.graphics;
   requires java.sql;


    opens com.example.picturesq to javafx.fxml;
    exports com.example.picturesq;
}