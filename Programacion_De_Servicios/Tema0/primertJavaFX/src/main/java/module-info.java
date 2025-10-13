module com.dam.primertjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.dam.primertjavafx to javafx.fxml;
    exports com.dam.primertjavafx;
}