module com.example.studentloancalculator {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studentloancalculator to javafx.fxml;
    exports com.example.studentloancalculator;
}