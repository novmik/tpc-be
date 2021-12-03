package com.novmik.tpc.typemedicalinstitution;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class TypeMedicalInstitutionService {

  private final TypeMedicalInstitutionRepository typeMedicalInstitutionRepository;

  protected List<TypeMedicalInstitution> getAllTypeMedicalInstitution() {
    return typeMedicalInstitutionRepository.findAll();
  }
}
