module com.dam.fantasycollectionfx_victoraracil {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;


    opens com.dam.fantasycollectionfx_victoraracil to javafx.fxml;
    exports com.dam.fantasycollectionfx_victoraracil;
}