package org.example.service;

import org.example.entity.DnaRecord;
import org.example.repository.DnaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MutantServiceTest {

    @Mock
    private DnaRepository dnaRepository;

    @Mock
    private MutantDetector mutantDetector;

    @InjectMocks
    private MutantService mutantService;

    @Test
    void testAnalyzeDna_NewMutant() {
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};

        // Simular que NO existe en BD
        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.empty());
        // Simular que el detector dice TRUE
        when(mutantDetector.isMutant(dna)).thenReturn(true);

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        // Verificamos que guardó en base de datos
        verify(dnaRepository, times(1)).save(any(DnaRecord.class));
    }

    @Test
    void testAnalyzeDna_Cached() {
        String[] dna = {"AAAA", "CCCC", "TCAG", "GGTC"};
        DnaRecord existingRecord = new DnaRecord();
        existingRecord.setMutant(true);

        // Simular que YA existe en BD
        when(dnaRepository.findByDna(anyString())).thenReturn(Optional.of(existingRecord));

        boolean result = mutantService.analyzeDna(dna);

        assertTrue(result);
        // Verificar que NO se volvió a llamar al detector (ahorro de recursos)
        verify(mutantDetector, never()).isMutant(dna);
    }
}