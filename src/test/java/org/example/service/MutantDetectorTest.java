package org.example.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    @Test
    void testMutantHorizontal() {
        // Caso: 4 A's horizontales en primera fila
        String[] dna = {
                "AAAA",
                "CCCC",
                "TCAG",
                "GGTC"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantVertical() {
        // Caso: Columnas con secuencias
        String[] dna = {
                "ATCG",
                "ATCG",
                "ATCG",
                "ATCG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testMutantDiagonal() {
        // Caso: Diagonal principal y secuencias mezcladas
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    @Test
    void testHuman() {
        // Caso: Sin secuencias suficientes
        String[] dna = {
                "AAAT",
                "CCCG",
                "TCAG",
                "GGTC"
        };
        assertFalse(detector.isMutant(dna));
    }

    @Test
    void testNullOrEmpty() {
        // Validaciones de robustez
        assertFalse(detector.isMutant(null));
        assertFalse(detector.isMutant(new String[]{}));
    }

    @Test
    void testInvalidNxN() {
        // Matriz no cuadrada
        String[] dna = {"ATC", "GCA"};
        assertFalse(detector.isMutant(dna));
    }
}