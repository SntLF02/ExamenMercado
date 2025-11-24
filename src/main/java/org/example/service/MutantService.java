package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.DnaRecord;
import org.example.repository.DnaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final DnaRepository dnaRepository;
    private final MutantDetector mutantDetector;

    public boolean analyzeDna(String[] dna) {
        // 1. Convertimos el array a un String único para usarlo como ID/Hash
        String dnaString = String.join(",", dna);

        // 2. Verificamos si ya existe en BD para ahorrar cómputo (Caché DB)
        Optional<DnaRecord> existingRecord = dnaRepository.findByDna(dnaString);
        if (existingRecord.isPresent()) {
            return existingRecord.get().isMutant();
        }

        // 3. Si no existe, calculamos si es mutante
        boolean isMutant = mutantDetector.isMutant(dna);

        // 4. Guardamos el resultado
        DnaRecord newRecord = DnaRecord.builder()
                .dna(dnaString)
                .isMutant(isMutant)
                .build();
        dnaRepository.save(newRecord);

        return isMutant;
    }
}