module com.example.trafalgarbattlesnew {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires com.jfoenix;


    opens com.example.trafalgarbattlesnew.graphiccontrollers to javafx.fxml;
    exports com.example.trafalgarbattlesnew.graphiccontrollers;
}