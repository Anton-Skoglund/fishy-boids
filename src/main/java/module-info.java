module org.example.fishyboids {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.fishyboids to javafx.fxml;
    exports org.example.fishyboids;
}