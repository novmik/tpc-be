package com.novmik.tpc.typemedicalinstitution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link TypeMedicalInstitution} data persistence layer operation interface.
 */
@Repository
public interface TypeMedicalInstitutionRepository extends
    JpaRepository<TypeMedicalInstitution, Integer> {
}
