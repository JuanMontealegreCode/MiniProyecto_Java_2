package com.example.hellojavafx.controllers;

import com.example.hellojavafx.models.Sudoku;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class GameController {

    private Sudoku sudokuModel = new Sudoku();
    private final ArrayList<ArrayList<TextField>> sudokuGrid = new ArrayList<>(6);

    @FXML
    private TextField tf1, tf2, tf3, tf4, tf5, tf6;
    @FXML
    private TextField tf7, tf8, tf9, tf10, tf11, tf12;
    @FXML
    private TextField tf13, tf14, tf15, tf16, tf17, tf18;
    @FXML
    private TextField tf19, tf20, tf21, tf22, tf23, tf24;
    @FXML
    private TextField tf25, tf26, tf27, tf28, tf29, tf30;
    @FXML
    private TextField tf31, tf32, tf33, tf34, tf35, tf36;

    @FXML
    public void initialize() {
        // Inicialización de las celdas
        ArrayList<TextField> row1 = new ArrayList<>();
        row1.add(tf1); row1.add(tf2); row1.add(tf3); row1.add(tf4); row1.add(tf5); row1.add(tf6);
        sudokuGrid.add(row1);

        ArrayList<TextField> row2 = new ArrayList<>();
        row2.add(tf7); row2.add(tf8); row2.add(tf9); row2.add(tf10); row2.add(tf11); row2.add(tf12);
        sudokuGrid.add(row2);

        ArrayList<TextField> row3 = new ArrayList<>();
        row3.add(tf13); row3.add(tf14); row3.add(tf15); row3.add(tf16); row3.add(tf17); row3.add(tf18);
        sudokuGrid.add(row3);

        ArrayList<TextField> row4 = new ArrayList<>();
        row4.add(tf19); row4.add(tf20); row4.add(tf21); row4.add(tf22); row4.add(tf23); row4.add(tf24);
        sudokuGrid.add(row4);

        ArrayList<TextField> row5 = new ArrayList<>();
        row5.add(tf25); row5.add(tf26); row5.add(tf27); row5.add(tf28); row5.add(tf29); row5.add(tf30);
        sudokuGrid.add(row5);

        ArrayList<TextField> row6 = new ArrayList<>();
        row6.add(tf31); row6.add(tf32); row6.add(tf33); row6.add(tf34); row6.add(tf35); row6.add(tf36);
        sudokuGrid.add(row6);

        generarNuevoTablero();
    }

    @FXML
    public void OnMouseClickedHelpButton(MouseEvent event) {
        // Seleccionar una celda vacía aleatoria y sugerir un valor válido
        ArrayList<TextField> celdasVacias = new ArrayList<>();

        // Recopilar todas las celdas vacías
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (sudokuGrid.get(i).get(j).getText().isEmpty()) {
                    celdasVacias.add(sudokuGrid.get(i).get(j));
                }
            }
        }

        if (!celdasVacias.isEmpty()) {
            // Seleccionar una celda vacía aleatoria
            Random random = new Random();
            TextField celdaSugerida = celdasVacias.get(random.nextInt(celdasVacias.size()));

            // Encontrar la fila y columna de la celda sugerida
            int fila = -1, columna = -1;
            for (int i = 0; i < sudokuGrid.size(); i++) {
                if (sudokuGrid.get(i).contains(celdaSugerida)) {
                    fila = i;
                    columna = sudokuGrid.get(i).indexOf(celdaSugerida);
                    break;
                }
            }

            if (fila != -1 && columna != -1) {
                // Buscar un valor válido para la celda
                boolean sugerenciaEncontrada = false;
                for (int valor = 1; valor <= 6; valor++) {
                    if (sudokuModel.validarFila(fila, valor) && sudokuModel.validarColumna(columna, valor)
                            && sudokuModel.validarCuadrante(fila, columna, valor)) {
                        // Mostrar la sugerencia
                        celdaSugerida.setText(String.valueOf(valor));
                        celdaSugerida.setStyle("-fx-background-color: yellow;"); // Resaltar la celda en amarillo
                        sugerenciaEncontrada = true;
                        break;
                    }
                }

                // Si no se encontró un valor válido, mostrar un mensaje
                if (!sugerenciaEncontrada) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Información");
                    alert.setHeaderText(null);
                    alert.setContentText("No se encontró un valor válido para la celda seleccionada.");
                    alert.showAndWait();
                }
            } else {
                // Mostrar un mensaje si la celda no tiene fila o columna válida
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setHeaderText(null);
                alert.setContentText("No se pudo determinar la ubicación de la celda seleccionada.");
                alert.showAndWait();
            }
        } else {
            // Mostrar un mensaje si no hay celdas vacías
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText(null);
            alert.setContentText("No hay celdas vacías disponibles para sugerencias.");
            alert.showAndWait();
        }
    }

    @FXML
    void OnKeyTypedInCell(KeyEvent event) {
        TextField source = (TextField) event.getSource();
        String input = event.getCharacter();

        // Validar que la entrada esté en el rango permitido (1-6)
        if (!input.matches("[1-6]")) {
            event.consume();
            source.setStyle("-fx-border-color: red;");
        } else {
            int fila = -1, columna = -1;

            // Busca en qué fila y columna está el TextField
            for (int i = 0; i < sudokuGrid.size(); i++) {
                ArrayList<TextField> row = sudokuGrid.get(i);
                if (row.contains(source)) {
                    fila = i;
                    columna = row.indexOf(source);
                    break;
                }
            }

            // Si encontramos la fila y columna
            if (fila != -1 && columna != -1) {
                int valor = Integer.parseInt(input);

                // Verificar si el número es correcto
                if (sudokuModel.verificarNumero(fila, columna, valor)) {
                    sudokuModel.revelarNumero(fila, columna); // Revelar el número
                    source.setText(String.valueOf(valor)); // Muestra el número en la celda
                    source.setStyle("-fx-border-color: green;"); // Resaltar la celda en verde si es correcto
                } else {
                    event.consume();
                    source.setStyle("-fx-border-color: red;"); // Resaltar la celda en rojo si es incorrecto
                }
            }
        }
    }

    @FXML
    public void OnMouseClickedRestartNewGame(MouseEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Seguro que quieres empezar un nuevo juego?");
        alert.setContentText("Esto reiniciará el tablero.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            generarNuevoTablero();
        }
    }

    public void generarNuevoTablero() {
        sudokuModel.generarSudokuCompleto(); // Genera el tablero completo
        int[][] visibleTablero = sudokuModel.getVisibleSudoku(); // Obtén el tablero visible (inicialmente vacío)

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (visibleTablero[i][j] != 0) {
                    sudokuGrid.get(i).get(j).setText(String.valueOf(visibleTablero[i][j]));
                } else {
                    sudokuGrid.get(i).get(j).clear(); // Limpia las celdas vacías
                    sudokuGrid.get(i).get(j).setStyle(""); // Reinicia el estilo de la celda
                }
            }
        }
    }
}
