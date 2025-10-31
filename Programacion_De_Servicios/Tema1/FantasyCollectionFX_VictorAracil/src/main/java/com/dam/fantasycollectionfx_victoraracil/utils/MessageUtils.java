package com.dam.fantasycollectionfx_victoraracil.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Utility class for showing different types of messages in JavaFX.
 */
public class MessageUtils {

    /**
     * Shows an information message to the user.
     *
     * @param header  The title or short description of the message.
     * @param message The detailed message to display.
     */
    public static void showMessage(String header, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows an error message to the user.
     *
     * @param header  The title or short description of the error.
     * @param message The detailed error message.
     */
    public static void showError(String header, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Shows a confirmation dialog and returns true if the user accepts.
     *
     * @param header  The question or short description.
     * @param message The detailed confirmation message.
     * @return true if the user clicks OK, false otherwise.
     */
    public static boolean showConfirmation(String header, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(header);
        alert.setContentText(message);

        return alert.showAndWait()
                .filter(response -> response == javafx.scene.control.ButtonType.OK)
                .isPresent();
    }
}
