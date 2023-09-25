module com.example.trafalgarbattlesnew {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.trafalgarbattlesnew.graphiccontrollers to javafx.fxml;
    exports com.example.trafalgarbattlesnew.graphiccontrollers;
}