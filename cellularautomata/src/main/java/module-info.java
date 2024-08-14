module com.celluarautomata {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.celluarautomata to javafx.fxml;
    exports com.celluarautomata;
}
