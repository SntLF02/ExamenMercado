package org.example.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {

    private static final int SEQUENCE_LENGTH = 4;

    public boolean isMutant(String[] dna) {
        if (dna == null || dna.length == 0) return false;

        int n = dna.length;
        // Validación básica NxN
        if (dna[0].length() != n) return false;

        // Convertir a char[][] es más rápido que usar charAt repetidamente
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            matrix[i] = dna[i].toCharArray();
        }

        int sequencesFound = 0;

        // Recorremos la matriz
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                // Optimización: Si ya encontramos más de 1, salimos (Early Termination)
                if (sequencesFound > 1) return true;

                // Buscar Horizontal
                if (j <= n - SEQUENCE_LENGTH) {
                    if (checkSequence(matrix, i, j, 0, 1)) sequencesFound++;
                }

                // Buscar Vertical
                if (i <= n - SEQUENCE_LENGTH) {
                    if (checkSequence(matrix, i, j, 1, 0)) sequencesFound++;
                }

                // Buscar Diagonal Principal
                if (i <= n - SEQUENCE_LENGTH && j <= n - SEQUENCE_LENGTH) {
                    if (checkSequence(matrix, i, j, 1, 1)) sequencesFound++;
                }

                // Buscar Diagonal Inversa
                if (i <= n - SEQUENCE_LENGTH && j >= SEQUENCE_LENGTH - 1) {
                    if (checkSequence(matrix, i, j, 1, -1)) sequencesFound++;
                }
            }
        }
        return sequencesFound > 1;
    }

    private boolean checkSequence(char[][] matrix, int row, int col, int dRow, int dCol) {
        char first = matrix[row][col];
        if (first == 0) return false; // Ignorar caracteres nulos si los hubiera

        for (int k = 1; k < SEQUENCE_LENGTH; k++) {
            if (matrix[row + k * dRow][col + k * dCol] != first) {
                return false;
            }
        }
        return true;
    }
}