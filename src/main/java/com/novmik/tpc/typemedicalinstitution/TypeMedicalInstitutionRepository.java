package com.novmik.tpc.typemedicalinstitution;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeMedicalInstitutionRepository extends
    JpaRepository<TypeMedicalInstitution, Integer> {

}
