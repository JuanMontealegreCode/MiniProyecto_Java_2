package com.example.hellojavafx.view.alert;

/**
 * Interfaz para mostrar alertas en la interfaz gráfica.
 * Define el método {@link #showAlert(String, String, String)} que debe
 * implementarse para mostrar una alerta con un título, encabezado y mensaje.
 */
public interface AlertBoxInterface {

    /**
     * Muestra una alerta con el título, encabezado y mensaje especificados.
     *
     * @param title   El título de la alerta.
     * @param header  El texto del encabezado de la alerta.
     * @param message El contenido del mensaje de la alerta.
     */
    public void showAlert(String title, String header, String message);
}
