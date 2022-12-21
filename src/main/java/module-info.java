module com.blastoisefx {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.blastoisefx to javafx.fxml;
    exports com.blastoisefx;
}
