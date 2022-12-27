module com.blastoisefx {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.blastoisefx to javafx.fxml;
    exports com.blastoisefx;
}
