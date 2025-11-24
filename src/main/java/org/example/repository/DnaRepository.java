package org.example.repository;

import org.example.entity.DnaRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DnaRepository extends JpaRepository<DnaRecord, Long> {
    Optional<DnaRecord> findByDna(String dna);
    long countByIsMutant(boolean isMutant);
}