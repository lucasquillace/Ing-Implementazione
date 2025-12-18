module luca.ingimplementazione {
    requires javafx.controls;
    requires javafx.fxml;


    opens luca.ingimplementazione to javafx.fxml;
    exports luca.ingimplementazione;
    exports luca.ingimplementazione.controller;
    opens luca.ingimplementazione.controller to javafx.fxml;
}