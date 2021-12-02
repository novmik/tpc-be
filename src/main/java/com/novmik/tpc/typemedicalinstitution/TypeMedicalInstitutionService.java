package com.novmik.tpc.typemedicalinstitution;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class TypeMedicalInstitutionService {

    private final TypeMedicalInstitutionRepository typeMedicalInstitutionRepository;

    protected List<TypeMedicalInstitution> getAllTypeMedicalInstitution() {
        return typeMedicalInstitutionRepository.findAll();
    }
}
