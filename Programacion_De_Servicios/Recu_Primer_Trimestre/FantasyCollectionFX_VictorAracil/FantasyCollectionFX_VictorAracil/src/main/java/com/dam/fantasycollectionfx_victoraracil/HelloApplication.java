package com.dam.fantasycollectionfx_victoraracil;

import com.dam.fantasycollectionfx_victoraracil.utils.FileUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * MainApplication is the entry point for the FantasyCollectionFX project.
 * It loads the main-view.fxml, applies styles, shows the main window, and
 * ensures that all items are saved when the application is closed.
 */
public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) {
        try {
            //Load main FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/dam/fantasycollectionfx_victoraracil/hello-view.fxml"));

            //Create the scene with the loaded content
            Scene scene = new Scene(loader.load(), 950, 650);

            //configure main window
            stage.setTitle("Fantasy Collection FX");
            stage.setScene(scene);
            stage.setResizable(false);

            //Obtain controller (auto-save on close)
            HelloController controller = loader.getController();

            //Auto-save when app is closed
            stage.setOnCloseRequest(event -> {
                try {
                    FileUtils.saveItems(controller.getItemList());
                    System.out.println("Items saved successfully on exit.");
                } catch (Exception e) {
                    System.err.println("Error saving items on exit: " + e.getMessage());
                }
            });

            //Show window
            stage.show();

        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(); //launch JavaFX app
    }
}

