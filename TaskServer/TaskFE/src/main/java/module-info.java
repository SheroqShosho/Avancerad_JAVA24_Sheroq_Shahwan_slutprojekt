module org.example.taskfe {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires com.fasterxml.jackson.databind;

    opens org.example.taskfe to javafx.fxml;
    exports org.example.taskfe;
}