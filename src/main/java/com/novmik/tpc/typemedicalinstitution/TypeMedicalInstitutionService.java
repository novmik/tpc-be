package com.novmik.tpc.typemedicalinstitution;

import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * {@link TypeMedicalInstitution} business interface layer.
 */
@AllArgsConstructor
@Service
public class TypeMedicalInstitutionService {

  /**
   * {@link TypeMedicalInstitutionRepository}.
   */
  private final TypeMedicalInstitutionRepository typeMiRepository;

  /**
   * Список {@link TypeMedicalInstitution}.
   *
   * @return список {@link TypeMedicalInstitution}
   */
  protected List<TypeMedicalInstitution> getAllTypeMedicalInstitution() {
    return typeMiRepository.findAll();
  }
}
