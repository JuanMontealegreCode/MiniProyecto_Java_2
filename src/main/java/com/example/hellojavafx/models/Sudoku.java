package com.example.hellojavafx.models;

import java.util.Random;

public class Sudoku {

    private int[][] sudoku; // El tablero completo generado
    private int[][] visibleSudoku; // Tablero que el usuario verá (oculto inicialmente)

    public Sudoku() {
        sudoku = new int[6][6]; // Tablero de 6x6
        visibleSudoku = new int[6][6]; // El tablero que se muestra al usuario
        limpiarSudoku();
    }

    // Método para limpiar ambos tableros
    public void limpiarSudoku() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                sudoku[i][j] = 0;
                visibleSudoku[i][j] = 0;
            }
        }
    }

    // Generar el tablero completo usando backtracking
    public boolean generarSudokuCompleto() {
        return resolverSudoku(0, 0);
    }

    // Algoritmo de backtracking para llenar el tablero
    private boolean resolverSudoku(int fila, int columna) {
        if (fila == 6) {
            return true; // Si llegamos al final, el Sudoku está completamente generado
        }

        if (columna == 6) {
            return resolverSudoku(fila + 1, 0); // Pasar a la siguiente fila
        }

        Random random = new Random();
        int[] numeros = {1, 2, 3, 4, 5, 6}; // Posibles números
        for (int i = numeros.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // Mezclamos los números para elegir aleatoriamente
            int temp = numeros[i];
            numeros[i] = numeros[j];
            numeros[j] = temp;
        }

        for (int num : numeros) {
            if (esNumeroValido(fila, columna, num)) {
                sudoku[fila][columna] = num;
                if (resolverSudoku(fila, columna + 1)) {
                    return true;
                }
                sudoku[fila][columna] = 0; // Backtrack
            }
        }
        return false; // No es posible resolver
    }

    // Nuevo método para validar si un número es válido en la posición dada
    public boolean esNumeroValido(int fila, int columna, int valor) {
        return validarFila(fila, valor) && validarColumna(columna, valor) && validarCuadrante(fila, columna, valor);
    }

    // Métodos de validación para filas, columnas y cuadrantes
    public boolean validarFila(int fila, int valor) {
        for (int j = 0; j < 6; j++) {
            if (sudoku[fila][j] == valor) {
                return false;
            }
        }
        return true;
    }

    public boolean validarColumna(int columna, int valor) {
        for (int i = 0; i < 6; i++) {
            if (sudoku[i][columna] == valor) {
                return false;
            }
        }
        return true;
    }

    public boolean validarCuadrante(int fila, int columna, int valor) {
        int inicioFila = (fila / 2) * 2;
        int inicioColumna = (columna / 3) * 3;
        for (int i = inicioFila; i < inicioFila + 2; i++) {
            for (int j = inicioColumna; j < inicioColumna + 3; j++) {
                if (sudoku[i][j] == valor) {
                    return false;
                }
            }
        }
        return true;
    }

    // Método para obtener el tablero completo (para uso interno)
    public int[][] getSudoku() {
        return sudoku;
    }

    // Método para verificar si el número ingresado es correcto
    public boolean verificarNumero(int fila, int columna, int valor) {
        return sudoku[fila][columna] == valor;
    }

    // Revela el número si es correcto
    public void revelarNumero(int fila, int columna) {
        visibleSudoku[fila][columna] = sudoku[fila][columna];
    }

    // Método para obtener el tablero visible
    public int[][] getVisibleSudoku() {
        return visibleSudoku;
    }

    // Método para revelar dos valores en cada bloque de 2x3
    public void revelarDosValoresEnCadaBloque() {
        Random random = new Random();
        // Recorremos cada bloque de 2x3
        for (int bloqueFila = 0; bloqueFila < 6; bloqueFila += 2) {
            for (int bloqueColumna = 0; bloqueColumna < 6; bloqueColumna += 3) {
                int valoresRevelados = 0;

                // Mientras no hayamos revelado dos valores
                while (valoresRevelados < 2) {
                    int fila = bloqueFila + random.nextInt(2);
                    int columna = bloqueColumna + random.nextInt(3);

                    if (visibleSudoku[fila][columna] == 0 && sudoku[fila][columna] != 0) {
                        visibleSudoku[fila][columna] = sudoku[fila][columna];
                        valoresRevelados++;
                    }
                }
            }
        }
    }

    // Método para sugerir un número válido para una celda vacía
    public int sugerirNumero(int fila, int columna) {
        for (int num = 1; num <= 6; num++) {
            if (esNumeroValido(fila, columna, num)) {
                return num; // Retorna el primer número válido encontrado
            }
        }
        return -1; // No hay sugerencias válidas
    }
}
