module com.wejrup.handballprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.wejrup.handballprojekt to javafx.fxml;
    exports com.wejrup.handballprojekt;
    exports com.wejrup.handballprojekt.controller;
    opens com.wejrup.handballprojekt.controller to javafx.fxml;
    exports com.wejrup.handballprojekt.util;
    opens com.wejrup.handballprojekt.util to javafx.fxml;
}