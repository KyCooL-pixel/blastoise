module com.blastoisefx {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens com.blastoisefx to javafx.fxml;
    opens com.blastoisefx.controller to javafx.fxml;
    opens com.blastoisefx.model to javafx.fxml;
    opens com.blastoisefx.utils;

    exports com.blastoisefx;
    exports com.blastoisefx.controller;
    exports com.blastoisefx.model;
    exports com.blastoisefx.utils;

}
