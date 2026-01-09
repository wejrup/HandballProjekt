module com.wejrup.handballprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.wejrup.handballprojekt to javafx.fxml;
    exports com.wejrup.handballprojekt;
}