package com.example.hellojavafx.view.alert;

import javafx.scene.control.Alert;

/**
 * Clase que representa una caja de alerta en la interfaz gráfica.
 * Implementa la interfaz {@link AlertBoxInterface} para mostrar alertas
 * al usuario.
 */
public class AlertBox implements AlertBoxInterface {

    /**
     * Muestra una alerta con el título, encabezado y mensaje especificados.
     *
     * @param title   El título de la alerta.
     * @param header  El texto del encabezado de la alerta.
     * @param message El contenido del mensaje de la alerta.
     */
    @Override
    public void showAlert(String title, String header, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait(); // Muestra la alerta y espera a que el usuario la cierre
    }
}
