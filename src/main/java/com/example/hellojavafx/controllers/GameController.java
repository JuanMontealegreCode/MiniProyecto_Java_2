package com.example.hellojavafx.controllers;

import com.example.hellojavafx.models.Sudoku;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class GameController {

    private static final int GRID_SIZE = 6;

    @FXML
    private TextField tf1;
    @FXML
    private TextField tf2;
    @FXML
    private TextField tf3;
    @FXML
    private TextField tf4;
    @FXML
    private TextField tf5;
    @FXML
    private TextField tf6;
    @FXML
    private TextField tf7;
    @FXML
    private TextField tf8;
    @FXML
    private TextField tf9;
    @FXML
    private TextField tf10;
    @FXML
    private TextField tf11;
    @FXML
    private TextField tf12;
    @FXML
    private TextField tf13;
    @FXML
    private TextField tf14;
    @FXML
    private TextField tf15;
    @FXML
    private TextField tf16;
    @FXML
    private TextField tf17;
    @FXML
    private TextField tf18;
    @FXML
    private TextField tf19;
    @FXML
    private TextField tf20;
    @FXML
    private TextField tf21;
    @FXML
    private TextField tf22;
    @FXML
    private TextField tf23;
    @FXML
    private TextField tf24;
    @FXML
    private TextField tf25;
    @FXML
    private TextField tf26;
    @FXML
    private TextField tf27;
    @FXML
    private TextField tf28;
    @FXML
    private TextField tf29;
    @FXML
    private TextField tf30;
    @FXML
    private TextField tf31;
    @FXML
    private TextField tf32;
    @FXML
    private TextField tf33;
    @FXML
    private TextField tf34;
    @FXML
    private TextField tf35;
    @FXML
    private TextField tf36;

    private TextField[][] textFields;

    private Sudoku sudoku;

    @FXML
    public void initialize() {
        // Inicializar la matriz de TextFields
        textFields = new TextField[GRID_SIZE][GRID_SIZE];

        // Asignar los TextFields a la matriz
        textFields[0][0] = tf1;
        textFields[0][1] = tf2;
        textFields[0][2] = tf3;
        textFields[0][3] = tf4;
        textFields[0][4] = tf5;
        textFields[0][5] = tf6;

        textFields[1][0] = tf7;
        textFields[1][1] = tf8;
        textFields[1][2] = tf9;
        textFields[1][3] = tf10;
        textFields[1][4] = tf11;
        textFields[1][5] = tf12;

        textFields[2][0] = tf13;
        textFields[2][1] = tf14;
        textFields[2][2] = tf15;
        textFields[2][3] = tf16;
        textFields[2][4] = tf17;
        textFields[2][5] = tf18;

        textFields[3][0] = tf19;
        textFields[3][1] = tf20;
        textFields[3][2] = tf21;
        textFields[3][3] = tf22;
        textFields[3][4] = tf23;
        textFields[3][5] = tf24;

        textFields[4][0] = tf25;
        textFields[4][1] = tf26;
        textFields[4][2] = tf27;
        textFields[4][3] = tf28;
        textFields[4][4] = tf29;
        textFields[4][5] = tf30;

        textFields[5][0] = tf31;
        textFields[5][1] = tf32;
        textFields[5][2] = tf33;
        textFields[5][3] = tf34;
        textFields[5][4] = tf35;
        textFields[5][5] = tf36;

        // Inicializar el Sudoku y generar un nuevo juego
        sudoku = new Sudoku();
        generarNuevoJuego();
    }

    private void generarNuevoJuego() {
        // Generar un nuevo Sudoku
        sudoku.generarNuevoSudoku();

        // Obtener el tablero visible que se mostrará al usuario
        int[][] grid = sudoku.getVisibleSudoku();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] != 0) {
                    textFields[i][j].setText(String.valueOf(grid[i][j]));
                    textFields[i][j].setEditable(false);
                    textFields[i][j].setStyle("-fx-background-color: lightgray;");
                } else {
                    textFields[i][j].setText("");
                    textFields[i][j].setEditable(true);
                    textFields[i][j].setStyle("");
                }

                // Limitar el texto a un carácter y permitir solo números del 1 al 6
                textFields[i][j].setTextFormatter(new javafx.scene.control.TextFormatter<String>(change -> {
                    if (change.getControlNewText().matches("[1-6]?")) {
                        return change;
                    } else {
                        return null;
                    }
                }));

                // Agregar un listener para validar la entrada
                int row = i;
                int col = j;
                textFields[i][j].textProperty().addListener((observable, oldValue, newValue) -> {
                    if (!newValue.isEmpty()) {
                        int valor = Integer.parseInt(newValue);
                        validarEntrada(row, col, valor);
                    } else {
                        textFields[row][col].setStyle("");
                    }
                });
            }
        }
    }

    private void validarEntrada(int row, int col, int valor) {
        // Verificar si el número ingresado es correcto
        if (sudoku.verificarNumero(row, col, valor)) {
            textFields[row][col].setStyle("-fx-text-inner-color: black;");
            // Puedes agregar lógica adicional para verificar si el Sudoku está completo
        } else {
            textFields[row][col].setStyle("-fx-text-inner-color: red;");
        }
    }

    @FXML
    void OnMouseClickedRestartNewGame(MouseEvent event) {
        generarNuevoJuego();
    }

    @FXML
    void OnMouseClickedHelpButton(MouseEvent event) {
        // Mostrar la solución completa
        int[][] grid = sudoku.getSudoku();
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                textFields[i][j].setText(String.valueOf(grid[i][j]));
                textFields[i][j].setEditable(false);
                textFields[i][j].setStyle("-fx-background-color: lightgray;");
            }
        }
    }
}
